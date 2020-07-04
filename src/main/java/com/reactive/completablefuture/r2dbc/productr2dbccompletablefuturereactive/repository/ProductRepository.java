package com.reactive.completablefuture.r2dbc.productr2dbccompletablefuturereactive.repository;
import com.reactive.completablefuture.r2dbc.productr2dbccompletablefuturereactive.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
public interface ProductRepository extends R2dbcRepository<Product, Long> {
    /*
    * Create
    * Issue : Works. But looks like it doesn't return the product in return rather it is CompletableFuture<Void> at runtime
    */
    @Async
    @Query("insert into product (name, price) values(:name, :price)")
    CompletableFuture<Product> saveProduct(String name, Double price);
    //read
    @Async
    CompletableFuture<Product> findOneByName(String name);
    @Async
    CompletableFuture<Product> findById(String id);
    //delete
    @Async
    CompletableFuture<Product> deleteByName(String name);

  /*
   * Issue : Throwing exception : Query [select * from product] returned non unique result.
   */
    @Async
    @Query("select * from product")
    CompletableFuture<List<Product>> findAllAsync();
}