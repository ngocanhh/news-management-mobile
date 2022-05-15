package com.group22.news_management.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.group22.news_management.dao.UserDAO;
import com.group22.news_management.database.DatabaseHelper;
import com.group22.news_management.model.UserModel;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private DatabaseHelper databaseHelper;

    public UserDAOImpl(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS (id INTEGER PRIMARY KEY AUTOINCREMENT, roleid INTEGER NOT NULL, " +
                " username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, fullname VARCHAR(255), avatar BLOB, " +
                "FOREIGN KEY(roleid) REFERENCES ROLES(id))";
        databaseHelper.changeData(sql);
    }

    @Override
    public List<UserModel> findAll() {
        List users = new ArrayList();
        String sql = "SELECT * FROM USERS;";
        Cursor cursor = databaseHelper.getData(sql);
        while (cursor.moveToNext()){
            users.add(mapping(cursor));
        }
        return users;
    }

    @Override
    public void insert(UserModel userModel) {
        String sql = "INSERT INTO USERS(id, roleId, username, password) VALUES(null, " +
                userModel.getRoleId() + ", '" + userModel.getUsername() + "', '" + userModel.getPassword() + "')";
        databaseHelper.changeData(sql);
    }

    @Override
    public UserModel findByUsername(String username) {
        String sql = "SELECT * FROM USERS WHERE username = '" + username + "'";
        Cursor cursor = databaseHelper.getData(sql);
        if(cursor.moveToNext()){
            return mapping(cursor);
        }
        return null;
    }

    @Override
    public void updateAvatar(long id, byte[] avatar) {
        String sql = "UPDATE USERS SET avatar = ? WHERE id = ?";
        SQLiteStatement statement = databaseHelper.getWritableDatabase().compileStatement(sql);
        statement.clearBindings();
        statement.bindBlob(1, avatar);
        statement.bindLong(2, id);
        statement.executeUpdateDelete();
    }

    @Override
    public UserModel findById(long id) {
        String sql = "SELECT * FROM USERS WHERE id = " + id;
        Cursor cursor = databaseHelper.getData(sql);
        if(cursor.moveToNext()){
            return mapping(cursor);
        }
        return null;
    }

    private UserModel mapping(Cursor cursor){
        UserModel userModel = new UserModel();
        userModel.setId(cursor.getLong(0));
        userModel.setRoleId(cursor.getLong(1));
        userModel.setUsername(cursor.getString(2));
        userModel.setPassword(cursor.getString(3));
        userModel.setFullName(cursor.getString(4));
//        userModel.setAvatar(cursor.getBlob(5));
        return userModel;
    }

}
