package orders.database;

import db.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDAO {

    public void insertOrderProduct(OrderProductVO orderProduct) {
        String sql = "INSERT INTO Orders_product (ORDER_ID, PRODUCT_ID, ORDER_QUANTITY) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderProduct.getOrder_id());
            pstmt.setString(2, orderProduct.getProduct_id());
            pstmt.setInt(3, orderProduct.getOrder_quantity());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}