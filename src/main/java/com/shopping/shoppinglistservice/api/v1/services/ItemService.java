package com.shopping.shoppinglistservice.api.v1.services;


import com.shopping.shoppinglistservice.api.v1.dtos.ItemDTO;
import com.shopping.shoppinglistservice.api.v1.models.Item;
import com.shopping.shoppinglistservice.api.v1.models.ShoppingList;
import com.shopping.shoppinglistservice.api.v1.repositories.ShoppingItemRepository;
import com.shopping.shoppinglistservice.api.v1.repositories.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    @Autowired
    private ShoppingItemRepository itemRepository;

    @Autowired
    private ShoppingListRepository listRepository;


    public ItemDTO addItem(Long listId, ItemDTO itemDTO, ShoppingList savedEntity) {

        ShoppingList listEntity = savedEntity;
        // 1. Buscar la lista padre (Si no existe, fallamos aquí)
        if ( null == savedEntity ) {
            listEntity = listRepository.findById(listId)
                    .orElseThrow(() -> new RuntimeException("Lista no encontrada con id: " + listId));
        }

        // 2. Convertir DTO a Entidad (Item)
        Item itemEntity = new Item();
        itemEntity.setName(itemDTO.getName());
        itemEntity.setQuantity(itemDTO.getQuantity());
        itemEntity.setBought(false); // Por defecto no está marcado

        // 3. LA CLAVE: Asignar la relación bidireccional
        // Le decimos al ítem: "Tú perteneces a esta lista"
        itemEntity.setShoppingList(listEntity);

        // 4. Guardar el ítem
        Item savedItem = itemRepository.save(itemEntity);

        // 5. Convertir a DTO para devolver
        ItemDTO resultDTO = buildItemDTO(savedItem);
        resultDTO.setId(savedItem.getId());

        return resultDTO;
    }

    public List<ItemDTO> getAllItems(Long id) {
        List<Item> itemList= itemRepository.findItemByShoppingListId(id);
        return itemList.stream().map(ItemService::buildItemDTO).toList();

    }

    public void toggleItemStatus(Long listId, Long itemId) {
        Item item = itemRepository.findItemByIdIAndShoppingList(listId, itemId);

        if (item == null) {
            throw new RuntimeException("Item no encontrado con id: " + itemId + " en la lista con id: " + listId);
        }
        // Cambiar el estado de isBought
        item.setBought(!item.isBought());

        // Guardar los cambios
        itemRepository.save(item);
    }

    public void deleteItem(Long listId, Long itemId) {
        Item item = itemRepository.findItemByIdIAndShoppingList(listId, itemId);

        if (item == null) {
            throw new RuntimeException("Item no encontrado con id: " + itemId + " en la lista con id: " + listId);
        }

        itemRepository.delete(item);
    }

    private static ItemDTO buildItemDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .isBought(item.isBought())
                .shoppingListId(item.getShoppingList().getId())
                .build();
    }


}
