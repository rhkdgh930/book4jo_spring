package com.booksajo.bookPanda.cart.repository;

import com.booksajo.bookPanda.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository< CartItem, Long> {
    List<CartItem> findAllByCartId(Long CartId);
}
