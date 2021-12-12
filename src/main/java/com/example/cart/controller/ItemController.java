package com.example.cart.controller;

import com.example.cart.dto.UpdateItemDto;
import com.example.cart.model.Item;
import com.example.cart.repository.ItemRepository;
import com.example.cart.service.ItemService;
import com.example.cart.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public List<Item> getAll() {

        return itemService.getAll();
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {

        Item item = itemService.getById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Item not found"));
    }
    @PostMapping("/items")
    public ResponseEntity<?> saveItem(@RequestBody @Valid Item item){

        if(itemRepository.existsByName(item.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse(false,"Item name exists already"));
        }

        itemRepository.save(item);

        return ResponseEntity.ok(item);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@PathVariable(name = "id") int id,
                                        @RequestBody @Valid UpdateItemDto dto){
        return itemService.updateItem(id, dto);

    }
}

