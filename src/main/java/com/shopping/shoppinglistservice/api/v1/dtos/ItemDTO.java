package com.shopping.shoppinglistservice.api.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private Long id;
    private Long shoppingListId;
    private String name;
    private int quantity;
    private Boolean isBought = false;


}
