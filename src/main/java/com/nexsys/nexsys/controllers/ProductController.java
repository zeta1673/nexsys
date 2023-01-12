package com.nexsys.nexsys.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexsys.nexsys.models.Product;
import com.nexsys.nexsys.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    private Optional<Product> findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping()
    private List<Product> findAll(@RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> sortBy, @RequestParam Optional<Direction> order) {

        return productService
                .findAll(PageRequest.of(pageNumber.orElse(0), pageSize.orElse(5), order.orElse(Direction.ASC),
                        sortBy.orElse("id")));

    }

    @PostMapping()
    private Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        productService.update(id, product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteById(@PathVariable Long id) {

        try {
            productService.findById(id);
            productService.deleteById(id);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("error deleting the data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("product deleted", HttpStatus.OK);

    }

}
