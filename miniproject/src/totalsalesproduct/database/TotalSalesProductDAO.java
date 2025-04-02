package totalsalesproduct.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import db.ConnectionProvider;

public class TotalSalesProductDAO {
	public ArrayList<TotalSalesProductVO> selectAllSalesProduct() {
		ArrayList<TotalSalesProductVO> list = new ArrayList<TotalSalesProductVO>();
		String sql = "select product_id, (select product_name from product p where s.product_id = p.product_id) 제품명, "
				+ " sum(sales_quantity) ,cost_price_at, sale_price_at from sales_product s group by product_id, sale_price_at, cost_price_at";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String productId = rs.getString(1);
				String productName = rs.getString(2);
				int salesCount = rs.getInt(3);
				int costPriceAt = rs.getInt(4);
				int salePriceAt = rs.getInt(5);
				int sumSalePrice = salePriceAt * salesCount;
				int profits = (salePriceAt - costPriceAt) * salesCount;
				double profitsRate = ((double)profits / sumSalePrice) * 100;
				TotalSalesProductVO vo = new TotalSalesProductVO(productId, productName, salesCount, costPriceAt, salePriceAt, sumSalePrice, profits, profitsRate);
				list.add(vo);
			}
			ConnectionProvider.close(conn, stmt);
		} catch (Exception e) {
			System.out.println("예외발생 : " + e);
		}
		return list;
	}
}
