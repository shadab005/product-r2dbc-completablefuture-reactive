package com.reactive.completablefuture.r2dbc.productr2dbccompletablefuturereactive.controller;


import com.reactive.completablefuture.r2dbc.productr2dbccompletablefuturereactive.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository repository;

    @Autowired
    DatabaseClient databaseClient;

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/sample")
    public CompletableFuture<Product> getProduct() {
        System.out.println("getProduct invoked");
        Product p = new Product(null, "testName", 100.00);
        //repository.save(p);

        return CompletableFuture.completedFuture(p);
    }

    @GetMapping
    public Flux<Product> getAll() {
        System.out.println("getAll invoked");
        return repository.findAll();
    }

    @GetMapping("/all")
    public Flux<Product> getAllByQuery() {
        System.out.println("all invoked");
        return databaseClient.execute("select * from product").as(Product.class).fetch().all();
    }

    @Async
    @GetMapping("/getAll")
    public CompletableFuture<List<Product>> all() {
        return repository.findAllAsync();
    }

    @Async
    @GetMapping("/name/{name}")
    public CompletableFuture<Product> getByName(@PathVariable String name) {
        System.out.println("all invoked");
        return repository.findOneByName(name);
    }

    @Async
    @DeleteMapping("/name/{name}")
    public CompletableFuture<Product> deleteByName(@PathVariable String name) {
        System.out.println("delete invoked");
        return repository.deleteByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return repository.save(product);
    }


    @Async
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Product> create(@RequestBody Product product) {
        return repository.saveProduct(product.getName(), product.getPrice());
    }
}
