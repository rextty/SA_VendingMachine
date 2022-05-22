package Model.Service;

import Model.Entity.Message;
import Model.JDBC_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReplenishmentService {

    private final JDBC_Connect jdbc_connect;

    public ReplenishmentService() {
        jdbc_connect = new JDBC_Connect();
    }

    public List<Message> getAllMessage() {
        String query = "SELECT * FROM vending_machine.message;";

        try {
            ResultSet resultSet = jdbc_connect.getConnection().createStatement().executeQuery(query);

            List<Message> messageList = new ArrayList<>();

            while (resultSet.next()) {
                Message message = new Message();
                message.setProductId(resultSet.getString("productId"));
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
        String query = String.format("INSERT INTO vending_machine.message (productId, message, msgType, state) " +
                "VALUES ('%s', '%s', '%d', %b);", message.getProductId(), message.getMessage(), message.getMsgType(), false);

        try {
            jdbc_connect.getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkExistMessageByProductIdAndMsgType(Message message) {
        String query = String.format("SELECT *  FROM vending_machine.message " +
                "WHERE productId = %s AND msgType = %d;", message.getProductId(), message.getMsgType()
        );

        try {
            ResultSet resultSet = jdbc_connect.getConnection().createStatement().executeQuery(query);
            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
