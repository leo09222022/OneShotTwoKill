package payment.database;
/*
 * 커넥션은 공통으로 관리하는 것으로 불러온다.
 * 결제필요 요소들이 다 입력되고 결제 버튼을 누르는 순간 인서트 한다.
 * 
 * 
 * 판매 테이블: 판매 아이디 2001번의 판매 날짜, 총 판매 금액, 구매했을 때 사용했던 카드 정보를 입력
 * insert into sales(sales_id, sales_date, sales_total, card_num, expiration_date, card_ver) values (2001, '2025-03-15', 30000, '1234567812345678', '2028-12-31', '12345678');
 * 
 * 판매상품 테이블: 판매 아이디, 상품 아이디, 상품수량, 판매가, 원가를 입력
 * insert into sales_product(sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) values (2001, '8801056158816', 5, 1500, 800);
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import db.ConnectionProvider;
import sales.database.SalesProductVO;
import sales.database.SalesVO;

public class PaymentDAO {
	
	// 판매 내역 추가
	public int insertCardInfo(SalesVO sv, SalesProductVO spv) {
		int result = 1;
		String sql = "insert into sales("
				+ " sales_id, sales_date, sales_total, "
				+ " card_num, expiration_date, card_ver) "
				+ " values (?, sysdate, ?, ?, ?, ?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
		
			// pstm.set
			pstm.setInt(1, spv.getSalesId());
			// pstm.setDate(2, sv.getSalesDate());
			pstm.setInt(2, sv.getSalesTotal());
			pstm.setString(3, sv.getCardNum());
			pstm.setDate(4, sv.getExpirationDate());
			pstm.setString(5, sv.getCardVer());
			
			result = pstm.executeUpdate();
			
			ConnectionProvider.close(conn, pstm);
			
			System.out.println("결제진행");
		} catch (Exception e) {
			System.out.println("예외발생 : "+e.getMessage());
		}
		
		return result;
	}
	
	
	
	// 판매 상품 추가
	public int insertCardProduct(SalesProductVO spv) {
		int result = 1;
		String sql = "insert into sales_product("
				+ " sales_id, product_id, sales_quantity, "
				+ " sale_price_at, cost_price_at) "
				+ " values (?, ?, ?, ?, ?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
		
			// pstm.set
			pstm.setInt(1, spv.getSalesId());
			pstm.setString(2, spv.getProductId());
			pstm.setInt(3, spv.getSalesQuantity());
			pstm.setInt(4, spv.getSalePriceAt());
			pstm.setInt(5, spv.getCostPriceAt());
			
			result = pstm.executeUpdate();
			
			ConnectionProvider.close(conn, pstm);
			
			System.out.println("판매상품 인서트");
		} catch (Exception e) {
			System.out.println("예외발생 : "+e.getMessage());
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
}
