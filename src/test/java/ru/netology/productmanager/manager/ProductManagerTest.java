package ru.netology.productmanager.manager;

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

    private void save4Items() {
        manager.add(book1);
        manager.add(book2);
        manager.add(smartphone1);
        manager.add(smartphone2);
    }

    @ParameterizedTest
    @CsvSource(value = {"iphone", "IPHONE"})
    public void shouldSearchIncorrectLetters(String text) {
        save4Items();

        manager.add(smartphone3);

        Product[] actual = manager.searchBy(text);
        Product[] expected = {};
        assertArrayEquals(expected, actual);

    }

    @Test
    public void shouldSearchIfNotFullWord() {
        save4Items();

        Product[] actual = manager.searchBy("Pot");
        Product[] expected = {book1, book2};

        assertArrayEquals(expected, actual);
    }


    @Test
    public void shouldSearchByTextIfExistsSmartphoneModel() {
        save4Items();

        manager.add(smartphone3);

        Product[] actual = manager.searchBy("Iphone");
        Product[] expected = {smartphone1, smartphone3};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByTextIfExistsSmartphoneManufacturer() {
        save4Items();

        manager.add(smartphone3);

        Product[] actual = manager.searchBy("Apple");
        Product[] expected = {smartphone1, smartphone3};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByTextIfExistsBookTitle() {
        save4Items();

        Product[] actual = manager.searchBy("Potter");
        Product[] expected = {book1, book2};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByTextIfExistsBookAuthor() {
        save4Items();

        manager.add(book3);

        Product[] actual = manager.searchBy("Agustin");
        Product[] expected = {book3};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByTextIfNotExists() {
        save4Items();

        Product[] actual = manager.searchBy("Violet");
        Product[] expected = {};

        assertArrayEquals(expected, actual);
    }


    @Test
    public void shouldMatchIfSmartphoneModelEquals() {
        save4Items();

        boolean actual = manager.matches(smartphone1, "Iphone 11");
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchIfSmartphoneManufacturerEquals() {
        save4Items();

        boolean actual = manager.matches(smartphone1, "Apple");
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchIfSmartphoneAndEmptySearch() {
        save4Items();

        boolean actual = manager.matches(smartphone1, " ");
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchIfSmartphoneModelNotEquals() {
        save4Items();

        boolean actual = manager.matches(smartphone1, "Iphone 10");
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchIfSmartphoneManufacturerNotEquals() {
        save4Items();

        boolean actual = manager.matches(smartphone1, "Samsung");
        boolean expected = false;

        assertEquals(expected, actual);
    }


    @Test
    public void shouldMatchIfBookAndEmptySearch() {
        save4Items();

        boolean actual = manager.matches(book1, " ");
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchIfBookAuthorEquals() {
        save4Items();

        boolean actual = manager.matches(book2, "Rowling");
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchIfBookTitleEquals() {
        save4Items();

        boolean actual = manager.matches(book1, "Harry Potter");
        boolean expected = true;

        assertEquals(expected, actual);
    }


    @Test
    public void shouldMatchIfBookNotEquals() {
        save4Items();

        boolean actual = manager.matches(book1, "Gulliver");
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchIfNotBookOrSmartphone() {
        save4Items();
        Product product = new Product();
        boolean actual = manager.matches(product, " ");
        boolean expected = false;

        assertEquals(expected, actual);
    }

}