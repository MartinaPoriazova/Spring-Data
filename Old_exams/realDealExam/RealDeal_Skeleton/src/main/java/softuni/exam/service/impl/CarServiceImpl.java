package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.dto.CarSeedDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import javax.validation.Validator;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final String CARS_FILE_PATH = "src/main/resources/files/json/cars.json";

    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files
                .readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        CarSeedDto[] carSeedDtos = gson
                .fromJson(readCarsFileContent(), CarSeedDto[].class);

        this.carRepository.saveAll(Arrays.stream(carSeedDtos)
                .map(car -> appendResponseMessage(stringBuilder, car))
                .filter(this.validationUtil::isValid)
                .map(car -> this.modelMapper.map(car, Car.class))
                .collect(Collectors.toList()));

        return stringBuilder.toString().trim();
    }

    private CarSeedDto appendResponseMessage(StringBuilder stringBuilder, CarSeedDto car) {
        String message;

        if (this.validationUtil.isValid(car)) {
            message = String.format("Successfully imported car - %s %s", car.getMake(), car.getModel());
        } else {
            message = "Invalid car";
        }

        stringBuilder.append(message).append(System.lineSeparator());

        return car;
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder stringBuilder = new StringBuilder();

        carRepository.findCarsOrderByPicturesCountThenByMake()
                .forEach(car -> {
                    stringBuilder
                            .append(String.format("Car make - %s, model - %s\n" +
                                                "\tKilometers - %d\n" +
                                                "\tRegistered on - %s\n" +
                                                "\tNumber of pictures - %d\n",
                                                car.getMake(), car.getModel(), car.getKilometers(),
                                                car.getRegisteredOn(), car.getPictures().size()))
                            .append(System.lineSeparator());
                });
        return stringBuilder.toString();
    }

    @Override
    public Car findById(long id) {
        return carRepository
                .findById(id)
                .orElse(null);
    }
}
