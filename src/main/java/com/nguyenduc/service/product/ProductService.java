package com.nguyenduc.service.product;

import com.nguyenduc.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService implements IProductService {

    private static  Map<Long, Product> productMap = new HashMap<>();
    private static Long autoPlusId = 1L;
    static {
        productMap.put(++autoPlusId, new Product(autoPlusId, "Iphone", "new", 13000, "image"));
        productMap.put(++autoPlusId, new Product(autoPlusId, "Iphone2", "new", 13000, "image"));
        productMap.put(++autoPlusId, new Product(autoPlusId, "Iphone3", "new", 13000, "image"));
        productMap.put(++autoPlusId, new Product(autoPlusId, "Iphone4", "new", 13000, "image"));
        productMap.put(++autoPlusId, new Product(autoPlusId, "Iphone5", "new", 13000, "image"));
    }


    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public void save(Product product) {
        Long id = product.getId();
        if (id == null) {
            id = ++autoPlusId;
            product.setId(id);
            productMap.put(id, product);
        } else {
            productMap.put(id, product);
        }
    }

    @Override
    public void delete(Long id) {
        productMap.remove(id);
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        for (Product product : new ArrayList<>(productMap.values())) {
            if (product.getName().contains(name)) {
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public Product findById(Long id) {
        return productMap.get(id);
    }
}
