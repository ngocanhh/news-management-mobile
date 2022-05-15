package com.group22.news_management.presenter.impl;

import android.util.Log;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.presenter.SignUpPresenter;
import com.group22.news_management.view.SignUpView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenterImpl implements SignUpPresenter {

    private SignUpView signUpView;

    public SignUpPresenterImpl(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void signUp(String username, String password, String phoneNumber) {
        NewsManagementAPI.newsManagementAPI.callSignUpApi(username, password, phoneNumber).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel userModel = response.body();
                if(userModel != null){
                    signUpView.onSuccess(userModel);
                }else {
                    signUpView.onError();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("error: " , t.toString());
            }
        });
    }
}
