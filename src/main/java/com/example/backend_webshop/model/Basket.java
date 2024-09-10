package com.example.backend_webshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Basket {
    @Id
    private Long productId;
    private Integer amount;
    private Boolean isOutOfStock;
}
