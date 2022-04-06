package softuni.exam.models.entity.dto;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedRootDto {

    @XmlElement(name="seller")
    private List<SellerSeedDto> sellers;

    public List<SellerSeedDto> getSellers() {
        return sellers;
    }

    public SellerSeedRootDto() {
        this.sellers = new ArrayList<>();
    }
}