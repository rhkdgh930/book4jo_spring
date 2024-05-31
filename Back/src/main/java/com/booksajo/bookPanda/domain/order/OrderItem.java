package com.booksajo.bookPanda.domain.order;

import com.booksajo.bookPanda.domain.book.BookSales;
import com.booksajo.bookPanda.domain.cart.CartItem;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "cartitem_id")
    private CartItem cartItem;

    @OneToOne
    @JoinColumn(name = "booksales_id")
    private BookSales bookSales;
}
