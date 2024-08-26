package com.ElectronicStore.ElectronicStore.DTO;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse <T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;

    private long totalElement;

    private  int totalPage;

    private boolean isLastPage;

}
