package com.fdzc.oracle0.dao;

import com.fdzc.oracle0.bean.Game;
import com.fdzc.oracle0.utils.DBUtils;
import oracle.jdbc.OracleCallableStatement;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StoreDaoImpl implements IStoreDao {
    //仅作为测试用！
    private List<Game> games;
    private List<Integer> cart = new ArrayList<>();

    @Override
    public boolean addGame(Game game) {     //添加一个游戏到商城
        CallableStatement call = null;
        boolean result=false;
        Connection conn = DBUtils.getConn();

        String SQL_ADD_GAME = "insert into TBL_GAMES values(t_game_id_seq.nextval,?,?,?,?,?,?,?,?,1)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL_ADD_GAME);
            pstmt.setString(1,game.getName());
            pstmt.setString(2,game.getDev());
            pstmt.setString(3, game.getPub());
            pstmt.setInt(4,game.getPrice());
            pstmt.setInt(5,game.getDiscount());
            pstmt.setString(6, game.getSummary());
            pstmt.setString(7,game.getPubDate());
            pstmt.setString(8,game.getMainImg());

            pstmt.executeUpdate();
            result=true;
            //update: insert,delete,update. query: select
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    //pstmt-预备SQl语句
    private static final String SQL_SEARCH_BY_KEYWORD = "select * from tbl_games where name like ? or dev like ? or pub like ?";

    public List<Game> getGames(String keyWord, int page) {
        Connection conn = DBUtils.getConn();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<Game> objList = new ArrayList<Game>();

        try {
            pstmt =conn.prepareStatement(SQL_SEARCH_BY_KEYWORD);
            pstmt.setString(1,"%"+keyWord+"%");
            pstmt.setString(2,"%"+keyWord+"%");
            pstmt.setString(3,"%"+keyWord+"%");
            rset = pstmt.executeQuery();
            while(rset.next()){
                Game obj = new Game();
                obj.setGid(rset.getInt("gid"));
                obj.setName(rset.getString("name"));
                obj.setDev(rset.getString("dev"));
                obj.setPub(rset.getString("pub"));
                obj.setPrice(rset.getInt("price"));
                obj.setDiscount(rset.getInt("discount"));
                obj.setSummary(rset.getString("summary"));
                obj.setPubDate(rset.getString("publish_date"));
                obj.setMainImg(rset.getString("main_image"));
                obj.setStatus(rset.getInt("status")==1?true:false);
                objList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.releaseRes(conn,pstmt,null,rset);
        }

        return objList;
    }

    // 测试用的比对代码，真正使用时，直接在数据库查询清楚
    private boolean checkGame(Game g, String k) {
        boolean result = false;
        // 看看在不在tag里
        for (String s : g.getTag()) {
            if (s.toLowerCase().indexOf(k.toLowerCase()) != -1) {
                return true;
            }
        }

        if (g.getName().toLowerCase().indexOf(k.toLowerCase()) != -1) {
            result = true;
        } else if (g.getSummary().toLowerCase().indexOf(k.toLowerCase()) != -1) {
            result = true;
        } else if (g.getDev().toLowerCase().indexOf(k.toLowerCase()) != -1) {
            result = true;
        } else if (g.getPub().toLowerCase().indexOf(k.toLowerCase()) != -1) {
            result = true;
        }

        return result;
    }

    @Override
    public boolean addTag(int gid, String tagName) {    //管理员给某游戏添加一个标签
        boolean result=false;
        ResultSet rs = null;
        CallableStatement call = null;
        Connection conn = DBUtils.getConn();

        try {
            call = conn.prepareCall("{ call AddGameTag(?,?,?) }"); //执行3条sql操作：1.根据tagName从Tag表中查询获取tag的ID
            call.setInt(1, gid);                        //               2.在游戏Tag关系表中增加一条新的记录
            call.setString(2, tagName);                 //               3.查询该条记录，返回的游标绑定这条查询语句
            call.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            rs = ((OracleCallableStatement) call).getCursor(3); //获取结果
            if(rs.next()){
                result=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean stopSell(int gid) {
        Connection conn = DBUtils.getConn();
        CallableStatement call = null;

        try {
            call = conn.prepareCall("{ call StopSell(?) }");
            call.setInt(1, gid);
//            call.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean changeGame(Game game) {
        return false;
    }

    /////////////////////////////////////KOMACHI///////////////////////////////////////
    @Override
    public List<Game> getLatestGames() {    // 获取最新的10款游戏
        CallableStatement call = null;
        ResultSet rs = null;
        List<Game> lst = new ArrayList<>();

        Connection conn = DBUtils.getConn();
        try {
            call = conn.prepareCall("{ call GET10GAMESID(?) }");
            call.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            rs = ((OracleCallableStatement) call).getCursor(1); //获取结果集
            while (rs.next()) {
                lst.add(new Game(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10) == 1 ? true : false));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.releaseRes(conn,null,call,rs);
        return lst;
    }

    @Override
    public List<Game> getNavGames() throws SQLException {
        CallableStatement call = null;
        ResultSet rs = null;
        List<Game> navGames = new ArrayList<>();

        Connection conn = DBUtils.getConn();

        try {
            call = conn.prepareCall("{ call Get3GamesRandomlyWithoutTags(?) }");
            call.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            rs = ((OracleCallableStatement) call).getCursor(1); //获取结果集
            System.out.println(rs);
            while (rs.next()) {
                Game game = new Game();
                game.setGid(rs.getInt("GID"));
                game.setName(rs.getString("NAME"));
                game.setDev(rs.getString("DEV"));
                game.setPub(rs.getString("PUB"));
                game.setPrice(rs.getInt("PRICE"));
                game.setDiscount(rs.getInt("DISCOUNT"));
                game.setSummary(rs.getString("SUMMARY"));
                game.setPubDate(rs.getString("PUBLISH_DATE"));
                game.setMainImg(rs.getString("MAIN_IMAGE"));
                game.setStatus(rs.getBoolean("STATUS"));


        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(navGames.size());
        DBUtils.releaseRes(conn,null,call,rs);
        return navGames;
    }

    @Override
    public Game getGame(int gid) throws SQLException {   // 根据 ID 获取某个游戏的所有信息 - 记得查tag
        CallableStatement call = null;
        ResultSet rs = null;

        Connection conn = DBUtils.getConn();

        try {
            call = conn.prepareCall("{ call GetGameInfoWithoutTags(?,?) }");
            call.setInt(1, gid);
            call.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            rs = ((OracleCallableStatement) call).getCursor(2); //获取结果
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(rs.next()){
            Game game = new Game();
            game.setGid(rs.getInt("GID"));

            game.setName(rs.getString("NAME"));
            game.setDev(rs.getString("DEV"));
            game.setPub(rs.getString("PUB"));
            game.setPrice(rs.getInt("PRICE"));
            game.setDiscount(rs.getInt("DISCOUNT"));
            game.setSummary(rs.getString("SUMMARY"));
            game.setPubDate(rs.getString("PUBLISH_DATE"));
            game.setMainImg(rs.getString("MAIN_IMAGE"));
            game.setStatus(rs.getBoolean("STATUS"));

            List<String> tag = getGameTags(gid);
            game.setTag(tag);

            return game;
        }else{
            return null;
        }

    }

    private List<String> getGameTags(int gid) throws SQLException {
        CallableStatement call = null;
        ResultSet rs = null;
        List<String> lst = new ArrayList<String>();

        Connection conn = DBUtils.getConn();
        call = conn.prepareCall("{ call GetGameTags(?,?) }");   //根据ID获取某个游戏的tag集
        call.setInt(1, gid);
        call.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
        call.execute();    //执行存储过程
        rs = ((OracleCallableStatement) call).getCursor(2); //获取结果集
        while (rs.next()) {
            lst.add(rs.getString("NAME"));
        }

        return lst;
    }

    @Override
    public List<Game> getCart(int uid, int page) {  // 查询购物车 - 一页十个
        CallableStatement call = null;
        ResultSet rs = null;
        List<Game> lst = new ArrayList<Game>();
        Connection conn = DBUtils.getConn();
        try {
            call = conn.prepareCall("{ call GetCartGamesID(?,?,?) }");
            call.setInt(1, uid);
            call.setInt(2, page);
            call.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            rs = ((OracleCallableStatement) call).getCursor(3); //获取结果集
            while (rs.next()) {
                Game game = getGame(rs.getInt("GID"));
                game.setTag(getGameTags(rs.getInt("GID")));
                lst.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lst;
    }

    //@Override

    public boolean addToCart(int gid, int uid) throws SQLException {
        boolean result=false;

        CallableStatement call1 = null;
        ResultSet rs = null;
        Connection conn = DBUtils.getConn();


        try {
            call1 = conn.prepareCall("{ call CartAddingCheck(?,?,?) }");    //查询商品是否已入库（Status=1 or 2）或已在购物车（Status=3）
            call1.setInt(1, gid);
            call1.setInt(2, uid);
            call1.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call1.execute();
            rs = ((OracleCallableStatement) call1).getCursor(3);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        if(!rs.next()){  //当商品为未拥有状态时

            CallableStatement call2 = null;
            try {
                call2 = conn.prepareCall("{ call AddToCart(?,?,?) }");  //执行2条sql操作：1.添加记录到UGR表，设置状态status=3（在购物车）
                call2.setInt(1, gid);                        //               2.查询该条记录是否存在于UGR表，游标绑定这条查询语句
                call2.setInt(2, uid);

                call2.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
                call2.execute();

                result=true;    //则将结果设定为true

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBUtils.releaseRes(conn,null,call1,rs);
        return result;
    }
    /////////////////////////////////////KOMACHI///////////////////////////////////////

}
