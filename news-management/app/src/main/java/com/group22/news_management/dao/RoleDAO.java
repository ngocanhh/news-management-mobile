package com.group22.news_management.dao;

import com.group22.news_management.model.RoleModel;
import java.util.List;

public interface RoleDAO {

    void createTable();
    void insert(RoleModel roleModel);
    List<RoleModel> findAll();
}
