package com.booksajo.bookPanda.service;

import com.booksajo.bookPanda.constant.Status;
import com.booksajo.bookPanda.domain.BookSales;
import com.booksajo.bookPanda.domain.Order;
import com.booksajo.bookPanda.domain.OrderItem;
import com.booksajo.bookPanda.domain.User;
import com.booksajo.bookPanda.dto.OrderRequestDto;
import com.booksajo.bookPanda.dto.response.OrderResponseDto;
import com.booksajo.bookPanda.repository.BookSalesRepository;
import com.booksajo.bookPanda.repository.OrderItemRepository;
import com.booksajo.bookPanda.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;

    //바로 주문
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, String email){
        User user = userRepository.findByUserEmail(email);

        requestDto.setUser(user);
        requestDto.setStatus(Status.ORDER);
        requestDto.setOrderDate(LocalDateTime.now());

        Order order = new Order(requestDto);

        return new OrderResponseDto(order);
    }

//    //장바구니 주문
//    public OrderResponseDto orderCartItem(List<OrderRequestDto> requestDtoList, Authentication authentication){
//
//    }
}
