package com.ElectronicStore.ElectronicStore.Model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private long userId;

    @Column(name ="User_name",nullable = false)
    private String name;

    @Column(name="User_email",unique = true,nullable = false)
    private String email;

    @Column(name="User_password",nullable = false,length=60)

    private String password;

    @Column(name = "User_phoneno",length=10,unique = true,nullable = false)
    private String phoneno;


   @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
   private List<Role> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println("Role inside GrantAuthority:"+role);
        for (Role r : role) {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
