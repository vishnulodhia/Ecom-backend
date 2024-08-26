package com.ElectronicStore.ElectronicStore.Controller;


import com.ElectronicStore.ElectronicStore.DTO.APiResponseMessage;
import com.ElectronicStore.ElectronicStore.DTO.CategoryDto;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.Service.Interface.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@RequestMapping("/Category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value= "/createCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryDto> createCategory( @RequestPart(value = "files",required = true) MultipartFile file,@RequestPart(value="category",required = true) @Valid CategoryDto categoryDto)throws IOException {
        System.out.println("categoryDto:"+categoryDto);
          return new ResponseEntity<>(categoryService.create(categoryDto,file),HttpStatus.CREATED);
    }

//    @PostMapping(value = "/updateCategory/{id}", consumes = {"multipart/form-data"} )
//    public ResponseEntity<CategoryDto> updateCategory(@PathVariable long id,@Valid @ModelAttribute  CategoryDto categoryDto)throws IOException{
//        return new ResponseEntity<>(categoryService.update(categoryDto,id,categoryDto.getFile()),HttpStatus.OK);
//    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<APiResponseMessage> deleteCategory(@PathVariable long id) throws IOException{
        categoryService.delete(id);
        APiResponseMessage message = APiResponseMessage.builder().message("Category deleted successfully").status(HttpStatus.OK).success(true).build();
    return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping("/findAllCategory")
    public ResponseEntity<PageableResponse<CategoryDto>> findAllCategory(@RequestParam(value = "PageNumber" , defaultValue = "0" , required = false) int PageNumber, @RequestParam(value = "PageSize" , defaultValue = "10" , required = false) int PageSize, @RequestParam(value = "SortBy" , defaultValue = "categoryTitle" , required = false) String SortBy, @RequestParam(value = "SortDir" , defaultValue = "asc" , required = false) String SortDir ) {
    return new ResponseEntity<>(categoryService.getAll(PageNumber,PageSize,SortBy,SortDir),HttpStatus.OK);
    }

    @GetMapping("/findCategory/{id}")
    public ResponseEntity<CategoryDto> findCategory(@PathVariable long id) {
      return new ResponseEntity<>(categoryService.get(id),HttpStatus.OK);
    }


}
