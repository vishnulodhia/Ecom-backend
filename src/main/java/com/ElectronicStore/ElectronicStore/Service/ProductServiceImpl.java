package com.ElectronicStore.ElectronicStore.Service;


import com.ElectronicStore.ElectronicStore.DTO.CategoryDto;
import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.DTO.ProductDto;
import com.ElectronicStore.ElectronicStore.Exception.ResourceNotFoundException;
import com.ElectronicStore.ElectronicStore.Model.Category;
import com.ElectronicStore.ElectronicStore.Model.Product;
import com.ElectronicStore.ElectronicStore.Repositories.ProductRepository;
import com.ElectronicStore.ElectronicStore.Service.Interface.ProductService;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDto create(ProductDto productDto,MultipartFile file) throws IOException {
        if(file.isEmpty())
            throw new ResourceNotFoundException("Category cover image not found");

        productDto.setUrl(fileService.uploadFileFirebase(file,"product"));
        Product product = modelMapper.map(productDto,Product.class);
        System.out.println("product: "+ product);
        return modelMapper.map(productRepository.save(product),ProductDto.class);
    }

//    @Override
//    public CategoryDto create(CategoryDto categoryDto, MultipartFile file) throws IOException {
//        if(file.isEmpty())
//            throw new ResourceNotFoundException("Category cover image not found");
//
//        categoryDto.setUrl(fileService.uploadFileFirebase(file,"category"));
//        return modelMapper.map(categoryRepositories.save(modelMapper.map(categoryDto, Category.class)), CategoryDto.class);
//    }
//@Override
//public CategoryDto update(CategoryDto categoryDto, long id,MultipartFile file) throws IOException {
//    Category category = categoryRepositories.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
//    if(!categoryDto.getFileName().equals(category.getFileName())){
//        category.setFileName(categoryDto.getFileName());
//        category.setUrl(fileService.updateFileFirebase(file,"category",category.getUrl()));
//    }
//    category.setCategoryDescription(categoryDto.getCategoryDescription());
//    category.setCategoryTitle(categoryDto.getCategoryTitle());
//    category.setFileName(categoryDto.getFileName());
//    return modelMapper.map(categoryRepositories.save(modelMapper.map(categoryDto,Category.class)), CategoryDto.class);
//}




    @Override
    public ProductDto update(ProductDto productDto, long id,MultipartFile file) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource not found"));
        if(!productDto.getFileName().equals(product.getFileName())){
        product.setFileName(productDto.getFileName());
        product.setUrl(fileService.updateFileFirebase(file,"product",product.getUrl()));
    }
       System.out.println("productDTO"+productDto.isLive());

        product.setProductTitle(productDto.getProductTitle());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductPrice(productDto.getProductPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductDiscountPrice(productDto.getProductDiscountPrice());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setProductSpecifications(productDto.getProductSpecifications());
        product.setCategoryId(productDto.getCategoryId());
        return modelMapper.map(productRepository.save(product),ProductDto.class);
    }

    @Override
    public String delete(long id) {
    productRepository.deleteById(id);
    return "product deleted successfully";
    }

    @Override
    public ProductDto get(long id) {
        return modelMapper.map( productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found")),ProductDto.class);
    }



    @Override
    public PageableResponse<ProductDto> getFilterProduct(int pageNumber, int pageSize, String sortDir, CategoryDto categoryDto, int minPrice, int maxPrice) {
//        Sort sort = (sortDir.equals("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        if(categoryDto==null)
           return Pagination.getPageableResponse(productRepository.getProductDiscountPrice(minPrice,maxPrice,pageable),ProductDto.class);
        else if (minPrice==0 && maxPrice==0) {
           return Pagination.getPageableResponse(productRepository.findByCategoryId(modelMapper.map(categoryDto,Category.class),pageable),ProductDto.class);
        }
        return Pagination.getPageableResponse(productRepository.getProductCategoryAndDiscountPrice(categoryDto.getCategoryId(),minPrice,maxPrice,pageable),ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equals("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return Pagination.getPageableResponse(productRepository.findAll(pageable),ProductDto.class);
    }



    @Override
    public PageableResponse<ProductDto> getALlLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equals("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return Pagination.getPageableResponse(productRepository.findByisLiveTrue(pageable),ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String productTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equals("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return Pagination.getPageableResponse(productRepository.findByproductTitleContaining(productTitle,pageable),ProductDto.class);
    }
}
