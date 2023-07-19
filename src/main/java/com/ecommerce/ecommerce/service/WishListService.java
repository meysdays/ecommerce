package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.DTO.ProductDto;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.model.WishList;
import com.ecommerce.ecommerce.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductService productService;
    public void createdWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishListForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for (WishList wishList: wishLists){
            productDtos.add(productService.getProductDto(wishList.getProduct()));
        }
        return productDtos;
    }
}
