package com.group22.news_management.presenter.impl;

import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.dao.UserDAO;
import com.group22.news_management.dao.impl.UserDAOImpl;
import com.group22.news_management.database.DatabaseHelper;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.presenter.UserPresenter;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenterImpl implements UserPresenter {

    private UserDAO userDAO;

    public UserPresenterImpl(DatabaseHelper databaseHelper) {
        this.userDAO = new UserDAOImpl(databaseHelper);
    }

    @Override
    public void createTable() {
        userDAO.createTable();
    }

    @Override
    public void insert(UserModel userModel) {
        if (userDAO.findByUsername(userModel.getUsername()) == null){
            String hash = BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt());
            userModel.setPassword(hash);
            userDAO.insert(userModel);
        }
    }

    @Override
    public List<UserModel> findAll() {
        return userDAO.findAll();
    }

    @Override
    public void updateAvatar(long id, String avatar) {
//        NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
//        newsManagementAPI.callUpdateAvatarApi(id, avatar).enqueue(new Callback<UserModel>() {
//            @Override
//            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<UserModel> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public UserModel findById(long id) {
        return userDAO.findById(id);
    }
}
