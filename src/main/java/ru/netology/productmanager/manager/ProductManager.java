package ru.netology.productmanager.manager;

import ru.netology.productmanager.domain.Product;
import ru.netology.productmanager.domain.Book;
import ru.netology.productmanager.domain.Smartphone;
import ru.netology.productmanager.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private ProductRepository repo;

    public ProductManager(ProductRepository repo) {
        this.repo = repo;
    }

    public void add(Product product) {
        repo.save(product);
    }

    public Product[] searchBy(String text) {
        List<Product> result = new ArrayList<>();
        for (Product item : repo.findAll()) {
            if (matches(item, text)) {
                result.add(item);
            }
        }
        return result.toArray(new Product[0]);
    }

    public boolean matches(Product product, String search) {
        if (product instanceof Book) {
            Book book = (Book) product;
            if (book.getAuthor().contains(search)) {
                return true;
            }
            return book.getTitle().contains(search);
        } else if (product instanceof Smartphone) {
            Smartphone smartphone = (Smartphone) product;
            if (smartphone.getManufacturer().contains(search)) {
                return true;
            }
            return smartphone.getModel().contains(search);
        }
        return false;
    }
}
