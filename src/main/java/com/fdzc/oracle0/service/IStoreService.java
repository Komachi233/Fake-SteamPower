package com.fdzc.oracle0.service;

import com.fdzc.oracle0.bean.Game;

import java.util.List;

public interface IStoreService {
    public Game getGame(int gid);
    public List<Game> getLatestGames();
    public List<Game> getGames();
    public List<Game> getGames(String keyword, String page);
    public List<Game> getNavGames();
}
