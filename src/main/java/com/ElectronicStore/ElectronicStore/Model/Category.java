package com.ElectronicStore.ElectronicStore.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="category")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long categoryId;

    @Column(nullable = false,length = 60)
    private String categoryTitle;

    @Column(unique = true,nullable = false,length = 50)
    private String categoryDescription;

   @Column(unique = true,nullable = false)
   private String fileName;

    @Column(nullable = false,length = 600)
    private String url;

}
