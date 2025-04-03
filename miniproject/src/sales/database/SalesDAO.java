package sales.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.ConnectionProvider;
import product.database.ProductVO;

public class SalesDAO {
	// 바코드(product_id)로 조회
	public ProductVO findById(String productId) {
	    ProductVO product = null;
	    String sql = "select * from product where product_id=?";
	    
	    try {
	        Connection conn = ConnectionProvider.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, productId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            product = new ProductVO(
	                rs.getString("product_id"),
	                rs.getInt("category_id"),
	                rs.getString("product_name"),
	                rs.getInt("optimal_stock"),
	                rs.getInt("stock"),
	                rs.getInt("cost_price"),
	                rs.getInt("sale_price"),
	                rs.getDate("created_at"),
	                rs.getDate("updated_at")
	            );
	        }
	        ConnectionProvider.close(conn, pstmt, rs);
	    } catch (Exception e) {
	        System.out.println("예외발생: " + e.getMessage());
	    }

	    return product;
	}

}
