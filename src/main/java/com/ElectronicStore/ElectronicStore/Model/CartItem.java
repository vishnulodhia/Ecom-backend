package com.ElectronicStore.ElectronicStore.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;


}
