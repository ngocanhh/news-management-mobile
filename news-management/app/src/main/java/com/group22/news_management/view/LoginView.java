package com.group22.news_management.view;

import com.group22.news_management.model.UserModel;

public interface LoginView {

    void loginSuccess(UserModel userModel);
    void loginError();
}
