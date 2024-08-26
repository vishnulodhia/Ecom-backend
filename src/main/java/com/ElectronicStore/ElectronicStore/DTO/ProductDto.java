package com.ElectronicStore.ElectronicStore.DTO;

import com.ElectronicStore.ElectronicStore.Model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {

    private long productId;

    private Category categoryId;

    @NotBlank
    @Size(min=2, max=15, message = "product title must be of minimum 3 character")
    private String productTitle;

    @NotBlank(message = "Description is required")
    @Size(min=2, message = "product description must be of minimum 3 character")
    private String productDescription;

    @NotBlank(message = "Description is required")
    @Size(min=2, message = "product Specifications must be a json")
    private String productSpecifications;

    @Min(value=0, message = "product price must be greater than zero" )
    private int productPrice;

    @Min(value=0, message = "product discount price must be greater than zero" )
    private int productDiscountPrice;

   @Min(value=0, message = "product quantity must be greater than zero" )
    private int productQuantity;

    private Date addedDate;

    private boolean isLive;

    private boolean stock;

    private String fileName;

    private String url;
}
