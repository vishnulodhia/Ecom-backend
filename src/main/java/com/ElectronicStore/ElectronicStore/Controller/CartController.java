package com.ElectronicStore.ElectronicStore.Controller;

import com.ElectronicStore.ElectronicStore.DTO.APiResponseMessage;
import com.ElectronicStore.ElectronicStore.DTO.AddCartRequest;
import com.ElectronicStore.ElectronicStore.DTO.CartDto;
import com.ElectronicStore.ElectronicStore.Service.Interface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Cart")
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("addItemsToCart/{userId}")
    public ResponseEntity<APiResponseMessage> addItemsToCart(@PathVariable long userId, @RequestBody AddCartRequest request){
    APiResponseMessage aPiResponseMessage = new APiResponseMessage();
    CartDto cartDto = cartService.addItemToCart(userId,request);
        if(cartDto==null){
            aPiResponseMessage.setMessage("The required quantity is more than the existing quantity.");
            aPiResponseMessage.setSuccess(false);
            aPiResponseMessage.setStatus(HttpStatus.OK);
        }

        else {
            aPiResponseMessage.setMessage("Item added to cart!");
            aPiResponseMessage.setSuccess(true);
            aPiResponseMessage.setStatus(HttpStatus.OK);
        }

     return new ResponseEntity<>(aPiResponseMessage, HttpStatus.OK);

    }


    @GetMapping("removeItemFromCart/{itemId}")
    public ResponseEntity<APiResponseMessage> removeItemFromCart(@PathVariable long itemId){
        cartService.removeItemFromCart(itemId);
        APiResponseMessage response = APiResponseMessage.builder()
                .message("Item is removed")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("clearCart/{userId}")
    public ResponseEntity<APiResponseMessage> clearCart(@PathVariable long userId){
        cartService.clearCart(userId);
        APiResponseMessage response = APiResponseMessage.builder()
                .message("Cart is clear")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getCart/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable long userId){
        return new ResponseEntity<>(cartService.fetchCart(userId), HttpStatus.OK);
    }



}
