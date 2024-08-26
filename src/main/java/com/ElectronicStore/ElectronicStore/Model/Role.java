package com.ElectronicStore.ElectronicStore.Model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="role")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private long roleId;

    @Column(nullable = false,length = 100)
    private String roleName;

   @Column(nullable = false,length =150)
   private String permission;

}
