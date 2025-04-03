package totalordersproduct.database;

import java.sql.Date;

public class TotalOrdersProductVO {
	String productId; // 바코드
	String productName; //상품명
	int orderQuantity; // 발주수량
	Date orderDate; // 발주일
	int costPriceAt; // 원가
	int totCostPriceAt; // 총가격
	public TotalOrdersProductVO(String productId, String productName, int orderQuantity, Date orderDate,
			int costPriceAt, int totCostPriceAt) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.orderQuantity = orderQuantity;
		this.orderDate = orderDate;
		this.costPriceAt = costPriceAt;
		this.totCostPriceAt = totCostPriceAt;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public int getCostPriceAt() {
		return costPriceAt;
	}
	public void setCostPriceAt(int costPriceAt) {
		this.costPriceAt = costPriceAt;
	}
	public int getTotCostPriceAt() {
		return totCostPriceAt;
	}
	public void setTotCostPriceAt(int totCostPriceAt) {
		this.totCostPriceAt = totCostPriceAt;
	}
	public TotalOrdersProductVO() {
		super();
	}
	
}
