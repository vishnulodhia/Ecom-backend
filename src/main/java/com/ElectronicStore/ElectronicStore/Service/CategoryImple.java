package com.ElectronicStore.ElectronicStore.Service;

import com.ElectronicStore.ElectronicStore.DTO.CategoryDto;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.Exception.ResourceNotFoundException;
import com.ElectronicStore.ElectronicStore.Model.Category;
import com.ElectronicStore.ElectronicStore.Repositories.CategoryRepositories;
import com.ElectronicStore.ElectronicStore.Service.Interface.CategoryService;

import com.ElectronicStore.ElectronicStore.util.Pagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CategoryImple implements CategoryService {
    @Autowired
    private CategoryRepositories categoryRepositories;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileServiceImpl fileService;
    @Override
    public CategoryDto create(CategoryDto categoryDto, MultipartFile file) throws IOException {
        if(file.isEmpty())
            throw new ResourceNotFoundException("Category cover image not found");

        categoryDto.setUrl(fileService.uploadFileFirebase(file,"category"));
        return modelMapper.map(categoryRepositories.save(modelMapper.map(categoryDto,Category.class)), CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, long id,MultipartFile file) throws IOException {
     Category category = categoryRepositories.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
     if(!categoryDto.getFileName().equals(category.getFileName())){
         category.setFileName(categoryDto.getFileName());
         category.setUrl(fileService.updateFileFirebase(file,"category",category.getUrl()));
     }
     category.setCategoryDescription(categoryDto.getCategoryDescription());
     category.setCategoryTitle(categoryDto.getCategoryTitle());
     category.setFileName(categoryDto.getFileName());
        return modelMapper.map(categoryRepositories.save(modelMapper.map(categoryDto,Category.class)), CategoryDto.class);
    }

    @Override
    public void delete(long id) throws IOException {
        Category category = categoryRepositories.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        fileService.deleteFileFirebase(category.getUrl(),"category");
        categoryRepositories.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equals("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable page = PageRequest.of(pageNumber,pageSize, sort);
        return Pagination.getPageableResponse(categoryRepositories.findAll(page),CategoryDto.class);
    }

    @Override
    public CategoryDto get(long id) {
        return modelMapper.map(categoryRepositories.findById(id), CategoryDto.class);
    }
}
