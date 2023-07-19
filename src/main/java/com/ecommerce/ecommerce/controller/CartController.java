package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.DTO.cart.AddToCartDto;
import com.ecommerce.ecommerce.DTO.cart.CartDto;
import com.ecommerce.ecommerce.common.ApiResponse;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.service.AuthenticationService;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    AuthenticationService authenticationService;
    //add to cart api

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token){

        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable("id") Integer id, @RequestParam("token") String token){

        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.deleteCart(id, user);

        return new ResponseEntity<>(new ApiResponse(true, "Deleted from cart"), HttpStatus.OK);

    }
}
