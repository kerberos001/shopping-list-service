package com.shopping.shoppinglistservice.api.v1.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingListDTO {

    private Long id;

    private String name;

    private List<ItemDTO> items;

    public ShoppingListDTO(Long id, String name) {
        this.id=id;
        this.name = name;

    }
}
