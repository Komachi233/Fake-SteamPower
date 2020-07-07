package com.fdzc.oracle0.service;

import com.fdzc.oracle0.bean.Game;
import com.fdzc.oracle0.bean.User;
import com.fdzc.oracle0.bean.UserType;
import com.fdzc.oracle0.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    IUserDao userDao;

    @Override
    public User checkUserLogin(String username,String password) {
        User u = userDao.getUser(username);
        if(u==null){
            return null;
        }
        if(u.getType()== UserType.BANNED||u.getType()==UserType.DEACTIVATED){
            return null;
        }else if(password.equals(u.getPassword())){
            return u;
        }
        return null;
    }

    @Override
    public User getUserByCookie(String cookie) {
        return userDao.getUserByCookie(cookie);
    }

    @Override
    public List<Game> getUserGames(int uid) {
        return null;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void changePass(int uid,String password) {
        userDao.changePass(uid,password);
    }

    @Override
    public void banUser(String uid) {
        try {
            int u = Integer.parseInt(uid);
            userDao.banUser(u);
        }catch (NumberFormatException e){

        }
    }


    @Override
    public User getUser(int uid) {
        return userDao.getUser(uid);
    }

    @Override
    public List<User> getUsers(String keyword, String page) {
        List<User> users = new ArrayList<>();
        int p;
        try {
            if(page==null){
                page = "1";
            }
            p = Integer.parseInt(page);
            users = userDao.getUsers(keyword,p);
        }catch (NumberFormatException e){
            // System.out.println("数字错误");
            return null;
        }
        return users;
    }
}
