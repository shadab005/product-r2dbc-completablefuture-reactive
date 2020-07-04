package com.reactive.completablefuture.r2dbc.productr2dbccompletablefuturereactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table("product")
public class Product {
    @Id
    private Long id;
    private String name;
    private Double price;
}
