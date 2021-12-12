package com.example.cart.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemDto {
    @NotNull
    private String name;
    @NotNull
    private int price;
    @NotNull
    private int quantity;
}
