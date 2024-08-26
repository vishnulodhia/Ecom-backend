package com.ElectronicStore.ElectronicStore.Service.Interface;

import com.ElectronicStore.ElectronicStore.DTO.CategoryDto;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto, MultipartFile file) throws IOException;
    CategoryDto update(CategoryDto categoryDto,long id,MultipartFile file) throws IOException;

    void delete(long id) throws IOException;

    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDi);

    CategoryDto get(long id);
}
