package com.example.cart.service;

import com.example.cart.model.Cart;
import com.example.cart.model.Item;
import com.example.cart.repository.CartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepositoryMock;

    @Mock
    private ItemService itemService;

    @Test
    public void getAllCarts(){
        List<Item> items = Arrays.asList(new Item(1,"Item1",100,2,200), new Item(2,"Item2",400,5,2000));
        when(cartRepositoryMock.findAll()).thenReturn(Arrays.asList(new Cart(1,"Item1",100,2, items),
                new Cart(1,"Item1",100,2, items) ));
        assertEquals(2,cartService.getAll().get(0).getNumberOfItems());
        assertEquals("Item1",cartService.getAll().get(0).getName());
    }

    @Test
    public void addItems_success(){
        List<Item> items = new ArrayList<>();
        items.add(new Item(1,"Item1",100,2,200));
        items.add(new Item(2,"Item2",400,5,2000));
        Item newItem = new Item(3,"new Item",300,2,600);
        Cart cart = new Cart(1, "Item1", 100, 2, items);
        when(cartRepositoryMock.findById(1)).thenReturn(Optional.of(cart));
        when(itemService.getById(3)).thenReturn(newItem);
        items.add(newItem);
        cart.setItems(items);
        when(cartRepositoryMock.save(cart)).thenReturn(cart);
        ResponseEntity<?> updatedCart = cartService.addItem(1,3);
        assertTrue(updatedCart.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void removeItem_success(){
        List<Item> items = new ArrayList<>();
        items.add(new Item(1,"Item1",100,2,200));
        items.add(new Item(2,"Item2",400,5,2000));
        Cart cart = new Cart(1, "Item1", 2200, items.size(), items);
        when(cartRepositoryMock.findById(1)).thenReturn(Optional.of(cart));
        when(itemService.getById(2)).thenReturn(items.get(1));
        items.remove(items.get(0));
        cart.setItems(items);
        when(cartRepositoryMock.save(cart)).thenReturn(cart);
        assertEquals(1,cart.getItems().size());

    }


}
