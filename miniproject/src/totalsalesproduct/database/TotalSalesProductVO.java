package totalsalesproduct.database;

public class TotalSalesProductVO {
	String productId; // 제품 아이디
	String productName; // 제품명
	int salesCount; // 판매수량
	int costPriceAt; // 원가
	int salePriceAt; // 판매가
	int sumSalePrice; // 총 판매수익
	int profits; // 순이익
	double profitsRate; // 이익률;
	public TotalSalesProductVO(String productId, String productName, int salesCount, int costPriceAt, int salePriceAt,
			int sumSalePrice, int profits, double profitsRate) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.salesCount = salesCount;
		this.costPriceAt = costPriceAt;
		this.salePriceAt = salePriceAt;
		this.sumSalePrice = sumSalePrice;
		this.profits = profits;
		this.profitsRate = profitsRate;
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
	public int getSalesCount() {
		return salesCount;
	}
	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}
	public int getCostPriceAt() {
		return costPriceAt;
	}
	public void setCostPriceAt(int costPriceAt) {
		this.costPriceAt = costPriceAt;
	}
	public int getSalePriceAt() {
		return salePriceAt;
	}
	public void setSalePriceAt(int salePriceAt) {
		this.salePriceAt = salePriceAt;
	}
	public int getSumSalePrice() {
		return sumSalePrice;
	}
	public void setSumSalePrice(int sumSalePrice) {
		this.sumSalePrice = sumSalePrice;
	}
	public int getProfits() {
		return profits;
	}
	public void setProfits(int profits) {
		this.profits = profits;
	}
	public double getProfitsRate() {
		return profitsRate;
	}
	public void setProfitsRate(double profitsRate) {
		this.profitsRate = profitsRate;
	}
	public TotalSalesProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TotalSalesProductVO [productId=" + productId + ", productName=" + productName + ", salesCount="
				+ salesCount + ", costPriceAt=" + costPriceAt + ", salePriceAt=" + salePriceAt + ", sumSalePrice="
				+ sumSalePrice + ", profits=" + profits + ", profitsRate=" + profitsRate + "]";
	}
	
}
