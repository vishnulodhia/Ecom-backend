package com.ElectronicStore.ElectronicStore.Repositories;

import com.ElectronicStore.ElectronicStore.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositories extends JpaRepository<User,Long > {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String keyword);
}
