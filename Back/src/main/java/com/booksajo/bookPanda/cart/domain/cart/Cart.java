package com.booksajo.bookPanda.cart.domain.cart;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CartItem> cartItems = new ArrayList<>();

//    public void addItem(CartItem cartItem) {
//        cartItems.add(cartItem);
//        cartItem.setCart(this);
//    }
//
//    public void removeItem(CartItem cartItem) {
//        cartItems.remove(cartItem);
//        cartItem.setCart(null);
//    }
}
