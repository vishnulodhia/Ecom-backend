package com.ElectronicStore.ElectronicStore.Service;

import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.DTO.UserDto;
import com.ElectronicStore.ElectronicStore.Exception.ResourceNotFoundException;
import com.ElectronicStore.ElectronicStore.Model.Role;
import com.ElectronicStore.ElectronicStore.Model.User;
import com.ElectronicStore.ElectronicStore.Repositories.RoleRepository;
import com.ElectronicStore.ElectronicStore.Repositories.UserRepositories;
import com.ElectronicStore.ElectronicStore.Service.Interface.UserService;
import com.ElectronicStore.ElectronicStore.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {

        List<Role> role = roleRepository.findByRoleName("ROLE_NORMAL").orElseThrow(()-> new ResourceNotFoundException("Role not found"));
        System.out.println("Role name: "+roleRepository.findByRoleName("ROLE_NORMAL").orElseThrow(()-> new ResourceNotFoundException("Role not found")));
        User user = User.builder()
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .phoneno(userDto.getPhoneno())
                .role(role)
                .build();

        User saveduser = userRepositories.save(user);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, long id) {
        User user = userRepositories.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        User updateUser = userRepositories.save(user);
        return entityToDto(updateUser);
    }

    @Override
    public void deleteUser(long id) {
     userRepositories.deleteById(id);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int size, String SortBy, String SortDir) {
        System.out.println("Pagenumber service:"+pageNumber+"Size"+size);
        Sort sort = (SortDir.equals("desc"))?(Sort.by(SortBy).descending()):(Sort.by(SortBy).ascending());
        Pageable page = PageRequest.of(pageNumber,size, sort);
        return Pagination.getPageableResponse(userRepositories.findAll(page),UserDto.class);
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userRepositories.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found exception"));
        UserDto userdto = entityToDto(user);
        return userdto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
         User user = userRepositories.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User NOt found "));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String username) {
        List<User> users = userRepositories.findByNameContaining(username);
        List<UserDto> userdtos = users.stream().map(user-> entityToDto(user)).collect(Collectors.toList());
        return userdtos;
    }

    UserDto entityToDto(User user){
        UserDto userDto = UserDto.builder().userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneno(user.getPhoneno())
                .build();
        return userDto;
    }

    User dtoToEntity(UserDto userDto){
        User user = User.builder().userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneno(userDto.getPhoneno())
                .build();

        return user;
    }


}
