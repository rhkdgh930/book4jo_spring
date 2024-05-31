package com.booksajo.bookPanda.service;

import com.booksajo.bookPanda.domain.Order;
import com.booksajo.bookPanda.domain.OrderItem;
import com.booksajo.bookPanda.dto.OrderItemRequestDto;
import com.booksajo.bookPanda.dto.response.OrderItemResponseDto;
import com.booksajo.bookPanda.repository.OrderItemRepository;
import com.booksajo.bookPanda.repository.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final BookSalesRepository bookSalesRepository;
    private final OrderRepository orderRepository;

    //주문한 상품 추가
    @Transactional
    public OrderItemResponseDto createOrderItem(OrderItemRequestDto requestDto){
        BookSales bookSales = bookSalesRepository.findById(requestDto.getBookSales().getId())
                .orElseThrow(()->new IllegalArgumentException("주문할 책이 없습니다."));
        requestDto.setBookSales(bookSales);

        Order order = orderRepository.findById(requestDto.getOrder().getId())
                .orElseThrow(()->new IllegalArgumentException("주문하지 않았습니다."));
        requestDto.setOrder(order);

        OrderItem orderItem = new OrderItem(requestDto);
        orderItemRepository.save(orderItem);
        return new OrderItemResponseDto(orderItem);
    }

    public List<OrderItemResponseDto> getOrderItems(Long orderId){
        return orderItemRepository.findByOrderId(orderId).stream().map(OrderItemResponseDto::new).toList();
    }
}
