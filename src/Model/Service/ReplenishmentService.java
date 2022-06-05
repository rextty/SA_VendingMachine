package Model.Service;

import Model.Entity.Message;
import Model.JDBC_Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReplenishmentService {

    private final JDBC_Connect jdbc_connect;
    private final Connection connection;

    public ReplenishmentService() {
        jdbc_connect = new JDBC_Connect();
        connection = jdbc_connect.getConnection();
    }

    public List<Message> getAllMessage() {
        String query = "SELECT * FROM vending_machine.message;";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Message> messageList = new ArrayList<>();

            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt("id"));
                message.setMessage(resultSet.getString("message"));
                message.setMsgType(resultSet.getInt("msgType"));
                message.setNotify(resultSet.getBoolean("state"));

                messageList.add(message);
            }

            return messageList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessage(Message message) {
        String query = String.format("INSERT INTO vending_machine.message (message, msgType, state) " +
                "VALUES ('%s', '%d', %b);", message.getMessage(), message.getMsgType(), false);

        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkExistMessageByMsgAndMsgType(Message message) {
        String query = String.format("SELECT *  FROM vending_machine.message " +
                "WHERE message = '%s' AND msgType = %d;", message.getMessage(), message.getMsgType()
        );

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
