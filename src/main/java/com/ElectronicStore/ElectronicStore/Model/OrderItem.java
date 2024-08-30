package com.ElectronicStore.ElectronicStore.Model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Order_item")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long orderItemId;

    private int quantity;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;

}
