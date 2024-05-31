package com.booksajo.bookPanda.domain;

import com.booksajo.bookPanda.dto.OrderItemRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", columnDefinition = "BIGINT", nullable = false)
    private Order order;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "book_sales_id", referencedColumnName = "book_sales_id", columnDefinition = "BIGINT", nullable = false)
    private BookSales bookSales;

    @Builder
    public OrderItem(OrderItemRequestDto requestDto){
        this.quantity = requestDto.getQuantity();
        this.order = requestDto.getOrder();
        this.bookSales = requestDto.getBookSales();
    }
}
