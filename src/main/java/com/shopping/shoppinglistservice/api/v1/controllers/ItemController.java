package com.shopping.shoppinglistservice.api.v1.controllers;


import com.shopping.shoppinglistservice.api.v1.dtos.ItemDTO;
import com.shopping.shoppinglistservice.api.v1.dtos.ShoppingListDTO;
import com.shopping.shoppinglistservice.api.v1.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{listId}")
    public List<ItemDTO> getAllItems(@PathVariable Long listId) {
        // Lógica para devolver listas...
        return itemService.getAllItems(listId);
    }

    // 3. Agregar un producto a una lista específica (Jerarquía anidada)
    @PostMapping("/{listId}")
    public ResponseEntity<ItemDTO> addItemToList(
            @PathVariable Long listId,
            @RequestBody ItemDTO itemDTO) {

        // Llamamos al servicio pasando el ID del padre (lista) y el hijo (item)
        ItemDTO createdItem = itemService.addItem(listId, itemDTO, null);

        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    // 4. Marcar un producto como comprado
    @PutMapping("/{listId}/{itemId}/status")
    public ResponseEntity<Void> toggleItemStatus(@PathVariable Long listId, @PathVariable Long itemId) {

        itemService.toggleItemStatus(listId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{listId}/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long listId,
            @PathVariable Long itemId) {
        itemService.deleteItem(listId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
