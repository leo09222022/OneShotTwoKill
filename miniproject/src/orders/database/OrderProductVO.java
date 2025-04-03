package orders.database;

public class OrderProductVO {
	private int order_id;
	private String product_id;
	private int order_quantity;

	public OrderProductVO(int order_id, String product_id, int order_quantity) {
		super();
		this.order_id = order_id;
		this.product_id = product_id;
		this.order_quantity = order_quantity;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public int getOrder_quantity() {
		return order_quantity;
	}

	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}

	public OrderProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}

}