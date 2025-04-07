package sales.database;

import java.sql.Date;

/*
 * SALES (판매 테이블)
 * 작업자 : 왕시은
 * 용도 : 장바구니에 담긴 상품을 결제하면서 넘어오는 정보를 VO로 담는다.
 */
public class SalesVO {	
	private Integer salesId;	 	// 판매ID		NUMBER
	private Date salesDate;			// 판매일자	SYSDATE
	private int salesTotal;			// 총합계		NUMBER
	private String cardNum;			// 카드번호	CHAR
	private Date expirationDate;	// 카드유효기간	DATE
	private String cardVer;			// 승인번호	CHAR 
	
	public SalesVO() {}
	public SalesVO(Integer salesId, Date salesDate, int salesTotal, String cardNum, Date expirationDate, String cardVer) {
		this.salesId = salesId;
		this.salesDate = salesDate;
		this.salesTotal = salesTotal;
		this.cardNum = cardNum;
		this.expirationDate = expirationDate;
		this.cardVer = cardVer;
	}
	
	public Integer getSalesId() {
		return salesId;
	}
	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}
	public Date getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}
	public int getSalesTotal() {
		return salesTotal;
	}
	public void setSalesTotal(int salesTotal) {
		this.salesTotal = salesTotal;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getCardVer() {
		return cardVer;
	}
	public void setCardVer(String cardVer) {
		this.cardVer = cardVer;
	}
}
