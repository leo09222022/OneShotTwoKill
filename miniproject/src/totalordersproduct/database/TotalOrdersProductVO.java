package totalordersproduct.database;

import java.sql.Date;

public class TotalOrdersProductVO {
	String productId; // 바코드
	String productName; //상품명
	int orderQuantity; // 발주수량
	Date orderDate; // 발주일
	int costPriceProduct; // 원가
	int totCostPriceProduct; // 총가격
	String remarks; // 비고사항
	public TotalOrdersProductVO(String productId, String productName, int orderQuantity, Date orderDate,
			int costPriceProduct, int totCostPriceProduct, String remarks) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.orderQuantity = orderQuantity;
		this.orderDate = orderDate;
		this.costPriceProduct = costPriceProduct;
		this.totCostPriceProduct = totCostPriceProduct;
		this.remarks = remarks;
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
	public int getCostPriceProduct() {
		return costPriceProduct;
	}
	public void setCostPriceProduct(int costPriceProduct) {
		this.costPriceProduct = costPriceProduct;
	}
	public int getTotCostPriceProduct() {
		return totCostPriceProduct;
	}
	public void setTotCostPriceProduct(int totCostPriceProduct) {
		this.totCostPriceProduct = totCostPriceProduct;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public TotalOrdersProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
