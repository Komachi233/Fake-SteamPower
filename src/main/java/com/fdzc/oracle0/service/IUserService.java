package com.fdzc.oracle0.service;

import com.fdzc.oracle0.bean.Game;
import com.fdzc.oracle0.bean.User;

import java.util.List;

public interface IUserService {
    public User checkUserLogin(String username, String password);
    public User getUserByCookie(String cookie);
    // 获取用户库存游戏
    public List<Game> getUserGames(int uid);
    // 添加用户: 注册 or 生成新的管理
    public void addUser(User user);
    // 修改密码
    public void changePass(int uid,String password);
    // 封禁用户 or 注销账户
    public void banUser(String uid);
    // 查询用户
    public User getUser(int uid);
    public List<User> getUsers(String keyword,String page);
}
