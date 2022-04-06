package exam.model.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;

public class CustomerTownDto {
    @Expose
    String name;

    @NotBlank
    public String getName() {
        return name;
    }
}
