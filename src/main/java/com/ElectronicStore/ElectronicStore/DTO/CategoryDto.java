package com.ElectronicStore.ElectronicStore.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class CategoryDto {

    private long categoryId;


    @Size(min=2, max=15, message = "title must be of minimum 4 character")
    private String categoryTitle;


    private String categoryDescription;


    private String fileName;

    private String url;
}
