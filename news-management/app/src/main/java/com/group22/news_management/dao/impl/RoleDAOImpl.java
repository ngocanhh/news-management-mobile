package com.group22.news_management.dao.impl;

import android.database.Cursor;


import com.group22.news_management.dao.RoleDAO;
import com.group22.news_management.database.DatabaseHelper;
import com.group22.news_management.model.RoleModel;

import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private DatabaseHelper databaseHelper;

    public RoleDAOImpl(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ROLES (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100), code VARCHAR(100))";
        databaseHelper.changeData(sql);
    }

    @Override
    public void insert(RoleModel roleModel) {
        String sql = "INSERT INTO ROLES VALUES(null, '" + roleModel.getName() + "', '" + roleModel.getCode() + "' )";
        databaseHelper.changeData(sql);
    }

    @Override
    public List<RoleModel> findAll() {
        List roles = new ArrayList();
        String sql = "SELECT * FROM ROLES;";
        Cursor cursor = databaseHelper.getData(sql);
        while (cursor.moveToNext()){
            RoleModel roleModel = new RoleModel();
            roleModel.setId(cursor.getLong(0));
            roleModel.setName(cursor.getString(1));
            roleModel.setCode(cursor.getString(2));
            roles.add(roleModel);
        }
        return roles;
    }
}
