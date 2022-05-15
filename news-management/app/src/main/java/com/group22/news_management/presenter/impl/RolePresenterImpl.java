package com.group22.news_management.presenter.impl;

import com.group22.news_management.dao.RoleDAO;
import com.group22.news_management.dao.impl.RoleDAOImpl;
import com.group22.news_management.database.DatabaseHelper;
import com.group22.news_management.model.RoleModel;
import com.group22.news_management.presenter.RolePresenter;
import java.util.List;

public class RolePresenterImpl implements RolePresenter {

    private RoleDAO roleDAO;

    public RolePresenterImpl(DatabaseHelper databaseHelper) {
        this.roleDAO = new RoleDAOImpl(databaseHelper);
    }

    @Override
    public void createTable() {
        roleDAO.createTable();
    }

    @Override
    public void insert(RoleModel roleModel) {
        roleDAO.insert(roleModel);
    }

    @Override
    public List<RoleModel> findAll() {
        return roleDAO.findAll();
    }
}
