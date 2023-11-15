package com.example.DongAisa.mapper;


import com.example.DongAisa.domain.User;
import com.example.DongAisa.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserName(user.getUserName());
        userDto.setUserPassword(user.getUserPassword());

        return userDto;

    }
    public static User convertToModel(UserDto userDto){
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserName(userDto.getUserName());
        user.setUserPassword(userDto.getUserPassword());
        return  user;
    }

    public static List<UserDto> convertToDtoList(List<User> user){
        return user.stream().map(UserMapper::convertToDto).collect(Collectors.toList());
    }
}
