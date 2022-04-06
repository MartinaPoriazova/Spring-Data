package com.example._xml_exer.repository;

import com.example._xml_exer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u" +
            " WHERE (SELECT COUNT (p) FROM Product p WHERE p.seller.id = u.id AND p.buyer IS NOT NULL) > 0" +
            " ORDER BY u.lastName, u.firstName")
    List<User> findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName();

    @Query("SELECT u" +
            " FROM User u" +
            " WHERE size(u.soldProducts) > 0" +
            " ORDER BY size(u.soldProducts) DESC, u.lastName ASC")
    List<User> findAllUsersWithMoreThanOneSoldProductOrderBySoldProductsDescLastNameAsc();

    // @Query("SELECT u FROM User u" +
    //            " JOIN u.soldProducts p" +
    //            " WHERE p.buyer IS NOT NULL" +
    //            " ORDER BY u.lastName, u.firstName")
}
