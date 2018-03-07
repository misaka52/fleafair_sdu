/**
* 模仿天猫整站j2ee 教程 为how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	

package bean;

import java.util.Date;
import java.util.List;
//import tmall.dao.OrderDAO;

import dao.OrderDAO;

public class Order {
	private int id;
	private String leaveMessage;
	private Date createDate;
	private Date confirmDate_B;
	private Date confirmDate_S;
	private long buyerId;
	private long sellerId;
	private Product product;
	private float total;
	private int totalNumber;
	private String status;
	
	
	
	public String getStatusDesc(){
		String desc ="未知";
		switch(status){
		case OrderDAO.dealing:
			desc="待卖家确认";
			break;
		case OrderDAO.d_seller:
			desc="待买家确认";
			break;
		case OrderDAO.fail:
			desc="交易失败";
			break;
		case OrderDAO.success:
			desc="交易成功";
			break;
		case OrderDAO.delete:
			desc="刪除";
			break;
		case OrderDAO.finish:
			desc="已完成评价";
			break;
		default:
			desc="未知";
		}
		return desc;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getLeaveMessage() {
		return leaveMessage;
	}
	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getConfirmDate_B() {
		return confirmDate_B;
	}
	public void setConfirmDate_B(Date confirmDate_B) {
		this.confirmDate_B = confirmDate_B;
	}
	public Date getConfirmDate_S() {
		return confirmDate_S;
	}
	public void setConfirmDate_S(Date confirmDate_S) {
		this.confirmDate_S = confirmDate_S;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyer) {
		this.buyerId = buyer;
	}
	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long seller) {
		this.sellerId = seller;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product p) {
		this.product = p;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	
}
/**
* 模仿天猫整站j2ee 教程 为how2j.cn 版权所有
* 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
* 供购买者学习，请勿私自传播，否则自行承担相关法律责任
*/	
