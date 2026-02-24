package com.shopping.shoppinglistservice.api.v1.controllers;

import com.shopping.shoppinglistservice.api.v1.dtos.ShoppingListDTO;
import com.shopping.shoppinglistservice.api.v1.services.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shopping-lists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;


    // 1. Obtener todas las listas1
    @GetMapping
    public List<ShoppingListDTO> getAllLists() {
        // Lógica para devolver listas...
        return shoppingListService.getAllLists();
    }

    // 2. Crear una lista nueva
    @PostMapping
    public ResponseEntity<ShoppingListDTO> createList(@RequestBody ShoppingListDTO listDTO) {

        // 1. Llamar al servicio para guardar la lista (convierte DTO -> Entity -> DB)
        ShoppingListDTO createdList = shoppingListService.createShoppingList(listDTO);
        // Opción A: Retornar solo el objeto creado con status 201 (Más simple)
        return new ResponseEntity<>(createdList, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}