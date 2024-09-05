package com.example.backend_webshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String imgUrl;

    @JsonIgnore
    @JoinColumn(name = "categoryId")
    @ManyToOne
    private Category category;

    public void decrementStock(Integer amount){
        setStock(this.stock - amount);
    }
}
