package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.DTO.ProductDto;
import com.ecommerce.ecommerce.DTO.ResponseDtos;
import com.ecommerce.ecommerce.common.ApiResponse;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.model.WishList;
import com.ecommerce.ecommerce.service.AuthenticationService;
import com.ecommerce.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    // save product as wishlist item

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token){

        // authenticate the token
        authenticationService.authenticate(token);

        //find the user

        User user = authenticationService.getUser(token);

        //save the item in wishlist

        WishList wishList = new WishList(user, product);

        wishListService.createdWishList(wishList);

        ApiResponse apiResponse = new ApiResponse(true,"Added to wishlist");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    // get all wishlist item for a user

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {

        // authenticate the token
        authenticationService.authenticate(token);

        //find the user

        User user =  authenticationService.getUser(token);

        List<ProductDto> productDtos = wishListService.getWishListForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}
