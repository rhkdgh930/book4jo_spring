package com.booksajo.bookPanda.cart.repository;

import com.booksajo.bookPanda.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
//    Optional<Cart> findByUserId(Long userId);
}
