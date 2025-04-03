package totalordersproduct.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.ConnectionProvider;
import totalsalesproduct.database.TotalSalesProductVO;

public class TotalOrdersProductDAO {
	public ArrayList<TotalOrdersProductVO> selectDailyOrders(String year, String month, String day) {
		ArrayList<TotalOrdersProductVO> list = new ArrayList<TotalOrdersProductVO>();
		String sql  = "select op.product_id, product_name ,order_quantity, order_date, cost_price, total_cost, remarks "
				+ "from orders o, orders_product op, product p "
				+ "where o.order_id = op.order_id and "
				+ "op.product_id = p.product_id and TO_CHAR(order_date, 'YYYY-MM-DD') = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 날짜 형식: YYYY-MM-DD
			pstmt.setString(1, year + "-" + month + "-" + day);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String productId = rs.getString(1);
				String productName = rs.getString(2);
				int orderQuantity = rs.getInt(3);
				Date orderDate = rs.getDate(4);
				int costPriceProduct = rs.getInt(5);
				int totCostPriceProduct = rs.getInt(6);
				String remarks = rs.getString(7);
				TotalOrdersProductVO vo = new TotalOrdersProductVO(productId, productName, orderQuantity, orderDate, costPriceProduct, totCostPriceProduct, remarks);
				list.add(vo);
			}
			ConnectionProvider.close(conn, pstmt, rs);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("예외발생 : " + e);
		}
		return list;
	}
	
}