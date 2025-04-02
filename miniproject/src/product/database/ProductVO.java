package product.database;

import java.util.Date;

public class ProductVO {
    private String productId;
    private int categoryId;
    private String productName;
    private int optimalStock;
    private int stock;
    private int costPrice;
    private int salePrice;
    private Date createdAt; 
    private Date updatedAt;  

    public ProductVO() {
    	super();
    }

    public ProductVO(String productId, int categoryId, String productName, int optimalStock, int stock, int costPrice, int salePrice, Date createdAt, Date updatedAt) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.optimalStock = optimalStock;
        this.stock = stock;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public String getProductId() { 
    	return productId; 
    }
    public void setProductId(String productId) { 
    	this.productId = productId; 
    }
    public int getCategoryId() { 
    	return categoryId; 
    }
    public void setCategoryId(int categoryId) { 
    	this.categoryId = categoryId; 
    }
    public String getProductName() { 
    	return productName; 
    }
    public void setProductName(String productName) { 
    	this.productName = productName; 
    }
    public int getOptimalStock() { 
    	return optimalStock; 
    }
    public void setOptimalStock(int optimalStock) { 
    	this.optimalStock = optimalStock; 
    }
    public int getStock() { 
    	return stock; 
    }
    public void setStock(int stock) { 
    	this.stock = stock; 
    }
    public int getCostPrice() { 
    	return costPrice; 
    }
    public void setCostPrice(int costPrice) { 
    	this.costPrice = costPrice; 
    }
    public int getSalePrice() { 
    	return salePrice; 
    }
    public void setSalePrice(int salePrice) { 
    	this.salePrice = salePrice; 
    }
    public Date getCreatedAt() { 
    	return createdAt; 
    }
    public void setCreateDate(Date createdAt) { 
    	this.createdAt = createdAt; 
    }
    public Date getUpdatedAt() { 
    	return updatedAt; 
    }
    public void setUpdatedAt(Date updatedAt) { 
    	this.updatedAt = updatedAt;
    }
}


