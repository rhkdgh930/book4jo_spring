package com.booksajo.bookPanda.cart.repository;

import com.booksajo.bookPanda.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository< CartItem, Long> {

}
