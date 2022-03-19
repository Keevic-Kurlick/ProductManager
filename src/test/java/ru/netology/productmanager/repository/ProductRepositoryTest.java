package ru.netology.productmanager.repository;

import org.junit.jupiter.api.Test;
import ru.netology.productmanager.domain.Book;
import ru.netology.productmanager.domain.Product;
import ru.netology.productmanager.domain.Smartphone;
import ru.netology.productmanager.repository.exceptions.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    private ProductRepository repo = new ProductRepository();

    private Product book1 = new Book(1, "Harry Potter", 150, "Rowling", 500, 2002);
    private Product book2 = new Book(2, "Potter And Hermione", 160, "Rowling", 560, 2004);
    private Product smartphone1 = new Smartphone(3, "Iphone 11", 70000, "Apple");
    private Product smartphone2 = new Smartphone(4, "Galaxy S21", 30000, "Samsung");

    private void save4Items() {
        repo.addProduct(book1);
        repo.addProduct(book2);
        repo.addProduct(smartphone1);
        repo.addProduct(smartphone2);
    }

    @Test
    public void shouldRemoveByIdIfNullException() {
        save4Items();

        assertThrows(NotFoundException.class, () -> repo.removeById(5));
    }

    @Test
    public void shouldRemoveById() {
        save4Items();

        repo.removeById(2);
        Product[] actual = repo.findAll();
        Product[] expected = {book1, smartphone1, smartphone2};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldAdd() {
        save4Items();

        Product[] actual = repo.findAll();
        Product[] expected = {book1, book2, smartphone1, smartphone2};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchById() {
        save4Items();

        Product actual = repo.findById(2);
        Product expected = book2;

        assertEquals(expected, actual);
    }

    @Test
    public void shouldSearchByIdNull() {
        save4Items();

        Product actual = repo.findById(0);
        Product expected = null;

        assertEquals(expected, actual);
    }
}