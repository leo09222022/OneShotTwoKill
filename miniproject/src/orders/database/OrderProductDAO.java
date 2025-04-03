package orders.database;

import db.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDAO {

    public void insertOrderProduct(OrderProductVO orderProduct) {
        String sql = "INSERT INTO OrderProduct (ORDER_ID, PRODUCT_ID, ORDER_QUANTITY) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderProduct.getOrder_id());
            pstmt.setInt(2, orderProduct.getProduct_id());
            pstmt.setInt(3, orderProduct.getOrder_quantity());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public List<OrderProductVO> getOrderProductsByOrderId(int orderId) {
//        List<OrderProductVO> orderProducts = new ArrayList<>();
//        String sql = "SELECT ORDER_ID, PRODUCT_ID, ORDER_QUANTITY FROM OrderProduct WHERE ORDER_ID = ?";
//        try (Connection conn = ConnectionProvider.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, orderId);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                orderProducts.add(new OrderProductVO(
//                        rs.getInt("ORDER_ID"),
//                        rs.getInt("PRODUCT_ID"),
//                        rs.getInt("ORDER_QUANTITY")
//                ));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return orderProducts;
//    }
}
