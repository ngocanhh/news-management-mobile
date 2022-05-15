package com.group22.news_management.presenter.impl;

import android.util.Log;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.presenter.LoginPresenter;
import com.group22.news_management.view.LoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(String username, String password) {
        // call api login from server
        NewsManagementAPI.newsManagementAPI.callLoginApi(username, password).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel userModel = response.body();
                if (userModel != null){
                    loginView.loginSuccess(userModel);
                }else {
                    loginView.loginError();
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("error", t.toString());
            }
        });
    }
}
