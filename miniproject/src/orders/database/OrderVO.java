package orders.database;

import java.util.Date;

public class OrderVO {
	private int order_id;
	private Date order_date;
	private int total_cost;
	private String remarks;

	public OrderVO(int order_id, Date order_date, int total_cost, String remarks) {
		super();
		this.order_id = order_id;
		this.order_date = order_date;
		this.total_cost = total_cost;
		this.remarks = remarks;
	}

	public OrderVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public int getTotal_cost() {
		return total_cost;
	}

	public void setTotal_cost(int total_cost) {
		this.total_cost = total_cost;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}