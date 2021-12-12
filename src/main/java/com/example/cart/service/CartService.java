package com.example.cart.service;

import com.example.cart.model.Cart;
import com.example.cart.model.Item;
import com.example.cart.repository.CartRepository;
import com.example.cart.repository.ItemRepository;
import com.example.cart.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemService itemService;

    public List<Cart> getAll() {

        List<Cart> carts = cartRepository.findAll();

        return carts;
    }

    public Cart getById(int id) {
        Optional<Cart> findById = cartRepository.findById(id);
        if (findById.isPresent()) {
            Cart cart = findById.get();
            return cart;
        }
        return null;
    }

    public ResponseEntity<?> addItem(int cartId, int id) {
        // gushyiramo isanzwemo
        Optional<Cart> findById = cartRepository.findById(cartId);
        if (findById.isPresent()) {
            Cart cart = findById.get();
            List<Item> existingItems = cart.getItems();


            Item item = itemService.getById(id);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse(false, "Item not found"));
            }
            if (existingItems.contains(item)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse(false, "Item is already in the cart"));
            }

            item.setValue(item.getQuantity() * item.getPrice());
            existingItems.add(item);

            cart.setItems(existingItems);
            cart.setNumberOfItems(existingItems.size());
            cart.setTotalPrice(cart.getTotalPrice() + item.getValue());
            cartRepository.save(cart);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(cart);
        }
        return null;
    }

    public ResponseEntity<?> removeItem(int cartId, int id) {
        // gushyiramo isanzwemo
        Optional<Cart> findById = cartRepository.findById(cartId);
        if (findById.isPresent()) {
            Cart cart = findById.get();
            List<Item> existingItems = cart.getItems();


            Item item = itemService.getById(id);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse(false, "Item not found"));
            }
            if (!existingItems.contains(item)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse(false, "Item is not in the cart"));
            }

            item.setValue(item.getQuantity() * item.getPrice());
            existingItems.remove(item);

            cart.setItems(existingItems);
            cart.setNumberOfItems(existingItems.size());
            cart.setTotalPrice(cart.getTotalPrice() - item.getValue());
            cartRepository.save(cart);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(cart);
        }
        return null;
    }
}
