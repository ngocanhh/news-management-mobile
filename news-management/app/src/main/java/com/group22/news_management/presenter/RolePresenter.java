package com.group22.news_management.presenter;

import com.group22.news_management.model.RoleModel;
import java.util.List;

public interface RolePresenter {

    void createTable();
    void insert(RoleModel roleModel);
    List<RoleModel> findAll();
}
