package Model.Service;


import Model.Entity.PreOrder;
import Model.JDBC_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PreOrderService {

    private JDBC_Connect jdbc_connect = new JDBC_Connect();

    public PreOrderService() {}

    public PreOrder getPreOrder(int qrcode) {
        //  getPreOrderByQRCode
        String query = String.format("SELECT * FROM vending_machine.pre_order WHERE id = %d;", qrcode);

        PreOrder preOrder = new PreOrder();
        try {
            ResultSet resultSet = jdbc_connect.getConnection().createStatement().executeQuery(query);

            while (resultSet.next()) {
                preOrder.setId(resultSet.getInt("id"));
                preOrder.setExpireDate(resultSet.getString("expireDate"));
                preOrder.setTake(resultSet.getBoolean("isTake"));
                preOrder.setMachineSerialNumber(resultSet.getInt("machineSerialNumber"));
                preOrder.setUserId(resultSet.getInt("userId"));
            }

            return preOrder;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePreOrder(PreOrder preOrder) {
        String sql = String.format(
                "UPDATE vending_machine.pre_order SET isTake = %b WHERE id = %d;", preOrder.getTake(), preOrder.getId()
        );

        try {
            jdbc_connect.getConnection().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
