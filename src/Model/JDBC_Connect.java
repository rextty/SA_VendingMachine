package Model;

import Resource.MySQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC_Connect {

    private Connection connection;

    public JDBC_Connect() {
        //TODO: 這裡有做修改
        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
            connection = DriverManager.getConnection(MySQL.url, MySQL.username, MySQL.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
