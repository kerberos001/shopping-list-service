package com.shopping.shoppinglistservice.api.v1.repositories;

import com.shopping.shoppinglistservice.api.v1.models.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// <Entidad, Tipo_De_Dato_Del_ID>
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    // ¡Listo! Al extender JpaRepository ya tienes:
    // .findAll(), .findById(), .save(), .delete(), etc.
}