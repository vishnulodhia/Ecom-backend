package com.ElectronicStore.ElectronicStore.Model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Orders")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long orderId;

    @Column(nullable = false,length = 50)
    private String orderStatus;

    @Column(nullable = false,length = 50)
    private String paymentStatus;

    @Column(nullable = false)
    private long orderAmount;

    @Column(nullable = false,length = 1000)
    private String address;

    @Column(nullable = false,length = 10)
    private String phoneNo;

    @Column(nullable = false)
    private Date orderDate;

    @Column
    private Date deliverDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

}
