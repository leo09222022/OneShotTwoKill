package orders.database;

import db.ConnectionProvider;
import product.database.ProductVO;
import totalsalesproduct.database.TotalSalesProductVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAO {

	// 주문 삽입
	public void insertOrder(OrderVO order) {
		String sql = "INSERT INTO orders (ORDER_ID, ORDER_DATE, TOTAL_COST, REMARKS) VALUES (?, SYSDATE, ?, ?)";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, order.getOrder_id());
			pstmt.setInt(2, order.getTotal_cost());
			pstmt.setString(3, order.getRemarks());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("insertOrder error: " + e.getMessage());
		}
	}

	// 모든 상품 조회
	public List<ProductVO> getAllProducts() {
		List<ProductVO> productList = new ArrayList<>();
		String sql = "SELECT product_id, product_name, optimal_stock, stock FROM product";

		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				ProductVO product = new ProductVO();
				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setOptimalStock(rs.getInt("optimal_stock"));
				product.setStock(rs.getInt("stock"));

				productList.add(product);
			}
		} catch (Exception e) {
			System.out.println("getAllProducts error: " + e.getMessage());

		}
		return productList;
	}

//	 상품명으로 검색
	public List<ProductVO> searchByName(String name) {
		List<ProductVO> productList = new ArrayList<>();
		String sql = "SELECT product_id, product_name, optimal_stock, stock FROM product WHERE product_name LIKE ?";

		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + name + "%");

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ProductVO product = new ProductVO();
					product.setProductId(rs.getString("product_id"));
					product.setProductName(rs.getString("product_name"));
					product.setOptimalStock(rs.getInt("optimal_stock"));
					product.setStock(rs.getInt("stock"));

					productList.add(product);
				}
			}
		} catch (Exception e) {
			System.out.println("searchByName error: " + e.getMessage());
		}
		return productList;
	}

	public int getNextOrderId() {
		String sql = "SELECT COALESCE(MAX(ORDER_ID), 0) + 1 FROM orders";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				return rs.getInt(1); // 가장 큰 ORDER_ID + 1 반환
			}
		} catch (Exception e) {
			System.out.println("getNextOrderId error: " + e.getMessage());
		}
		return 1; // 기본값 (만약 orders 테이블이 비어 있다면 1 반환)
	}


	
	public int calculateTotalPrice(Map<String, Integer> orderCart) {
		int totalPrice = 0;
		String sql = "SELECT COST_PRICE FROM product WHERE PRODUCT_ID = ?";

		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			for (String productId : orderCart.keySet()) {
				pstmt.setString(1, productId);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						int price = rs.getInt("COST_PRICE");
						int quantity = orderCart.get(productId);
						totalPrice += price * quantity;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("calculateTotalPrice error: " + e.getMessage());
		}
		return totalPrice;
	}
	

	

}
