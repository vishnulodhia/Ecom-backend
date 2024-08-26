package com.ElectronicStore.ElectronicStore.util;

import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class Pagination {

    
        public static <U,V> PageableResponse<V>  getPageableResponse(Page<U> page, Class<V> type){

        List<U> entity = page.getContent();
        List<V> dtoList = entity.stream().map(Object-> new ModelMapper().map(Object,type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPage(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    
}
