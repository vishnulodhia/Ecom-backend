package com.ElectronicStore.ElectronicStore.Repositories;

import com.ElectronicStore.ElectronicStore.Model.Category;
import com.ElectronicStore.ElectronicStore.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByproductTitleContaining(String subtitle,Pageable pageable);

    Page<Product> findByisLiveTrue(Pageable pageable);

   Page<Product> findByCategoryId(Category categoryId,Pageable pageable);

   Page<Product> findByProductDiscountPriceBetween(int minPrice,int maxPrice,Pageable pageable);
   Page<Product> findByCategoryIdAndProductDiscountPriceBetween(Category categoryId, int minPrice, int maxPrice, Pageable pageable);


    @Query(value = "SELECT * FROM product WHERE category_id=:catId AND product_discount_price BETWEEN :minPrice AND :maxPrice",nativeQuery = true)
    Page<Product> getProductCategoryAndDiscountPrice(Long catId,int minPrice,int maxPrice,Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE product_discount_price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    Page<Product> getProductDiscountPrice(int minPrice, int maxPrice, Pageable pageable);


}
