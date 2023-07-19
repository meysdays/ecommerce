package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.DTO.cart.AddToCartDto;
import com.ecommerce.ecommerce.DTO.cart.CartDto;
import com.ecommerce.ecommerce.DTO.cart.CartItemDto;
import com.ecommerce.ecommerce.exceptions.CustomException;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;
    public void addToCart(AddToCartDto addToCartDto, User user) {

        //validate if the product id is valid
       Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return  cartDto;
    }

    public void deleteCart(Integer id, User user) {

        Optional<Cart> optionalCart = cartRepository.findById(id);

        if (optionalCart.isEmpty()){
            throw new CustomException("cart item id is invalid");
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user){
            throw new CustomException("cart item does not belong to user");
        }

        cartRepository.delete(cart);
    }
}
