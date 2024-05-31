package com.booksajo.bookPanda.domain.cart;

import com.booksajo.bookPanda.domain.book.BookSales;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "booksales_id")
    private BookSales bookSales;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;


    @Column(name = "quantity")
    private int quantity;
}
