package com.example.cart.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    private boolean status;
    private String message;

    public boolean isStatus() {
        return status;
    }

}
