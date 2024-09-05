package com.example.backend_webshop.controller;

import com.example.backend_webshop.database.BasketRepository;
import com.example.backend_webshop.database.CategoryRepository;
import com.example.backend_webshop.database.ProductRepository;
import com.example.backend_webshop.models.Basket;
import com.example.backend_webshop.models.Category;
import com.example.backend_webshop.models.Product;
import com.example.backend_webshop.observer.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class WebshopRestController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;

    private final ProductService productService = new ProductService();

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductByCategory(
            @PathVariable Long categoryId
    ) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Error - categoryId not found - please enter a valid ID"));

        List<Product> products = category.getProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductDetails(
            @PathVariable Long productId
    ) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Error - productId not found - please enter a valid ID"));

        productService.notifyObserver(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/addToBasket")
    public ResponseEntity<Basket> addToBasket(
            @RequestBody Basket newBasketEntry
    ) {
        Optional<Product> existingProduct = productRepository.findById(newBasketEntry.getProductId());
        Optional<Basket> existingBasket = basketRepository.findById(newBasketEntry.getProductId());
        Basket basket;

        if (existingProduct.isPresent()) {
            if (existingProduct.get().getStock() > 0 &&
                    existingProduct.get().getStock() >= newBasketEntry.getAmount()
            ) {
                if (existingBasket.isPresent()){
                    //overwrite existing basket
                    existingBasket.get().setAmount(
                            existingBasket.get().getAmount() + newBasketEntry.getAmount()
                    );
                    basket = existingBasket.get();
                } else {
                    //create new basket
                    basket = new Basket(
                            newBasketEntry.getProductId(),
                            newBasketEntry.getAmount(),
                            // implement boolean logic
                            newBasketEntry.getIsOutOfStock()
                    );
                }
                // update stock in Product
                existingProduct.get().decrementStock(newBasketEntry.getAmount());

                if(existingProduct.get().getStock() == 0){
                    basket.setIsOutOfStock(true);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error - product is sold out");
            }
            //update stock in database
            productRepository.save(existingProduct.get());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error - product not found - please enter a valid productID");
        }

        basketRepository.save(basket);
        return new ResponseEntity<>(basket, HttpStatus.CREATED);
    }

}
