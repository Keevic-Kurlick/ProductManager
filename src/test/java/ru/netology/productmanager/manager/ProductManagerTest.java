package ru.netology.productmanager.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.productmanager.domain.Book;
import ru.netology.productmanager.domain.Product;
import ru.netology.productmanager.domain.Smartphone;
import ru.netology.productmanager.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {
    private ProductRepository repo = new ProductRepository();
    private ProductManager manager = new ProductManager(repo);

    private Product book1 = new Book(1, "Harry Potter", 150, "Rowling", 500, 2002);
    private Product book2 = new Book(2, "Potter And Hermione", 160, "Rowling", 560, 2004);
    private Product book3 = new Book(6, "Friends", 460, "Agustin", 340, 2020);
    private Product smartphone1 = new Smartphone(3, "Iphone 11", 70000, "Apple");
    private Product smartphone2 = new Smartphone(4, "Galaxy S21", 30000, "Samsung");
    private Product smartphone3 = new Smartphone(5, "Iphone 13", 78000, "Apple");

    @BeforeEach
    private void save4Items() {
        manager.add(book1);
        manager.add(book2);
        manager.add(smartphone1);
        manager.add(smartphone2);
    }


    @AfterEach
    private void clear() {
        repo.removeById(1);
        repo.removeById(2);
        repo.removeById(3);
        repo.removeById(4);
    }

    @ParameterizedTest
    @CsvSource(value = {"iphone", "IPHONE"})
    public void shouldSearchIncorrectLetters(String text) {
        manager.add(smartphone3);

        Product[] actual = manager.searchBy(text);
        Product[] expected = {};
        assertArrayEquals(expected, actual);

    }

    @Test
    public void shouldSearchIfNotFullWord() {
        Product[] actual = manager.searchBy("Pot");
        Product[] expected = {book1, book2};

        assertArrayEquals(expected, actual);
    }


    @Test
    public void shouldSearchByTextIfExistsSmartphoneModel() {
        manager.add(smartphone3);

        Product[] actual = manager.searchBy("Iphone");
        Product[] expected = {smartphone1, smartphone3};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByTextIfExistsSmartphoneManufacturer() {
        manager.add(smartphone3);

        Product[] actual = manager.searchBy("Apple");
        Product[] expected = {smartphone1, smartphone3};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByTextIfExistsBookTitle() {
        Product[] actual = manager.searchBy("Potter");
        Product[] expected = {book1, book2};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByTextIfExistsBookAuthor() {
        manager.add(book3);

        Product[] actual = manager.searchBy("Agustin");
        Product[] expected = {book3};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByTextIfNotExists() {
        Product[] actual = manager.searchBy("Violet");
        Product[] expected = {};

        assertArrayEquals(expected, actual);
    }


    @Test
    public void shouldMatchIfSmartphoneModelEquals() {
        boolean actual = manager.matches(smartphone1, "Iphone 11");

        assertTrue(actual);
    }

    @Test
    public void shouldMatchIfSmartphoneManufacturerEquals() {
        boolean actual = manager.matches(smartphone1, "Apple");

        assertTrue(actual);
    }

    @Test
    public void shouldMatchIfSmartphoneAndEmptySearch() {
        boolean actual = manager.matches(smartphone1, " ");

        assertTrue(actual);
    }

    @Test
    public void shouldMatchIfSmartphoneModelNotEquals() {
        boolean actual = manager.matches(smartphone1, "Iphone 10");

        assertFalse(actual);
    }

    @Test
    public void shouldMatchIfSmartphoneManufacturerNotEquals() {
        boolean actual = manager.matches(smartphone1, "Samsung");

        assertFalse(actual);
    }


    @Test
    public void shouldMatchIfBookAndEmptySearch() {
        boolean actual = manager.matches(book1, " ");

        assertTrue(actual);
    }

    @Test
    public void shouldMatchIfBookAuthorEquals() {
        boolean actual = manager.matches(book2, "Rowling");

        assertTrue(actual);
    }

    @Test
    public void shouldMatchIfBookTitleEquals() {
        boolean actual = manager.matches(book1, "Harry Potter");

        assertTrue(actual);
    }


    @Test
    public void shouldMatchIfBookNotEquals() {
        boolean actual = manager.matches(book1, "Gulliver");

        assertFalse(actual);
    }

    @Test
    public void shouldMatchIfNotBookOrSmartphone() {
        Product product = new Product();
        boolean actual = manager.matches(product, " ");

        assertFalse(actual);
    }

}