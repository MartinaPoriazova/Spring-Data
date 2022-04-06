package com.example._xml_exer.model.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersAndProductsViewRootDto {

    @XmlAttribute(name="count")
    private long userCount;

    @XmlElement(name = "user")
    private List<UserAndSoldProductsDto> users;

    public UsersAndProductsViewRootDto() {
    }

    public UsersAndProductsViewRootDto(List<UserAndSoldProductsDto> users) {
        this.userCount = users.size();
        this.users = users;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public List<UserAndSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserAndSoldProductsDto> users) {
        this.users = users;
        this.userCount = users.size();
    }
}
