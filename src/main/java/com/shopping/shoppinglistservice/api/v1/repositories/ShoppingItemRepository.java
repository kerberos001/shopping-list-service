package com.shopping.shoppinglistservice.api.v1.repositories;

import com.shopping.shoppinglistservice.api.v1.models.Item;
import com.shopping.shoppinglistservice.api.v1.models.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingItemRepository extends JpaRepository<Item, Long> {
    // ¡Listo! Al extender JpaRepository ya tienes:
    // .findAll(), .findById(), .save(), .delete(), etc.

    @Query("SELECT i FROM Item i WHERE i.shoppingList.id = :listId")
    List<Item> findItemByShoppingListId(@Param("listId") Long listId);

    @Query("SELECT i FROM Item i WHERE i.shoppingList.id = :listId AND i.id = :itemId")
    Item findItemByIdIAndShoppingList( @Param("listId") Long listId, @Param("itemId") Long itemId );
}
