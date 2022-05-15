package com.group22.news_management.view;

import com.group22.news_management.model.UserModel;

public interface SignUpView {

    void onSuccess(UserModel userModel);
    void onError();
}
