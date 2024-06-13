package com.booksajo.bookPanda.cart.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartOrderResponseDto {
    private List<CartItemDto> cartItems;
    private int totalPrice;
    private String userName;
    private String userAddress;
    private String userPhoneNumber;
}
