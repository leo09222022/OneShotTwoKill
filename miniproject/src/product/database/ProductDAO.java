package product.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.ConnectionProvider;

public class ProductDAO {

    // Product 추가
    public int insertProduct(ProductVO p) {
        int result = -1;
        String sql = "insert into product (product_id, category_id, product_name, optimal_stock, stock, cost_price, sale_price, created_at, updated_at) "
                   + "values (?, ?, ?, ?, ?, ?, ?, sysdate, sysdate)";
        
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, p.getProductId());
            pstmt.setInt(2, p.getCategoryId());
            pstmt.setString(3, p.getProductName());
            pstmt.setInt(4, p.getOptimalStock());
            pstmt.setInt(5, p.getStock());
            pstmt.setInt(6, p.getCostPrice());
            pstmt.setInt(7, p.getSalePrice());
            
            result = pstmt.executeUpdate();
            
            ConnectionProvider.close(conn, pstmt); 
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }
        return result;
    }

    // Product 수정
    public int updateProduct(ProductVO p) {
        int result = -1;
        String sql = "update product set category_id=?, product_name=?, optimal_stock=?, stock=?, cost_price=?, sale_price=?, updated_at=sysdate where product_id=?";
        
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, p.getCategoryId());
            pstmt.setString(2, p.getProductName());
            pstmt.setInt(3, p.getOptimalStock());
            pstmt.setInt(4, p.getStock());
            pstmt.setInt(5, p.getCostPrice());
            pstmt.setInt(6, p.getSalePrice());
            pstmt.setString(7, p.getProductId());
            
            result = pstmt.executeUpdate();
            
            ConnectionProvider.close(conn, pstmt); 
           
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }
        
        return result;
    }

    // Product 삭제 (상품아이디를 매개변수로) 
    public void deleteProduct(String productId) {
        String sql = "delete from product where product_id=?";
        
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, productId);
            pstmt.executeUpdate();
            
            ConnectionProvider.close(conn, pstmt);
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }
    }

    // Product 이름으로 조회
    public ProductVO findByName(String productName) {
        ProductVO product = null;
        String sql = "select * from product where product_name=?";
        
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            
            // ResultSet 활용
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

    // 모든 Product 조회
    public ArrayList<ProductVO> findAll() {
        ArrayList<ProductVO> list = new ArrayList<>();
        String sql = "select * from product";
        
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            // ResultSet 활용
            while (rs.next()) {
                list.add(new ProductVO(
                    rs.getString("product_id"),
                    rs.getInt("category_id"),
                    rs.getString("product_name"),
                    rs.getInt("optimal_stock"),
                    rs.getInt("stock"),
                    rs.getInt("cost_price"),
                    rs.getInt("sale_price"),
                    rs.getDate("created_at"),
                    rs.getDate("updated_at")
                ));
            }
            ConnectionProvider.close(conn, pstmt, rs);
           
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }
        
        return list;
    }
}

