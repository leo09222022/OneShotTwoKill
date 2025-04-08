package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Init {
	
	public static void init() {
		createUser();
		grant();
		createTable();
		insertData();
	}
	
	public static void createUser() {
		try {
			Connection conn = ConnectionProvider.getManagerConnection("system", "manager");			
			String sql ="create user c##ostkmini identified by ostkmini";
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			ConnectionProvider.close(conn, stmt);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static void grant() {
		try {
			Connection conn = ConnectionProvider.getManagerConnection("system", "manager");				
			String sql ="grant connect, resource, dba to c##ostkmini";
			PreparedStatement pstmt = conn.prepareStatement(sql);	
			pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static void createTable() {
		String[] sqlStatements = {
				   "CREATE TABLE category ( category_id NUMBER PRIMARY KEY, category_name VARCHAR2(50) NOT NULL, created_at DATE DEFAULT SYSDATE NOT NULL )",
				    "CREATE TABLE product ( product_id CHAR(13) PRIMARY KEY, category_id NUMBER REFERENCES category(category_id), product_name VARCHAR2(100) NOT NULL, stock NUMBER DEFAULT 0 NOT NULL, optimal_stock NUMBER NOT NULL, cost_price NUMBER NOT NULL, sale_price NUMBER NOT NULL, created_at DATE DEFAULT SYSDATE NOT NULL, updated_at DATE DEFAULT SYSDATE NOT NULL )",
				    "CREATE TABLE orders ( order_id NUMBER PRIMARY KEY, order_date DATE DEFAULT SYSDATE NOT NULL, total_cost NUMBER NOT NULL, remarks VARCHAR2(200) )",
				    "CREATE TABLE orders_product ( order_id NUMBER NOT NULL, product_id CHAR(13) NOT NULL, order_quantity NUMBER NOT NULL, PRIMARY KEY (order_id, product_id), FOREIGN KEY (order_id) REFERENCES orders(order_id), FOREIGN KEY (product_id) REFERENCES product(product_id) )",
				    "CREATE TABLE sales ( sales_id NUMBER PRIMARY KEY, sales_date DATE DEFAULT SYSDATE NOT NULL, sales_total NUMBER NOT NULL, card_num CHAR(16) NOT NULL, expiration_date DATE NOT NULL, card_ver NUMBER NOT NULL )",
				    "CREATE TABLE sales_product ( sales_id NUMBER NOT NULL, product_id CHAR(13) NOT NULL, sales_quantity NUMBER NOT NULL, sale_price_at NUMBER NOT NULL, cost_price_at NUMBER NOT NULL, PRIMARY KEY (sales_id, product_id), FOREIGN KEY (sales_id) REFERENCES sales(sales_id), FOREIGN KEY (product_id) REFERENCES product(product_id) )",
				    "create sequence sales_seq start with 2000 increment by 1",
				    "CREATE OR REPLACE TRIGGER trg_update_stock_after_order AFTER INSERT ON orders_product FOR EACH ROW BEGIN UPDATE product SET stock = stock + :NEW.ORDER_QUANTITY WHERE product_id = :NEW.product_id; END;"

	        };
			
			try {
				Connection conn = ConnectionProvider.getConnection();
	            Statement stmt = conn.createStatement();

	            for (String sql : sqlStatements) {	                
	                 stmt.executeUpdate(sql);
	                 System.out.println("Executed: " + sql);
	            }
	            ConnectionProvider.close(conn, stmt);
			}catch (Exception e) {
				System.out.println("예외발생:"+e.getMessage());
			}
	    }
	
	
	
	public static void insertData() {
	   
	    String[] insertStatements = {
	    	// 카테고리
    		"INSERT ALL INTO category (category_id, category_name, created_at) VALUES (3001, '음료', '2025-03-01') INTO category (category_id, category_name, created_at) VALUES (3002, '스낵', '2025-03-02') INTO category (category_id, category_name, created_at) VALUES (3003, '유제품', '2025-03-03') INTO category (category_id, category_name, created_at) VALUES (3004, '빙과류', '2025-03-04') INTO category (category_id, category_name, created_at) VALUES (3005, '기타', '2025-03-05') SELECT * FROM dual",

    		// 상품
    		"INSERT ALL INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801056158816', 3001, '코카콜라 500ml', 50, 60, 800, 1500, '2025-03-01', '2025-03-10') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801037141425', 3002, '새우깡 90g', 30, 20, 1000, 1500, '2025-03-02', '2025-03-11') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801043035140', 3003, '바나나우유 240ml', 40, 60, 900, 1400, '2025-03-03', '2025-03-12') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801025700385', 3001, '핫식스 250ml', 25, 30, 700, 1200, '2025-03-04', '2025-03-13') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801115115289', 3002, '오징어땅콩 98g', 20, 20, 1500, 2000, '2025-03-05', '2025-03-14') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801062357115', 3003, '초코우유 300ml', 35, 10, 1100, 1700, '2025-03-06', '2025-03-15') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801115116170', 3004, '월드콘 160ml', 15, 10, 1000, 1800, '2025-03-07', '2025-03-16') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801069412345', 3005, '컵라면 신라면 큰사발', 30, 30, 1200, 1800, '2025-03-08', '2025-03-17') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801037141401', 3002, '포카칩 오리지널 66g', 20, 24, 1000, 1500, '2025-03-09', '2025-03-18') INTO product (product_id, category_id, product_name, stock, optimal_stock, cost_price, sale_price, created_at, updated_at) VALUES ('8801043035102', 3003, '딸기우유 240ml', 40, 10, 900, 1400, '2025-03-10', '2025-03-19') SELECT * FROM dual",

    		// 판매+판매상품
    		"INSERT ALL INTO sales (sales_id, sales_date, sales_total, card_num, expiration_date, card_ver) VALUES (sales_seq.nextval, '2025-03-15', 7500, '1234567812345678', '2028-12-31', 12345678) SELECT * FROM dual",
    		"INSERT ALL INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801056158816', 5, 1500, 800) SELECT * FROM dual",

    		"INSERT ALL INTO sales (sales_id, sales_date, sales_total, card_num, expiration_date, card_ver) VALUES (sales_seq.nextval, '2025-03-16', 10200, '2345678923456789', '2027-11-30', 23456789) SELECT * FROM dual",
    		"INSERT ALL INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801037141425', 4, 1500, 1000) INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801043035140', 3, 1400, 900) SELECT * FROM dual",

    		"INSERT ALL INTO sales (sales_id, sales_date, sales_total, card_num, expiration_date, card_ver) VALUES (sales_seq.nextval, '2025-03-17', 26900, '1234888599968888', '2026-10-31', 34567890) SELECT * FROM dual",
    		"INSERT ALL INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801025700385', 7, 1200, 700) INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801115115289', 5, 2000, 1500) INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801062357115', 5, 1700, 1100) SELECT * FROM dual",

    		"INSERT ALL INTO sales (sales_id, sales_date, sales_total, card_num, expiration_date, card_ver) VALUES (sales_seq.nextval, '2025-03-18', 25500, '4567890145678901', '2025-09-30', 45678901) SELECT * FROM dual",
    		"INSERT ALL INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801115116170', 5, 1800, 1000) INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801069412345', 5, 1800, 1200) INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801037141401', 5, 1500, 1000) SELECT * FROM dual",

    		"INSERT ALL INTO sales (sales_id, sales_date, sales_total, card_num, expiration_date, card_ver) VALUES (sales_seq.nextval, '2025-03-19', 7000, '5678901256789012', '2026-08-31', 56789012) SELECT * FROM dual",
    		"INSERT ALL INTO sales_product (sales_id, product_id, sales_quantity, sale_price_at, cost_price_at) VALUES (sales_seq.currval, '8801043035102', 5, 1400, 900) SELECT * FROM dual",

    		// 주문+주문상품
    		"INSERT ALL INTO orders (order_id, order_date, total_cost, remarks) VALUES (1001, '2025-03-10', 8000, '빠른 배송 요청') INTO orders (order_id, order_date, total_cost, remarks) VALUES (1002, '2025-03-11', 14300, '포장 배송 요청') INTO orders (order_id, order_date, total_cost, remarks) VALUES (1003, '2025-03-12', 25200, '도착 시 전화 요청') INTO orders (order_id, order_date, total_cost, remarks) VALUES (1004, '2025-03-13', 18000, '제품 상태 확인 후 수령') INTO orders (order_id, order_date, total_cost, remarks) VALUES (1005, '2025-03-14', 27600, NULL) SELECT * FROM dual",
    		"INSERT ALL INTO orders_product (order_id, product_id, order_quantity) VALUES (1001, '8801056158816', 10) INTO orders_product (order_id, product_id, order_quantity) VALUES (1002, '8801037141425', 5) INTO orders_product (order_id, product_id, order_quantity) VALUES (1002, '8801043035140', 7) INTO orders_product (order_id, product_id, order_quantity) VALUES (1003, '8801025700385', 3) INTO orders_product (order_id, product_id, order_quantity) VALUES (1003, '8801115115289', 8) INTO orders_product (order_id, product_id, order_quantity) VALUES (1003, '8801062357115', 13) INTO orders_product (order_id, product_id, order_quantity) VALUES (1004, '8801115116170', 18) INTO orders_product (order_id, product_id, order_quantity) VALUES (1005, '8801069412345', 23) SELECT * FROM dual"

	    		   
	    };

	    try {
	    	 Connection conn = ConnectionProvider.getConnection();
	         Statement stmt = conn.createStatement();
	         for (String sql : insertStatements) {	            
	                stmt.executeUpdate(sql);
	                System.out.println("Inserted: " + sql);
	         }
	         ConnectionProvider.close(conn, stmt);
	    } catch (Exception e) {
	    	System.out.println("예외발생:"+e.getMessage());
	    }
	}
}




















