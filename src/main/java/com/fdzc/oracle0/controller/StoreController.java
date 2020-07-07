package com.fdzc.oracle0.controller;

import com.fdzc.oracle0.bean.Game;
import com.fdzc.oracle0.bean.User;
import com.fdzc.oracle0.bean.UserType;
import com.fdzc.oracle0.service.IUserService;
import com.fdzc.oracle0.service.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@RestController
public class StoreController {
    @Autowired
    StoreServiceImpl storeService;

    @Autowired
    IUserService userService;

    @RequestMapping("/index")
    public ModelAndView getMainPage() throws SQLException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        List<Game> games = storeService.getLatestGames();
        List<Game> navs = storeService.getNavGames();
        mav.addObject("navs",navs);
        mav.addObject("games",games);
        return mav;
    }

    @RequestMapping("/addToCart/{gid}")
    public void addToCart(HttpServletRequest request, HttpServletResponse response,@PathVariable String gid) throws IOException {
        /*
        TODO:检查用户登陆，没登陆去登录页面，否则直接进入购物车页面
         */
        // 检查用户登录
        Cookie[] cookie = request.getCookies();
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
//                    System.out.println(user.getName());
                    if(storeService.addToCart(gid,user.getUid())){
                        response.sendRedirect("/cart");
                    }else{
                        //添加失败
                        response.sendRedirect("/cart?err=1");
                    }

                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }
    }

    @RequestMapping("/info/{gid}")
    public ModelAndView getInfo(@PathVariable Integer gid) throws SQLException {
        ModelAndView mav = new ModelAndView();
        Game game = storeService.getGame(gid);
        if(game==null){
            mav.setViewName("notFound");;
        }else{
            mav.setViewName("info");
            mav.addObject("game",game);
        }
        return mav;
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(name="keyword",required = false) String keyword, @RequestParam(name="page",required = false) String page){

        ModelAndView mav = new ModelAndView();
        List<Game> games;
        if(keyword==null){
            keyword="";
        }
        games = storeService.getGames(keyword, page);
        mav.setViewName("search");
        mav.addObject("games",games);

        mav.addObject("keyword",keyword);
        return mav;
    }

    @RequestMapping("/cart")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="page",required = false)String page,@RequestParam(name="err",required = false) String err) throws IOException {
        ModelAndView mav = new ModelAndView();
        Cookie[] cookie = request.getCookies();
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
//                    System.out.println(user.getName());
                    List<Game> games = storeService.getCart(user.getUid(),page);
                    mav.setViewName("cart");
                    mav.addObject("games",games);
                    if(err==null){
                        err = "0";
                    }
                    mav.addObject("err",err);
                    return mav;
                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }
        return null;
    }



    @RequestMapping("/purchase/{gid}")
    public String purchase(){
        return "暂时不支持购买功能";
    }

    @RequestMapping("/removeFromCart/{gid}")
    public String removeFromCart(){
        return "暂时不支持操作购物车";
    }

    @RequestMapping("/manage")
    public ModelAndView manage(HttpServletResponse response,HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        Cookie[] cookie = request.getCookies();
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
                    if(user.getType().equals(UserType.ADMINISTER)){
                        mav.setViewName("manage");
                    }else {
                        mav.setViewName("notFound");
                        return mav;
                    }
                    return mav;
                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }
        return null;
    }

    @RequestMapping("/managerSearchGame")
    public ModelAndView managerSearchGame(HttpServletResponse response,HttpServletRequest request,@RequestParam(name="keyword",required = false) String keyword, @RequestParam(name="page",required = false) String page) throws IOException {
        ModelAndView mav = new ModelAndView();
        Cookie[] cookie = request.getCookies();
        List<Game> games;
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
                    if(user.getType().equals(UserType.ADMINISTER)){
                        games = storeService.getGames(keyword, page);
                        mav.setViewName("search");
                        mav.addObject("manage",1);
                        mav.addObject("keyword",keyword);
                        mav.addObject("games",games);
                    }else {
                        mav.setViewName("notFound");
                        return mav;
                    }
                    return mav;
                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }
        return null;
    }

    @RequestMapping("/deactivate/{gid}")
    public void deactivateGame(HttpServletRequest request, HttpServletResponse response,@PathVariable String gid) throws IOException {
        /*
        TODO:检查用户登陆，没登陆去登录页面，否则去编辑页面
         */
        // 检查用户登录
        Cookie[] cookie = request.getCookies();
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
                    if(user.getType().equals(UserType.ADMINISTER)){
                        storeService.stopSell(gid);
                        response.sendRedirect("/manage");
                    }else{
                        response.sendRedirect("/notFound");
                    }

                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }
    }

    @RequestMapping("/edit/{gid}")
    public void editGame(HttpServletRequest request, HttpServletResponse response,Game game) throws IOException {
        /*
        TODO: 检查用户登陆，没登陆去登录页面，否则去编辑页面
         */
        // 检查用户登录
        Cookie[] cookie = request.getCookies();
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
                    if(user.getType().equals(UserType.ADMINISTER)){
                        /*
                        TODO: 打开模版
                         */

                        response.sendRedirect("/manage");
                    }else{
                        response.sendRedirect("/notFound");
                    }

                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }
    }

    @RequestMapping("/add")
    public void addGame(HttpServletRequest request, HttpServletResponse response,Game game) throws IOException {
        Cookie[] cookie = request.getCookies();
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
                    if(user.getType().equals(UserType.ADMINISTER)){
                        /*
                        TODO: 打开模版
                         */
                        System.out.println(game.getGid());
                        System.out.println(game.getName());
                        storeService.addGame(game);
                        response.sendRedirect("/manage");
                    }else{
                        response.sendRedirect("/notFound");
                    }

                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }

    }

    @RequestMapping("/addGame")
    public ModelAndView addGamePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();
        Cookie[] cookie = request.getCookies();
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
                    if(user.getType().equals(UserType.ADMINISTER)){
                        /*
                        TODO: 打开模版
                         */
                        mav.setViewName("add");
                        return mav;
                    }else{
                        response.sendRedirect("/login");
                    }

                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }
        return null;
    }


    @RequestMapping("/managerSearchUser")
    public ModelAndView managerSearchUser(HttpServletResponse response,HttpServletRequest request,@RequestParam(name="keyword",required = false) String keyword, @RequestParam(name="page",required = false) String page) throws IOException {
        ModelAndView mav = new ModelAndView();
        Cookie[] cookie = request.getCookies();
        List<User> users;
        if(cookie==null){
            response.sendRedirect("/login");
        }else{
            User user = null;
            for(Cookie c:cookie){
                if("user".equals(c.getName())){
                    // 有记录登录状态
                    user = userService.getUserByCookie(c.getValue());
                    if(user.getType().equals(UserType.ADMINISTER)){
                        users = userService.getUsers(keyword, page);
                        mav.setViewName("searchUser");
                        mav.addObject("keyword",keyword);
                        mav.addObject("users",users);
                    }else {
                        mav.setViewName("notFound");
                        return mav;
                    }
                    return mav;
                }
            }
            // 有cookie但是没有记录登录状态
            if(user==null) {
                response.sendRedirect("/login");
            }
        }
        return null;
    }
}
