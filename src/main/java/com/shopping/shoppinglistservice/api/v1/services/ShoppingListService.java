package com.shopping.shoppinglistservice.api.v1.services;

import com.shopping.shoppinglistservice.api.v1.dtos.ItemDTO;
import com.shopping.shoppinglistservice.api.v1.dtos.ShoppingListDTO;
import com.shopping.shoppinglistservice.api.v1.models.Item;
import com.shopping.shoppinglistservice.api.v1.models.ShoppingList;
import com.shopping.shoppinglistservice.api.v1.repositories.ShoppingItemRepository;
import com.shopping.shoppinglistservice.api.v1.repositories.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShoppingListService {

    @Autowired
    private ShoppingItemRepository itemRepository;

    @Autowired
    private ShoppingListRepository listRepository;

    public List<ShoppingListDTO> getAllLists() {
        // 1. Buscas en la base de datos (Devuelve Entidades)
        List<ShoppingList> entities = listRepository.findAll();

        // 2. Conviertes Entidades a DTOs (Mapeo)
        // Esto es necesario porque tu Controller promete devolver DTOs, no Entities.
        return entities.stream()
                .map(entity -> new ShoppingListDTO(entity.getId(), entity.getName()))
                .toList();
    }

    public ShoppingListDTO createShoppingList(ShoppingListDTO dto) {
        try {
            // 1. Convertir DTO a Entidad
            ShoppingList entity = new ShoppingList();
            entity.setName(dto.getName());

            if (CollectionUtils.isEmpty(dto.getItems())) {
                new RuntimeException("Lista no encontrada");
            }

            // 2. Guardar en Base de Datos
            ShoppingList savedEntity = listRepository.save(entity);

            // 3. Convertir la Entidad guardada (que ya tiene ID) a DTO
            ShoppingListDTO resultDTO = new ShoppingListDTO(savedEntity.getId(), savedEntity.getName());

            return resultDTO;

        } catch (Exception e) {
            throw e;
        }

    }

    public void deleteShoppingList(Long id) {
        listRepository.deleteById(id);
    }



}