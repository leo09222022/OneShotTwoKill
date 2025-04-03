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

    // [신규 상품 등록] 화면에서 카테고리 이름을 매개변수로 받아 해당 카테고리 아이디를 
    // 반환한다. 
    public int getCategoryIdByName(String categoryName) {
        int categoryId = -1;
        String sql = "select category_id from category where category_name = ?";
        
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                categoryId = rs.getInt("category_id");
            }
            
            ConnectionProvider.close(conn, pstmt, rs);
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        
        return categoryId;
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
    
    // [상품 수정] 화면에서 수정할 때 필요한 상품들을 전부 출력한다. 
    public ArrayList<ProductVO> getAllProducts() {
        ArrayList<ProductVO> productList = new ArrayList<>();
        String sql = "SELECT * FROM product";
        
        try (Connection conn = ConnectionProvider.getConnection();

        	PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ProductVO product = new ProductVO(
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
                productList.add(product);
            }
        } catch (Exception e) {
        	System.out.println("예외발생: " + e.getMessage());
        }
        return productList;
    }
    
    // [상품 수정] 화면에서 상품을 이름으로 검색할때 리스트형식으로 반환하여 
    // 비슷한 이름의 상품들이 있을시 모두 출력한다. 
    public ArrayList<ProductVO> searchProductsByName(String keyword) {
        ArrayList<ProductVO> productList = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE product_name LIKE ?";
        
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%"); // 키워드 포함 검색
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ProductVO product = new ProductVO(
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
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    // [상품 수정]에서 상품 "삭제" 시 현재 재고를 0으로 설정한다. 
    public void updateStockToZero(String productId) {
        String sql = "update product set stock = 0 where product_id = ?";
        
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, productId);
            pstmt.executeUpdate();
            
        } catch (Exception e) {
        	System.out.println("예외발생: " + e.getMessage());
        }
    }



}

