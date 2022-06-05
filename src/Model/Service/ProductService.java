package Model.Service;


import Model.Entity.Product;
import Model.JDBC_Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO:功能內聚
public class ProductService {

    private final JDBC_Connect jdbc_connect;
    private final Connection connection;

    public ProductService() {
        jdbc_connect = new JDBC_Connect();
        connection = jdbc_connect.getConnection();
    }

    public List<Product> getAllProduct() {
        String query = "SELECT * FROM vending_machine.product;";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Product> productList = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getInt("price"));
                product.setName(resultSet.getString("name"));
                product.setImage(resultSet.getBlob("image"));

                productList.add(product);
            }

            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getProductById(int id) {
        String query = "SELECT * FROM vending_machine.product WHERE `id` = " + id + ";";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            Product product = new Product();

            if (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getInt("price"));
                product.setName(resultSet.getString("name"));
                product.setImage(resultSet.getBlob("image"));
                return product;
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProductByProduct(Product product) {
        String query = String.format(
                "UPDATE vending_machine.product " +
                        "quantity = %s " +
                        "price = %s " +
                        "name = %s " +
                        "image = %s " +
                        "WHERE id = %d;", product.getQuantity(), product.getPrice(), product.getName(), product.getImage(), product.getId());

        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProductQuantityBytId(int id, int quantity) {
        String query = String.format(
                "UPDATE vending_machine.product " +
                        "SET quantity = %s " +
                        "WHERE id = %d;", quantity, id);

        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
