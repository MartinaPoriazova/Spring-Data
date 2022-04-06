package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedData();

//        TODO: Change methods and scanner when needed :)

        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();

//        _01_printAllBooksByAgeRestriction(input);
//        _02_getAllGoldenTitlesWithLessThan5000Copies();
//        _03_printAllTitlesAndPricesBetween(5, 40);
//        _04_printAllTitlesNotFromReleaseYear(input);
//        _05_printAllBooksBeforeReleaseDate(input);
//        _06_printAllAuthorsWithTheGivenLetterInTheEndOfFirstName(input);
//        _07_printAllBooksWithTheGivenChars(input);
//        _08_printAllTitlesWhichLastNameStartsBy(input);
//        _09_printCountOfBooksWithLongerTitle(input);
//        _10_getAuthorsTotalCopiesOfAllBooks();
//        _11_printBookInfoByTitle(input);
    }

    private void _11_printBookInfoByTitle(String title) {
        BookSummary summary = this.bookService.getInfoForBookByTitle(title);
        System.out.println(summary.getTitle() + " " + summary.getEditionType() + " " +
                summary.getAgeRestriction() + " " + summary.getPrice());
    }

    private void _10_getAuthorsTotalCopiesOfAllBooks() {
        this.authorService.getWithTotalCopies()
                .forEach(a -> System.out.println(
                    a.getFirstName() + " " + a.getLastName() +
                    " - " + a.getTotalCopies()));
    }

    private void _09_printCountOfBooksWithLongerTitle(String input) {
        int length = Integer.parseInt(input);
        int totalBooks = this.bookService.findBooksCountWithLongerTitleThanGiven(length);

        System.out.println(totalBooks);
    }

    private void _08_printAllTitlesWhichLastNameStartsBy(String chars) {
        this.bookService.findAllTitlesWhichLastNameStartsBy(chars)
                .forEach(book -> System.out.printf("%s (%s %s)\n",
                        book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
    }

    private void _07_printAllBooksWithTheGivenChars(String chars) {
        this.bookService.findAllBooksByGivenChars(chars)
                .forEach(System.out::println);
    }

    private void _06_printAllAuthorsWithTheGivenLetterInTheEndOfFirstName(String letter) {
        this.authorService.findByFirstNameEndingWith(letter)
            .stream()
            .map(a -> a.getFirstName() + " " + a.getLastName())
            .forEach(System.out::println);
    }

    private void _05_printAllBooksBeforeReleaseDate(String date) {
        this.bookService.findBooksReleasedBefore(date)
                .forEach(book -> System.out.printf("%s %s %.2f\n",
                        book.getTitle(), book.getEditionType(), book.getPrice()));
    }

    private void _04_printAllTitlesNotFromReleaseYear(String input) {
        int releaseYear = Integer.parseInt(input);
        this.bookService.findNotReleasedIn(releaseYear)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    private void _03_printAllTitlesAndPricesBetween(int i, int i1) {
        this.bookService.findAllWithPriceNotBetween(5, 40)
                .forEach(book -> System.out.println(book.getTitle() + " - $" + book.getPrice()));;
    }

    private void _02_getAllGoldenTitlesWithLessThan5000Copies() {
        this.bookService.findAllTitlesByEditionAndCopies(EditionType.GOLD, 5000)
                .forEach(System.out::println);
    }

    private void _01_printAllBooksByAgeRestriction(String ageRestriction) {
        this.bookService.findAllTitlesByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
