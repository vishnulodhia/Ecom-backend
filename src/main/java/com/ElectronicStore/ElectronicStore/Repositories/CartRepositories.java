package com.ElectronicStore.ElectronicStore.Repositories;

import com.ElectronicStore.ElectronicStore.Model.Cart;
import com.ElectronicStore.ElectronicStore.Model.Category;
import com.ElectronicStore.ElectronicStore.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CartRepositories extends JpaRepository<Cart,Long> {
    Optional<Cart> findByuser(User user);
}
