package com.shopping.shoppinglistservice.api.v1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*; // Si usas Spring Boot 3+ (o javax.persistence si es viejo)
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_lists")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // RELACIÓN UNO A MUCHOS
    // mappedBy = "shoppingList" indica que la clase Item es la "dueña" de la relación
    // cascade = ALL significa que si borras la lista, se borran sus items
    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    // Constructores, Getters y Setters
    public ShoppingList() {}

    public ShoppingList(String name) {
        this.name = name;
    }

    public ShoppingList(Long id) {
        this.id = id;
    }

    // Método helper para agregar items fácilmente y mantener la coherencia
    public void addItem(Item item) {
        items.add(item);
        item.setShoppingList(this);
    }

    // Getters y Setters estándar...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}