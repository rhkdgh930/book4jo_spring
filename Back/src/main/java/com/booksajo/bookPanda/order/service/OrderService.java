package com.booksajo.bookPanda.order.service;

import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.book.repository.BookSalesRepository;
import com.booksajo.bookPanda.order.constant.Status;
import com.booksajo.bookPanda.order.domain.Order;
import com.booksajo.bookPanda.order.domain.OrderItem;
import com.booksajo.bookPanda.order.dto.OrderRequestDto;
import com.booksajo.bookPanda.order.dto.response.OrderResponseDto;
import com.booksajo.bookPanda.order.repository.OrderItemRepository;
import com.booksajo.bookPanda.order.repository.OrderRepository;
import com.booksajo.bookPanda.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    OrderRepository orderRepository;
    BookSalesRepository bookSalesRepository;
    OrderItemRepository orderItemRepository;

    //바로 주문
    public OrderResponseDto createOrder(Long bookId, OrderRequestDto requestDto, Authentication authentication) {
        BookSales book = bookSalesRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책이 없습니다."));
        if(isStocked(book)){
            User user = (User) authentication.getPrincipal();
            requestDto.setUser(user);
            requestDto.setStatus(Status.NOT_PAID);

            List<OrderItem> orderItems = new ArrayList<>();

            requestDto.setTotalPrice(Integer.parseInt(book.getBookInfo().getDiscount()));//TODO : 전체 가격 측정

            Order order = new Order(requestDto);
            orderRepository.save(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setBookSales(book);
            orderItem.setOrder(order);

            orderItems.add(orderItem);

            orderItemRepository.saveAll(orderItems);

            return new OrderResponseDto(order);
        } else {
            throw new IllegalArgumentException("품절된 상품입니다.");
        }
    }

    //TODO : 주문 취소


    private int calculateTotalPrice(List<OrderItem> orderItems) {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            int itemPrice = Integer.parseInt(orderItem.getBookSales().getBookInfo().getDiscount());//TODO : 가격 측정
            int quantity = orderItem.getQuantity();
            totalPrice += (itemPrice * quantity);
        }
        return totalPrice;
    }

    //재고 있는지 확인
    private boolean isStocked(BookSales bookSales) {
        boolean isStocked = false;
        int stock = 0;
        stock = bookSales.getStock();

        if(stock > 0) {
            isStocked = true;
        }
        return isStocked;
    }


      //TODO : 아이템 재고 줄이고 CART 초기화
//    private void decreaseItemStockAndRemoveCartItems(Long cartItemId, Long count) {
//        CartItem cartItem = cartItemRepository.findById(cartItemId)
//                .orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다."));
//
//        BookSales book = cartItem.getBookSales();
//        decreaseItemStock(book.getId(), count);
//        cartItemRepository.delete(cartItem);
//    }

    //TODO : 책 재고 줄이기
    public void decreaseItemStock(long itemId, long quantity) {
        BookSales bookSales = bookSalesRepository.findById(itemId)
                .orElseThrow(() ->new IllegalArgumentException("아이템 없음."));

        if (bookSales.getStock() >= quantity) {
            bookSales.setStock((int) (bookSales.getStock() - quantity));
            bookSalesRepository.save(bookSales);
        } else {
            throw new IllegalArgumentException("재고 부족");//재고 부족 에러코드만들기
        }
    }
}
