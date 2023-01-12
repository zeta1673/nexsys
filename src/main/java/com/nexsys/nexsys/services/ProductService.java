package com.nexsys.nexsys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nexsys.nexsys.models.Product;
import com.nexsys.nexsys.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).toList();
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public void update(Long id, Product product) {
        Product currenProduct = productRepository.findById(id).get();

        currenProduct.setName(product.getName());
        currenProduct.setPrice(product.getPrice());

        productRepository.save(currenProduct);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
