package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.CustomerDto;
import exam.model.entity.Customer;
import exam.repository.CustomerRepository;
import exam.service.CustomerService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_FILE_PATH = "src/main/resources/files/json/customers.json";

    private final CustomerRepository customerRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.customerRepository = customerRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files
                .readString(Path.of(CUSTOMERS_FILE_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readCustomersFileContent(), CustomerDto[].class))
                .filter(customerDto -> {
                    boolean isValid = validationUtil.isValid(customerDto);
//                            && !isExistEmail(customerDto.getEmail());

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Customer %s – %s - %s",
                                    customerDto.getFirstName(), customerDto.getLastName(), customerDto.getEmail())
                                    : "Invalid Customer")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(customerDto -> {
                    Customer customer = modelMapper.map(customerDto, Customer.class);
                    customer.setTown(townService.findTownByName(customerDto.getTown().getName()));
                    return customer;
                })
                .forEach(customerRepository::save);

        return stringBuilder.toString().trim();
    }

    @Override
    public boolean isExistEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
