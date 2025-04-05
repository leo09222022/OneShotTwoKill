package payment.database;
/*
 * 커넥션은 공통으로 관리하는 것으로 불러온다.
 * 결제필요 요소들이 다 입력되고 결제 버튼을 누르는 순간 인서트 한다.
 * 
 * 판매 테이블: 판매 아이디 2001번의 판매 날짜, 총 판매 금액, 구매했을 때 사용했던 카드 정보를 입력
 * insert into sales(sales_id, sales_date, sales_total, card_num, expiration_date, card_ver) values (2001, '2025-03-15', 30000, '1234567812345678', '2028-12-31', '12345678');
 * 
	 ! 시퀀스 생성 ================================================
	create 
	  sequence sales_seq
	  start with 2007
	  increment by 1
	; 
	
	! 시퀀스 삭제 ================================================
	drop sequence sales_seq;
	
	! 시퀀스 적용 ================================================
	insert into board(no,title,writer,content,regdate) values(seq_board.nextval,);
	
	insert into 
	  sales(sales_id, sales_date, sales_total, card_num, expiration_date, card_ver) 
	  values (sales_seq.nextval, sysdate, 30000, '1234567812345678', '2028-12-31', '12345679');
 * 
 * 
 * 판매상품 테이블: 판매 아이디, 상품 아이디, 상품수량, 판매가, 원가를 입력
 * insert into sales_product(sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) values (2001, '8801056158816', 5, 1500, 800);
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import db.ConnectionProvider;
import sales.database.SalesProductVO;
import sales.database.SalesVO;

public class PaymentDAO {
	
	// 판매 내역 추가
	public int insertCardInfo(SalesVO sv) {
		int result = -1;
		int generatedSalesId = -1;
		
		String sql = "insert into sales("
				+ " sales_id, sales_date, sales_total, "
				+ " card_num, expiration_date, card_ver) "
				+ " values (sales_seq.nextval, sysdate, ?, ?, ?, ?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql, new String[] {"sales_id"}); // sales_id 가져오기  
			
			// pstm.setInt(1, spv.getSalesId());
			// pstm.setDate(2, sv.getSalesDate());
			pstm.setInt(1, sv.getSalesTotal());
			pstm.setString(2, sv.getCardNum());
			pstm.setDate(3, sv.getExpirationDate());
			pstm.setString(4, sv.getCardVer());
			
			result = pstm.executeUpdate();
			
			// 시퀀스로  생성된 sales_id 가져오기 
			if(result == 1) {
				ResultSet rsgk = pstm.getGeneratedKeys();
				if(rsgk.next()) {
					generatedSalesId = rsgk.getInt(1);
					sv.setSalesId(generatedSalesId);
				}
				rsgk.close();				
			}
			ConnectionProvider.close(conn, pstm);
			System.out.println("결제진행, sales_id"+generatedSalesId);
		} catch (Exception e) {
			System.out.println("예외발생(판매): "+e.getMessage());
		}
		return generatedSalesId; // result 로 넘길 경우 인서트 성공여부만 남기고, 'sales_id' 를 리턴하지 않음. 
	}
	
	
	
	// 판매 상품 추가
	public int insertCardProduct(List<SalesProductVO> spv) {
		int result = 1;
		String sql = "insert into sales_product("
				+ " sales_id, product_id, sales_quantity, "
				+ " sale_price_at, cost_price_at) "
				+ " values ( ?, ?, ?, ?, ?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
		
			for(SalesProductVO item : spv) {
				pstm.setInt(1, item.getSalesId());
				pstm.setString(2, item.getProductId());
				pstm.setInt(3, item.getSalesQuantity());
				pstm.setInt(4, item.getSalePriceAt());
				pstm.setInt(5, item.getCostPriceAt());
				
				result = pstm.executeUpdate();
			}
			ConnectionProvider.close(conn, pstm);
			System.out.println("판매상품 인서트");
		} catch (Exception e) {
			System.out.println("예외발생(판매상품) : "+e.getMessage());
		}
		return result;
	}
	
}
