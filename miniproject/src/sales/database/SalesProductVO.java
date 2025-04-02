package sales.database;

public class SalesProductVO {
	private int salesId;
	private String productId;
	private int salesQuantity;
	private int salePriceAt;
	private int costPriceAt;

	public SalesProductVO(int salesId, String productId, int salesQuantity, int salePriceAt, int costPriceAt) {
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

	public int getSalesId() {
		return salesId;
	}

	public void setSalesId(int salesId) {
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
