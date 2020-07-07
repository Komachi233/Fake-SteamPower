package com.fdzc.oracle0.dao;

import com.fdzc.oracle0.bean.Game;
import com.fdzc.oracle0.bean.User;
import com.fdzc.oracle0.bean.UserType;
import com.fdzc.oracle0.utils.DBUtils;
import oracle.jdbc.OracleCallableStatement;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements IUserDao{
    private List<User> users;
    private void setTestUser(){
        users = new ArrayList<>();
        users.add(new User(1001, UserType.DEFAULT ,"TypeAlpha","123456aa","",10000,"TypeAlpha12333"));
        users.add(new User(1002, UserType.DEFAULT ,"Komachi","123456aa","",10000,"Komachi12333"));
        users.add(new User(1003, UserType.DEFAULT ,"Kirin","123456aa","",10000,"Kirin12333"));
        users.add(new User(1004, UserType.ADMINISTER ,"G_fat","123456aa","",10000,"G_fat12333"));
    }

    @Override
    public List<Game> getUserGames(int uid) {
        return null;
    }

    @Override
    public void addUser(User user) {
        Connection conn = DBUtils.getConn();
        String SQL_ADD_USERS = "insert into TBL_USERS values(t_user_id_seq.nextval,?,?,?,'/',0,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL_ADD_USERS);
            switch (user.getType()){
                case DEFAULT:
                    pstmt.setInt(1,0);
                    break;
                case ADMINISTER:
                    pstmt.setInt(1,1);
                    break;
                case DEACTIVATED:
                    pstmt.setInt(1,2);
                    break;
                case BANNED:
                    pstmt.setInt(1,3);
                    break;
            }
            pstmt.setString(2,user.getName());
            pstmt.setString(3,user.getPassword());
            // 还管他什么加密算法？
            pstmt.setString(4,user.getName()+"1234555");

            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void changePass(int uid) {

    }

    @Override
    public void banUser(int uid) {
        Connection conn = DBUtils.getConn();
        String SQL_DELETE_USERS = "delete from TBL_USERS where userid = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_USERS);
            pstmt.setInt(1,uid);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User getUser(int uid) {

        CallableStatement call = null;
        ResultSet rs = null;
        User user = new User();
        Connection conn = DBUtils.getConn();

        try {
            call = conn.prepareCall("{ call GetUserByID(?,?) }");
            call.setInt(1, uid);
            call.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            rs = ((OracleCallableStatement) call).getCursor(2); //获取结果

            user.setUid(rs.getInt("UID"));

            switch (rs.getInt("TYPE")){
                case 0:
                    user.setType(UserType.DEFAULT);
                    break;
                case 1:
                    user.setType(UserType.ADMINISTER);
                    break;
                case 2:
                    user.setType(UserType.DEACTIVATED);
                    break;
                case 3:
                    user.setType(UserType.BANNED);
                    break;
            }

            user.setName(rs.getString("NAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setAvatar(rs.getString("AVATAR"));
            user.setBalance(rs.getInt("BALANCE"));
            user.setCookie(rs.getString("COOKIE"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;


/*        setTestUser();
        for(User u:users){
            if(uid==u.getUid()){
                return u;
            }
        }
        return null;*/
    }

    @Override
    public User getUser(String name) {

        CallableStatement call = null;
        ResultSet rs = null;
        User user = new User();
        Connection conn = DBUtils.getConn();

        try {
            call = conn.prepareCall("{ call GetUserByName(?,?) }");
            call.setString(1, name);
            call.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            rs = ((OracleCallableStatement) call).getCursor(2); //获取结果
            rs.next();
            user.setUid(rs.getInt("USERID"));
            switch (rs.getInt("TYPE")){
                case 0:
                    user.setType(UserType.DEFAULT);
                    break;
                case 1:
                    user.setType(UserType.ADMINISTER);
                    break;
                case 2:
                    user.setType(UserType.DEACTIVATED);
                    break;
                case 3:
                    user.setType(UserType.BANNED);
                    break;
            }
            user.setName(rs.getString("NAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setAvatar(rs.getString("AVATAR"));
            user.setBalance(rs.getInt("SURPLUS"));
            user.setCookie(rs.getString("COOKIE"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;


        /*// 设置测试用代码
        setTestUser();
        for(User u:users){
            if(name.equals(u.getName())){
                return u;
            }
        }
        return null;*/
    }

    @Override
    public User getUserByCookie(String cookie) {

        CallableStatement call = null;
        ResultSet rs = null;
        User user = new User();
        Connection conn = DBUtils.getConn();

        try {
            call = conn.prepareCall("{ call GetUserByCookie(?,?) }");
            call.setString(1, cookie);
            call.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);  //需要注册输出的参数
            call.execute();    //执行存储过程
            rs = ((OracleCallableStatement) call).getCursor(2); //获取结果
            rs.next();
            user.setUid(rs.getInt("USERID"));
            switch (rs.getInt("TYPE")){
                case 0:
                    user.setType(UserType.DEFAULT);
                    break;
                case 1:
                    user.setType(UserType.ADMINISTER);
                    break;
                case 2:
                    user.setType(UserType.DEACTIVATED);
                    break;
                case 3:
                    user.setType(UserType.BANNED);
                    break;
            }
            user.setName(rs.getString("NAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setAvatar(rs.getString("AVATAR"));
            user.setBalance(rs.getInt("SURPLUS"));
            user.setCookie(rs.getString("COOKIE"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

/*        setTestUser();
        for (User u:users){
            if(cookie.equals(u.getCookie())){
                return u;
            }
        }
        return null;*/
    }

    @Override
    public List<User> getUsers(String keyword,int page) {
        List<User> users = new ArrayList<>();
        Connection conn = DBUtils.getConn();
        String SQL_SELECT_USERS = "select * from TBL_USERS where rownum >= ? and rownum <= ? and name like ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_USERS);
            pstmt.setInt(1,(page-1)*10+1);
            pstmt.setInt(2,page*10);
            pstmt.setString(3,"%"+keyword+"%");
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()){
                User user = new User();
                user.setUid(rset.getInt("USERID"));
                switch (rset.getInt("TYPE")){
                    case 0:
                        user.setType(UserType.DEFAULT);
                        break;
                    case 1:
                        user.setType(UserType.ADMINISTER);
                        break;
                    case 2:
                        user.setType(UserType.DEACTIVATED);
                        break;
                    case 3:
                        user.setType(UserType.BANNED);
                        break;
                }
                user.setName(rset.getString("NAME"));
                user.setPassword(rset.getString("PASSWORD"));
                user.setAvatar(rset.getString("AVATAR"));
                user.setBalance(rset.getInt("SURPLUS"));
                user.setCookie(rset.getString("COOKIE"));
                users.add(user);
            }
            //update: insert,delete,update. query: select
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;

    }

    @Override
    public boolean setBalance(int uid) {
        return false;
    }
}
