package com.shopping.shoppinglistservice.api.v1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;
    private boolean isBought = false; // Estado del item

    // RELACIÓN MUCHOS A UNO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id") // Nombre de la columna en la BD MySQL
    private ShoppingList shoppingList;

    // Constructores
    public Item() {}

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public boolean isBought() { return isBought; }
    public void setBought(boolean bought) { isBought = bought; }
    public ShoppingList getShoppingList() { return shoppingList; }
    public void setShoppingList(ShoppingList shoppingList) { this.shoppingList = shoppingList; }
}