package com.booksajo.bookPanda.repository;

import com.booksajo.bookPanda.domain.BookSales;
import com.booksajo.bookPanda.domain.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long id);
}
