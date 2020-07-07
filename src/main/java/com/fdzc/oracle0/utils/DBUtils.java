package com.fdzc.oracle0.utils;

import java.sql.*;

public class DBUtils {
    // 连接串,第一部分什么连接串odbc,jdbc;第二部分什么厂商的数据库;第三部分什么数据库的什么类型
    // @后接 数据库IP地址:监听器端口:SID (oracle数据库版本)
//    private static final String CONN_STR = "jdbc:oracle:thin:@192.168.1.155:4004:orcl";
//    private static final String USERNAME = "kirin";
//    private static final String PWD = "oracle";

    private static final String CONN_STR="jdbc:oracle:thin:@192.168.1.188:1521:orcl";
    private static final String USERNAME="tester0";
    private static final String PWD="abc12345";

//    private static final String CONN_STR="jdbc:oracle:thin:@169.254.25.188:1521:orcl";
//    private static final String USERNAME="tester";
//    private static final String PWD = "abc123";

    /***
     * 获取连接
     *
     * @return
     */
    public static Connection getConn() {
        Connection conn = null;
        try {
            // Class.forName()在类路径中寻找有无此类存在(寻找驱动)，会抛出一个ClassNotFound异常
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 产生连接，会抛出一个SQL异常
            conn = DriverManager.getConnection(CONN_STR, USERNAME, PWD);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    /***
     * 释放资源
     *
     * @param conn
     * @param pstmt
     * @param cstmt
     * @param rset
     */
    public static void releaseRes(Connection conn, PreparedStatement pstmt,CallableStatement cstmt, ResultSet rset) {

        try {
            if (rset != null) {
                rset.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (cstmt != null) {
                cstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
