package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Picture;
import softuni.exam.models.entity.dto.PictureSeedDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURE_FILE_PATH = "src/main/resources/files/json/pictures.json";

    private final PictureRepository pictureRepository;
    private final CarService carService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PictureServiceImpl(PictureRepository pictureRepository, CarService carService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.carService = carService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files
                .readString(Path.of(PICTURE_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        PictureSeedDto[] pictureSeedDtos = gson
                .fromJson(readPicturesFromFile(), PictureSeedDto[].class);

        this.pictureRepository.saveAll(Arrays.stream(pictureSeedDtos)
                .map(pictureSeedDto -> appendResponseMessage(stringBuilder, pictureSeedDto))
                .filter(this.validationUtil::isValid)
                .map(pictureSeedDto -> {
                    Picture picture = this.modelMapper.map(pictureSeedDto, Picture.class);
                    picture.setCar(carService.findById(pictureSeedDto.getCar()));
                    return picture;
                })
                .collect(Collectors.toList()));

        return stringBuilder.toString().trim();
    }

    private PictureSeedDto appendResponseMessage(StringBuilder stringBuilder, PictureSeedDto pictureSeedDto) {
        String message;

        if (this.validationUtil.isValid(pictureSeedDto)) {
            message = String.format("Successfully imported picture - %s", pictureSeedDto.getName());
        } else {
            message = "Invalid picture";
        }

        stringBuilder.append(message).append(System.lineSeparator());

        return pictureSeedDto;
    }
}
