package com.ElectronicStore.ElectronicStore.Service.Interface;

import com.ElectronicStore.ElectronicStore.DTO.AddCartRequest;
import com.ElectronicStore.ElectronicStore.DTO.CartDto;
import com.ElectronicStore.ElectronicStore.Model.Cart;

public interface CartService {
    // If cart is not there it will create and add item to it
    CartDto addItemToCart(long userId, AddCartRequest request);

    // remove item from cart
   void removeItemFromCart(long cartItemId);


   void clearCart(long UserId);

   CartDto fetchCart(long userId);

}
