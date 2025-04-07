package sales.database;

public class SalesProductVO {
	private Integer salesId;
	private String productId;
	private int salesQuantity;
	private int salePriceAt;
	private int costPriceAt;

	/* 2025-04-05 왕시은 
	 * salesId 시퀀스로 전달시 int로 되어 있으면
	 * GUI에서 기본값으 null을 설정해 두면
	 * 값을 받지 못함. 
	 * 하여 int -> Integer로 변경
	 */
	public SalesProductVO(Integer salesId, String productId, int salesQuantity, int salePriceAt, int costPriceAt) {
		super();
		this.salesId = salesId;
		this.productId = productId;
		this.salesQuantity = salesQuantity;
		this.salePriceAt = salePriceAt;
		this.costPriceAt = costPriceAt;
	}

	public SalesProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getSalesId() {
		return salesId;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public int getSalePriceAt() {
		return salePriceAt;
	}

	public void setSalePriceAt(int salePriceAt) {
		this.salePriceAt = salePriceAt;
	}

	public int getCostPriceAt() {
		return costPriceAt;
	}

	public void setCostPriceAt(int costPriceAt) {
		this.costPriceAt = costPriceAt;
	}
}
