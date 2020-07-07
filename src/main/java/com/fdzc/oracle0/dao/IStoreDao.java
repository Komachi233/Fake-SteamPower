package com.fdzc.oracle0.dao;

import com.fdzc.oracle0.bean.Game;

import java.sql.SQLException;
import java.util.List;

public interface IStoreDao {
    // 获取某个游戏的搜索结果 - 一页10个
    public List<Game> getGames(String keyWord, int page);

    // 获取其中一个游戏的所有信息 - 记得查tag
    public Game getGame(int gid) throws SQLException;

    // 加入购物车 加入成功返回true，已在库中or已在购物车中or已下架返回false
    public boolean addToCart(int gid,int uid) throws SQLException;

    // 查询购物车 - 一页十个
    public List<Game> getCart(int uid, int page);

    // 获取最新的10款游戏
    public List<Game> getLatestGames() throws SQLException;

    // 获取三个游戏渲染首页
    public List<Game> getNavGames() throws SQLException;

    // 管理添加游戏
    public boolean addGame(Game game);

    // 增加tag 增加成功返回true ，有同名tag返回false
    public boolean addTag(int gid,String tagName);

    // 停止销售某款游戏 停止成功 true，失败false
    public boolean stopSell(int gid);

    // 修改某款游戏内容
    public boolean changeGame(Game game);

}
