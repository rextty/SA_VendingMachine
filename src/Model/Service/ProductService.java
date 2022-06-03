package Model.Service;


import Model.Entity.Product;
import Model.JDBC_Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO:功能內聚
public class ProductService {

    private final JDBC_Connect jdbc_connect;

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
                product.setProductId(resultSet.getString("productId"));
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

    public Product getProductByProductId(String productId) {
        String query = "SELECT * FROM vending_machine.product WHERE `productId` = " + productId + ";";

        try {
            ResultSet resultSet = jdbc_connect.getConnection().createStatement().executeQuery(query);

            Product product = new Product();

            if (resultSet.next()) {
                product.setProductId(resultSet.getString("productId"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getInt("price"));
                product.setName(resultSet.getString("name"));
                product.setImage(resultSet.getString("image"));
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
                        "SET productId = %s " +
                        "quantity = %s " +
                        "price = %s " +
                        "name = %s " +
                        "image = %s " +
                        "WHERE productId = %s;", product.getProductId(), product.getQuantity(), product.getPrice(), product.getName(), product.getImage(), product.getProductId());

        try {
            jdbc_connect.getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProductQuantityByProductId(String productId, int quantity) {
        String query = String.format(
                "UPDATE vending_machine.product " +
                        "SET quantity = %s " +
                        "WHERE productId = %s;", quantity, productId);

        try {
            jdbc_connect.getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
