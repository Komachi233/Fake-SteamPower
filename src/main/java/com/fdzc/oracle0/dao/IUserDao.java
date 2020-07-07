package com.fdzc.oracle0.dao;

import com.fdzc.oracle0.bean.Game;
import com.fdzc.oracle0.bean.User;

import java.util.List;

public interface IUserDao {
    // 获取用户库存游戏
    public List<Game> getUserGames(int uid);
    // 添加用户: 注册 or 生成新的管理
    public void addUser(User user);
    // 修改密码
    public void changePass(int uid);
    // 封禁用户 or 注销账户
    public void banUser(int uid);
    // 查询用户
    public User getUser(int uid);
    public User getUser(String name);
    public List<User> getUsers(String keyword,int page);
    // 由cookie获取用户
    public User getUserByCookie(String cookie);
    // 改余额
    public boolean setBalance(int uid);



}
