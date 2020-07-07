package com.fdzc.oracle0.service;

import com.fdzc.oracle0.bean.Game;

import java.sql.SQLException;
import java.util.List;

public interface IStoreService {
    public Game getGame(int gid) throws SQLException;
    public List<Game> getLatestGames() throws SQLException;
    public List<Game> getGames(String keyword, String page);
    public List<Game> getNavGames() throws SQLException;
    // 查询购物车 - 一页十个
    public List<Game> getCart(int uid, String page);
    // 加入购物车 加入成功返回true，已在库中or已在购物车中or已下架返回false
    public boolean addToCart(String gid,int uid);

    // 管理添加游戏
    public void addGame(Game game);

    // 增加tag 增加成功返回true ，有同名tag返回false
    public boolean add(int gid,String tagName);

    // 停止销售某款游戏 停止成功 true，失败false
    public boolean stopSell(String gid);

    // 修改某款游戏内容
    public boolean changeGame(Game game);
}
