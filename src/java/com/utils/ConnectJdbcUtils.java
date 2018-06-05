package com.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author liufei
 */
public class ConnectJdbcUtils {

    public static DatabaseMetaData getDatabaseMetaData(DatabaseMetaData dbMetaData) {

        Connection con;
        try {
            /**
             * Mysql数据库的连接配置
             */
            if (dbMetaData == null) {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/lightsword";
                String user = "root";
                String password = "root";
                con = DriverManager.getConnection(url, user, password);
                return con.getMetaData();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
