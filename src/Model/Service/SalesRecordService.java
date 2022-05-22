package Model.Service;

import Model.Entity.Message;
import Model.Entity.SalesRecord;
import Model.JDBC_Connect;

import java.sql.SQLException;

public class SalesRecordService {

    private final JDBC_Connect jdbc_connect;

    public SalesRecordService() {
        jdbc_connect = new JDBC_Connect();
    }

    public void addSalesRecord(SalesRecord salesRecord) {
        String query = String.format("INSERT INTO vending_machine.sales_record (productId, date) " +
                "VALUES (%s, '%s');", salesRecord.getProductId(), salesRecord.getDate()
        );

        try {
            jdbc_connect.getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
