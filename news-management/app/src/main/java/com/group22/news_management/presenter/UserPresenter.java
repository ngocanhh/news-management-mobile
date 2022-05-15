package com.group22.news_management.presenter;

import com.group22.news_management.model.UserModel;
import java.util.List;

public interface UserPresenter {

    void createTable();
    void insert(UserModel userModel);
    List<UserModel> findAll();
    void updateAvatar(long id, String avatar);
    UserModel findById(long id);
}
