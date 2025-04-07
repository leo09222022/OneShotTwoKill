package orders.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import db.ConnectionProvider;
import product.database.ProductVO;
import totalsalesproduct.database.TotalSalesProductVO;

public class OrderReceiptDAO {
	public OrderReceiptVO receiptsForOrders (int orderId, String productId, int quantity) {
		OrderReceiptVO vo = new OrderReceiptVO();
		String sql = "select p.product_name,o.ORDER_QUANTITY, p.cost_price "
				+ "from orders_product o, product p "
				+ "where o.product_id= p.product_id "
				+ "and o.product_id = ? "
				+ "and o.order_id=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, productId);
			pstmt.setInt(2, orderId);
			
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				vo.setOrderId(orderId);
				vo.setProductId(productId);
				vo.setProductName(rs.getString(1));
				vo.setOrderQuantity(quantity);
				vo.setCostPrice(rs.getInt(3));
				vo.setCostPerProduct(quantity*rs.getInt(3));
				
			}
			ConnectionProvider.close(conn, pstmt, rs);
		
		} catch (Exception e) {
			System.out.println("receiptsForOrders error: " + e.getMessage());

		}
		return vo;
	}
}