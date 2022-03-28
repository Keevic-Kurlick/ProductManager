package ru.netology.productmanager.repository;

import ru.netology.productmanager.domain.Product;
import ru.netology.productmanager.repository.exceptions.NotFoundException;

public class ProductRepository {
    private Product[] items = new Product[0];
    private Product[] searchResult = new Product[0];

    public void save(Product item) {
        int length = items.length + 1;
        Product[] tmp = new Product[length];
        System.arraycopy(items, 0, tmp, 0, items.length);
        int lastIndex = tmp.length - 1;
        tmp[lastIndex] = item;
        items = tmp;
    }

    public Product findById(int id) {
        for (Product item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public Product[] findAll() {
        return items;
    }

    public void removeById(int id) {
        if (findById(id) != null) {
            int length = items.length - 1;
            Product[] tmp = new Product[length];
            int index = 0;
            for (Product item : items) {
                if (item.getId() != id) {
                    tmp[index] = item;
                    index++;
                }
            }
            items = tmp;
        } else {
            throw new NotFoundException(id);
        }
    }
}
