package com.booksajo.bookPanda.domain.order;

import com.booksajo.bookPanda.domain.book.BookSales;
import com.booksajo.bookPanda.domain.cart.CartItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_sales_id")
    private BookSales bookSales;
}
