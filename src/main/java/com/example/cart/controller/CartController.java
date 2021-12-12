package com.example.cart.controller;
import com.example.cart.dto.ItemDto;
import com.example.cart.model.Cart;
import com.example.cart.repository.CartRepository;
import com.example.cart.service.CartService;
import com.example.cart.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart")
    public List<Cart> getAll() {

        return cartService.getAll();
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {

        Cart cart = cartService.getById(id);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Cart not found"));
    }
    @PostMapping("/cart")
    public ResponseEntity<?> saveCart(@RequestBody @Valid Cart cart){

        if(cartRepository.existsByName(cart.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse(false,"Cart name exists already"));
        }
        System.out.println(cart.getName());
        cartRepository.save(cart);

        return ResponseEntity.ok(cart);
    }

    @PutMapping("/cart/{id}/addItem")
    public ResponseEntity<?> addItemToCart(@PathVariable(name = "id") int id,
                                           @RequestBody @Valid ItemDto item){
        return cartService.addItem(id, item.getId());

    }
    @PutMapping("/cart/{id}/removeItem")
    public ResponseEntity<?> removeItemToCart(@PathVariable(name = "id") int id,
                                              @RequestBody @Valid ItemDto item){
        return cartService.removeItem(id, item.getId());

    }
}

