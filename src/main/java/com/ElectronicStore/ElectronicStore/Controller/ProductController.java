package com.ElectronicStore.ElectronicStore.Controller;


import com.ElectronicStore.ElectronicStore.DTO.APiResponseMessage;
import com.ElectronicStore.ElectronicStore.DTO.CategoryDto;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.DTO.ProductDto;


import com.ElectronicStore.ElectronicStore.Service.Interface.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/Product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;



//    @GetMapping("/findAllUser")
//    public ResponseEntity<PageableResponse<UserDto>> findAllUser(@RequestParam(value = "PageNumber" , defaultValue = "0" , required = false) int PageNumber, @RequestParam(value = "PageSize" , defaultValue = "10" , required = false) int PageSize, @RequestParam(value = "SortBy" , defaultValue = "name" , required = false) String SortBy, @RequestParam(value = "SortDir" , defaultValue = "asc" , required = false) String SortDir ){
//        System.out.println("PageNumber " + PageNumber+"PageSize"+PageSize);
//        return new ResponseEntity<>(userService.getAllUser(PageNumber,PageSize,SortBy,SortDir), HttpStatus.OK);
//    }
//
//
    @PostMapping(value = "/createProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createProduct(@RequestPart(value = "files",required = true) MultipartFile file, @RequestPart(value="product",required = true) @Valid ProductDto productDto) throws IOException {
        return new ResponseEntity<>(productService.create(productDto,file), HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateProduct/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> updateProduct(@RequestPart(value = "files",required = true) MultipartFile file,@RequestPart(value="product",required = true) @Valid ProductDto productDto,@PathVariable long id) throws IOException {
        return new ResponseEntity<>(productService.update(productDto,id,file), HttpStatus.CREATED);
    }

    @GetMapping("/findAllProduct")
    public ResponseEntity<PageableResponse<ProductDto>> findAllProduct(@RequestParam(value = "PageNumber" , defaultValue = "0" , required = false) int PageNumber, @RequestParam(value = "PageSize" , defaultValue = "10" , required = false) int PageSize, @RequestParam(value = "SortBy" , defaultValue = "productTitle" , required = false) String SortBy, @RequestParam(value = "SortDir" , defaultValue = "asc" , required = false) String SortDir ){
        System.out.println("PageNumber " + PageNumber+"PageSize"+PageSize);
        return new ResponseEntity<>(productService.getAll(PageNumber,PageSize,SortBy,SortDir), HttpStatus.OK);
    }


//    @GetMapping("/findProduct/{id}")
//    public ResponseEntity<ProductDto> findProduct(@PathVariable long id){
//        return new ResponseEntity<>(productService.get(id), HttpStatus.OK);
//    }

    @GetMapping("/findAllLiveProduct")
    public ResponseEntity<PageableResponse<ProductDto>> findAllLiveProduct(@RequestParam(value = "PageNumber" , defaultValue = "0" , required = false) int PageNumber, @RequestParam(value = "PageSize" , defaultValue = "10" , required = false) int PageSize, @RequestParam(value = "SortBy" , defaultValue = "productTitle" , required = false) String SortBy, @RequestParam(value = "SortDir" , defaultValue = "desc" , required = false) String SortDir ){
        return new ResponseEntity<>(productService.getALlLive(PageNumber,PageSize,SortBy,SortDir), HttpStatus.OK);
    }

    @GetMapping("/findProductTitle/{title}")
    public ResponseEntity<PageableResponse<ProductDto>> findProductTitle(@RequestParam(value = "PageNumber" , defaultValue = "0" , required = false) int PageNumber, @RequestParam(value = "PageSize" , defaultValue = "10" , required = false) int PageSize, @RequestParam(value = "SortBy" , defaultValue = "name" , required = false) String SortBy, @RequestParam(value = "SortDir" , defaultValue = "asc" , required = false) String SortDir,@RequestParam(value = "title" , defaultValue = "asc" , required = false) String title){
        System.out.println("PageNumber " + PageNumber+"PageSize"+PageSize);
        return new ResponseEntity<>(productService.searchByTitle(title,PageNumber,PageSize,SortBy,SortDir), HttpStatus.OK);
    }

    @GetMapping("findFilerProduct")
    public ResponseEntity<PageableResponse<ProductDto>> findFilerProduct(@RequestParam(value = "categoryId" , defaultValue = "0" , required = false) int categoryId,@RequestParam(value = "minPrice" , defaultValue = "0" , required = false) int minPrice,@RequestParam(value = "maxPrice" , defaultValue = "0" , required = false) int maxPrice,@RequestParam(value = "PageNumber" , defaultValue = "0" , required = false) int PageNumber, @RequestParam(value = "PageSize" , defaultValue = "10" , required = false) int PageSize, @RequestParam(value = "SortDir" , defaultValue = "asc" , required = false) String SortDir,@RequestParam(value = "title" , defaultValue = "asc" , required = false) String title){

        if( categoryId==0 && minPrice!=0 && maxPrice!=0) {
            System.out.println("CategoryId: "+categoryId);
            return new ResponseEntity<>(productService.getFilterProduct(PageNumber,PageSize,SortDir,null,minPrice,maxPrice), HttpStatus.OK);
        }
        CategoryDto categoryDto = CategoryDto.builder().categoryId(categoryId).build();
        return new ResponseEntity<>(productService.getFilterProduct(PageNumber, PageSize, SortDir,categoryDto,minPrice,maxPrice), HttpStatus.OK);
}



    @GetMapping("findProduct/{id}")
    public ResponseEntity<ProductDto> findProduct(@PathVariable long id){
        return new ResponseEntity<>(productService.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<APiResponseMessage> deleteProduct(@PathVariable long id){
        APiResponseMessage message = APiResponseMessage.builder().message(productService.delete(id)).status(HttpStatus.OK).success(true).build();
        productService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }






}
