package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Connect {

    private Connection connection;

    public JDBC_Connect() {
        //TODO: 這裡有做修改
        String url = "jdbc:mysql://localhost:3306/Vending_Machine?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
        String username = "root";
        String password = "pA22w0r78877";

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
            connection = DriverManager.getConnection(url, username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
