package Model.Service;


import Model.Entity.Product;
import Model.JDBC_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private JDBC_Connect jdbc_connect;

    public ProductService() {
        jdbc_connect = new JDBC_Connect();
    }

    public List<Product> getAllProduct() {
        String query = "SELECT * FROM vending_machine.product;";

        try {
            ResultSet resultSet = jdbc_connect.getConnection().createStatement().executeQuery(query);

            List<Product> productList = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getInt("price"));
                product.setName(resultSet.getString("name"));
                product.setImage(resultSet.getString("image"));

                productList.add(product);
            }

            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
