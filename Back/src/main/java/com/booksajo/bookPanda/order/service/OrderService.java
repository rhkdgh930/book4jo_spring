package com.booksajo.bookPanda.order.service;

import com.booksajo.bookPanda.order.domain.Order;
import com.booksajo.bookPanda.order.dto.OrderRequestDto;
import com.booksajo.bookPanda.order.dto.response.OrderResponseDto;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;

    //바로 주문
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, String email){
        User user = userRepository.findByUserEmail(email);

        requestDto.setUser(user);
        requestDto.setOrderDate(LocalDateTime.now());

        Order order = new Order(requestDto);

        return new OrderResponseDto(order);
    }

//    //장바구니 주문
//    public OrderResponseDto orderCartItem(List<OrderRequestDto> requestDtoList, Authentication authentication){
//
//    }
}
