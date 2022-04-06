package com.example._12_spring_intro_exer;

import com.example._12_spring_intro_exer.entities.Author;
import com.example._12_spring_intro_exer.entities.Book;
import com.example._12_spring_intro_exer.repositories.AuthorRepository;
import com.example._12_spring_intro_exer.repositories.BookRepository;
import com.example._12_spring_intro_exer.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService,
                         BookRepository bookRepository,
                         AuthorRepository authorRepository
    ) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        this.seedService.seedAuthors();
//        this.seedService.seedCategory();
//        this.seedService.seedBooks();

//        this._01_booksAfter2000();
//        this._02_allAuthorsWithBookBefore1990();
//        this._03_allAuthorsOrderedByBookCount();
        this._04_allBooksByAuthorOrderedByReleaseDate();
    }


    private void _01_booksAfter2000() {
        LocalDate year2000 = LocalDate.of(2000, 12, 31);
        List<Book> booksListAfter2000 = this.bookRepository.findByReleaseDateAfter(year2000);

        booksListAfter2000.forEach(book -> System.out.println(book.getTitle()));

        int count = this.bookRepository.countByReleaseDateAfter(year2000);

        System.out.println("Total count: " + count);
    }

    private void _02_allAuthorsWithBookBefore1990() {
        LocalDate year1990 = LocalDate.of(1990, 1, 1);

        List<Author> authors = this.authorRepository.findDistinctByBooksReleaseDateBefore(year1990);

        authors.forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));
    }

    private void _03_allAuthorsOrderedByBookCount() {
        List<Author> authors = this.authorRepository.findAll();
        authors.stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(author -> System.out.printf("%s %s -> %d%n",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()));
    }

    private void _04_allBooksByAuthorOrderedByReleaseDate() {
        List<Book> books = this.bookRepository.findAllByAuthor_FirstNameAAndAuthor_LastNameOrderByReleaseDateDescTitleAsc
                        ("George", "Powell");
                books.stream()
                .forEach(book -> System.out.printf("%s %s %d",
                                book.getTitle(),
                                book.getReleaseDate(),
                                book.getCopies()));

    }

}
