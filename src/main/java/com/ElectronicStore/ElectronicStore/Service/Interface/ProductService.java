package com.ElectronicStore.ElectronicStore.Service.Interface;

import com.ElectronicStore.ElectronicStore.DTO.CategoryDto;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.DTO.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto, MultipartFile file) throws IOException;

    ProductDto update(ProductDto productDto,long id,MultipartFile file) throws IOException;

    String delete(long id);

    ProductDto get(long id);



    PageableResponse<ProductDto> getFilterProduct(int pageNumber, int pageSize, String sortDir, CategoryDto categoryDto, int minPrice, int maxPrice);

    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getALlLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    PageableResponse<ProductDto> searchByTitle(String productTitle,int pageNumber,int pageSize,String sortBy,String sortDir);

}
