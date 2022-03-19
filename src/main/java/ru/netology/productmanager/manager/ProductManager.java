package ru.netology.productmanager.manager;

import ru.netology.productmanager.domain.Product;
import ru.netology.productmanager.domain.Book;
import ru.netology.productmanager.domain.Smartphone;
import ru.netology.productmanager.repository.ProductRepository;

public class ProductManager {
    private ProductRepository repo = new ProductRepository();

    public ProductManager(ProductRepository repo) {
        this.repo = repo;
    }

    private Product[] searchResult = new Product[0];

    public void add(Product product) {
        repo.addProduct(product);
    }

    public Product[] searchBy(String text) {
        for (Product item : repo.findAll()) {
            if (matches(item, text)) {
                searchResult = repo.save(item, searchResult);
            }
        }
        return searchResult;
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
