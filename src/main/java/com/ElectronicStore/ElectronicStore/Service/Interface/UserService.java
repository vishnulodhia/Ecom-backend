package com.ElectronicStore.ElectronicStore.Service.Interface;

import com.ElectronicStore.ElectronicStore.DTO.PageableResponse;
import com.ElectronicStore.ElectronicStore.DTO.UserDto;

import java.util.List;

public interface UserService {

UserDto createUser(UserDto userDto);
UserDto updateUser(UserDto userDto,long id);

void deleteUser(long id);

PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String SortBy, String SortDir);

UserDto getUserById(long id);

UserDto getUserByEmail(String email);

List<UserDto> searchUser(String username);

}
