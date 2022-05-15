package com.group22.news_management.dao;

import com.group22.news_management.model.UserModel;
import java.util.List;

public interface UserDAO {

    void createTable();
    List<UserModel> findAll();
    void insert(UserModel userModel);
    UserModel findByUsername(String username);
    void updateAvatar(long id, byte[] avatar);
    UserModel findById(long id);
}
