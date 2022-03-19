package ru.netology.productmanager.repository;

import ru.netology.productmanager.domain.Product;

public class ProductRepository {
    private Product[] items = new Product[0];

    public Product[] save(Product item, Product[] to) {
        int length = to.length + 1;
        Product[] tmp = new Product[length];
        System.arraycopy(to, 0, tmp, 0, to.length);
        int lastIndex = tmp.length - 1;
        tmp[lastIndex] = item;
        to = tmp;
        return to;
    }

    public void addProduct(Product item) {
        items = save(item, items);
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
    }
}
