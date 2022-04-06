package com.example._12_spring_intro_exer.repositories;

import com.example._12_spring_intro_exer.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByReleaseDateAfter(LocalDate releaseDate);

    int countByReleaseDateAfter(LocalDate releaseDate);

    List<Book> findAllByAuthor_FirstNameAAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(
            String author_firstName, String author_lastName);
}
