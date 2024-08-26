package com.ElectronicStore.ElectronicStore.Model;


import jakarta.persistence.*;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Product")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long productId;


    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false,referencedColumnName = "categoryId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category categoryId;

    @Column(nullable = false)
    private String productTitle;

    @Column(nullable = false,length = 10000)
    private String productDescription;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String productSpecifications;

    @Column(nullable = false)
    private int productPrice;

    @Column(nullable = false)
    private int productDiscountPrice;

    @Column(nullable = false)
    private int productQuantity;

    @Column(nullable = false)
    @CurrentTimestamp
    private Date addedDate;

    @Column(nullable = false)
    private boolean isLive;

    @Column(nullable = false)
    private boolean stock;

    @Column(unique = true,nullable = false)
    private String fileName;

    @Column(nullable = false,length = 600)
    private String url;
}
