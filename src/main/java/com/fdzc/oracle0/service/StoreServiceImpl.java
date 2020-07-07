package com.fdzc.oracle0.service;

import com.fdzc.oracle0.bean.Game;
import com.fdzc.oracle0.dao.IStoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class StoreServiceImpl implements IStoreService{
    @Autowired
    IStoreDao storeDao;

    @Override
    public List<Game> getLatestGames() throws SQLException {
        //返回最新上架的十个游戏
        return storeDao.getLatestGames();
    }

    @Override
    public List<Game> getNavGames() throws SQLException {

        return storeDao.getNavGames();
    }

    @Override
    public List<Game> getGames(String keyword, String page) {
        List<Game> games = new ArrayList<>();
        int p;
        // 返回gid为-1的说明参数查询错误
        try {
            if(page==null){
                page = "1";
            }
            p = Integer.parseInt(page);
            games = storeDao.getGames(keyword,p);
        }catch (NumberFormatException e){
            // System.out.println("数字错误");
            Game g = new Game();
            g.setGid(-1);
            games.add(g);
        }
        return games;
    }

    @Override
    public Game getGame(int gid) throws SQLException {
        return storeDao.getGame(gid);
    }

    @Override
    public List<Game> getCart(int uid, String page) {
        List<Game> games = new ArrayList<>();

        // 返回gid为-1的说明参数查询错误
        try {
            if(page==null){
                page = "1";
            }
            int p = Integer.parseInt(page);
            games = storeDao.getCart(uid,p);
        }catch (NumberFormatException e){
            // System.out.println("数字错误");
            Game g = new Game();
            g.setGid(-1);
            games.add(g);
        }
        return games;
    }

    @Override

    public boolean addToCart(String gid, int uid) {
        try {
            int g = Integer.parseInt(gid);
            return storeDao.addToCart(g,uid);
        }catch (NumberFormatException | SQLException e) {

            return false;
        }
    }

    @Override
    public void addGame(Game game) {
        System.out.println(game.getName());
        storeDao.addGame(game);
    }

    @Override
    public boolean add(int gid, String tagName) {
        System.out.println(gid+" "+tagName);
        return storeDao.addTag(gid,tagName);
    }

    @Override
    public boolean stopSell(String gid) {
        try {
            int g = Integer.parseInt(gid);
            System.out.println(gid);
            return storeDao.stopSell(g);
        }catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public boolean changeGame(Game game) {
        return storeDao.changeGame(game);
    }
}
