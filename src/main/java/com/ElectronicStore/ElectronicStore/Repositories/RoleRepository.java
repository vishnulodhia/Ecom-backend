package com.ElectronicStore.ElectronicStore.Repositories;

import com.ElectronicStore.ElectronicStore.Model.Role;
import com.ElectronicStore.ElectronicStore.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long > {

    Optional<List<Role>> findByRoleName(String role);
}
