package com.example.backend_webshop.controller;

import com.example.backend_webshop.database.BasketRepository;
import com.example.backend_webshop.database.CategoryRepository;
import com.example.backend_webshop.database.ProductRepository;
import com.example.backend_webshop.model.Basket;
import com.example.backend_webshop.model.Category;
import com.example.backend_webshop.model.Product;
import com.example.backend_webshop.observer.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class WebshopRestController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;

    private final ProductService productService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable Long categoryId
    ){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Error - ID: " + categoryId + " not exist, please enter a valid categoryID.")
                );

        List<Product> products = category.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductDetails(
            @PathVariable Long productId
    ){
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Error - ID: " + productId + " not exist, please enter a valid productID."
                        )
                );
        productService.notifyObservers(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/addToBasket")
    public ResponseEntity<Basket> addToBasket(
            @RequestBody Basket basket
    ){
        Product product = productRepository.findById(
                basket.getProductId()
        ).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Error - ID: " + basket.getProductId() + " not exist, please enter a valid productID.")
                );

        Optional<Basket> existingProduct = basketRepository.findById(basket.getProductId());
        Basket newBasketEntry;

        if(product.getStock() >= basket.getAmount()
        ){
            if(existingProduct.isPresent()){
                //update basket
                existingProduct.get().setAmount(
                        existingProduct.get().getAmount() +
                                basket.getAmount()
                );
                newBasketEntry = new Basket(
                        existingProduct.get().getProductId(),
                        existingProduct.get().getAmount(),
                        false
                );
            } else {
                newBasketEntry = new Basket(
                        basket.getProductId(),
                        basket.getAmount(),
                        false
                );
            }
            //increment product stock
            product.incrementStock(basket.getAmount());

            if(product.getStock() == 0){
                newBasketEntry.setIsOutOfStock(true);
            }
            basketRepository.save(newBasketEntry);
            //update product
            productRepository.save(product);
        } else{
            if(product.getStock() == 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry - product: " + product.getProductName() +
                        " is sold out");
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Sorry - Not enought amount for Product with id: " + basket.getProductId() + " you can only buy: "
                                + product.getStock() + " pieces"
                );
            }
        }
        return new ResponseEntity<>(newBasketEntry, HttpStatus.CREATED);
    };
}
