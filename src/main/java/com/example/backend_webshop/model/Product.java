package com.example.backend_webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @JsonProperty("productID")
    private Long productId;
    private String productName;
    private Double price;
    private Integer stock;
    private Double rating;
    @JsonProperty("img_url")
    private String imageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    public void incrementStock(Integer amount){
        this.stock = getStock() - amount;
    }
}
