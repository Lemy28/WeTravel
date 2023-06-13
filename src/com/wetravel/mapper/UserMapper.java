package com.wetravel.mapper;

import com.wetravel.model.pojo.User;

import java.util.List;

public interface UserMapper {
    public void saveUser(User user);

public void deleteUser(User user);
public void updateUser(User user);
public List<User> queryUser(User user);
}
