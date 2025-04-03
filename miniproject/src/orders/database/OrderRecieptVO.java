package orders.database;

public class OrderRecieptVO {
	private int orderId;
	private String productId;
	private String productName;
	private int orderQuantity;
	private int costPrice;
	private int costPerProduct;

	public OrderRecieptVO(int orderId, String productId, String productName, int orderQuantity, int costPrice,
			int costPerProduct) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.orderQuantity = orderQuantity;
		this.costPrice = costPrice;
		this.costPerProduct = costPerProduct;
	}

	public OrderRecieptVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public int getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}

	public int getCostPerProduct() {
		return costPerProduct;
	}

	public void setCostPerProduct(int costPerProduct) {
		this.costPerProduct = costPerProduct;
	}

}
