package dao;


import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Order;
import bean.Product;
import bean.User;
import util.DBUtil;
import util.DateUtil;
 
public class OrderDAO {
	public static final String dealing = "dealing";
	public static final String d_seller = "d_seller";
	public static final String success = "success";
	public static final String fail = "fail";
	public static final String delete = "delete";
	public static final String finish = "finish";

	
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select count(*) from Order_";
 
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return total;
    }
 
 public void update(Order bean) {

    	
        String sql = "update order_ set  status=? ,confirmDate_S = ? ,confirmDate_B = ? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, bean.getStatus());
            java.text.SimpleDateFormat sdf = 
               new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strCDate_S;
            String strCDate_B;
            if( bean.getConfirmDate_S()!=null){
            	strCDate_S = sdf.format(bean.getConfirmDate_S());
            	ps.setString(2, strCDate_S);
            }
            else {
            	ps.setDate(2, null);
            }
          
            
            if( bean.getConfirmDate_B()!=null){
            	strCDate_B = sdf.format(bean.getConfirmDate_B());
            	ps.setString(3, strCDate_B);
            }
            else {
            	 ps.setDate(3, null);
            }
           
            ps.setInt(4, bean.getId());
            ps.execute();
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
 
    }
 
    public Order get(int id) {
        Order bean = new Order();
 

        
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select * from order_ where id = " + id;
 
            ResultSet rs = s.executeQuery(sql);
 
            if (rs.next()) {
            	String leaveMessage = rs.getString("leaveMessage");
    			String status = rs.getString("status");
    			Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
    			Date confirmDate_S = DateUtil.t2d( rs.getTimestamp("confirmDate_S"));
    			Date confirmDate_B=DateUtil.t2d( rs.getTimestamp("confirmDate_B"));
    		    long sellerId=rs.getLong("sellerId");
    			long  buyerId = rs.getLong("buyerId");
    			int id_ = rs.getInt("id");
    			int pid=rs.getInt("pid");
    			
    			bean.setId(id_);
    			bean.setLeaveMessage(leaveMessage);
    			bean.setCreateDate(createDate);
    			bean.setConfirmDate_B(confirmDate_B);
    			bean.setConfirmDate_S(confirmDate_S);
    			Product p=new ProductDAO().get(pid);
    			bean.setProduct(p);
    			bean.setBuyerId(buyerId);
    			bean.setSellerId(sellerId);
  
    			bean.setStatus(status);
            }
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return bean;
    }
 
    
    public Order getNew(long uid, int pid) {
    	Order bean = new Order();
    	
    	String sql = "insert into order_(id,buyerName,createDate,sellerId,buyerId,pid) values(null,?,?,?,?,?)";
    	try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
    		User buyer = new UserDAO().get(uid);
    		Product p = new ProductDAO().get(pid);
    		User seller = p.getUser();
    		Date createDate = new Date();
    		
    		//创建订单的同时将改产品状态改为dealing
    		p.setStatus("dealing");
    		new ProductDAO().update(p);
    		
    		ps.setString(1, buyer.getName());
    		ps.setTimestamp(2, DateUtil.d2t(createDate));
    		ps.setLong(3, seller.getId());
    		ps.setLong(4, buyer.getId());
    		ps.setInt(5, pid);
    		
    		ps.execute();
    		
    		ResultSet rs = ps.getGeneratedKeys();
    		if(rs.next()) {
    			int id = rs.getInt(1);
    			bean.setId(id);
    		}
    		bean.setBuyerId(buyer.getId());
    		bean.setSellerId(seller.getId());
    		bean.setProduct(p);
    		bean.setCreateDate(createDate);
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return bean;
    }
    public Order get(User seller, int pid) {
		Order bean = new Order();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select * from order_ where pid = " + pid+" and sellerId= "+seller.getId();
 
            ResultSet rs = s.executeQuery(sql);
 
            if (rs.next()) {
            	String leaveMessage = rs.getString("leaveMessage");
    			String status = rs.getString("status");
    			Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
    			Date confirmDate_S = DateUtil.t2d( rs.getTimestamp("confirmDate_S"));
    			Date confirmDate_B=DateUtil.t2d( rs.getTimestamp("confirmDate_B"));
    			long  buyerId = rs.getLong("buyerId");
    			int id_ = rs.getInt("id");
    			
    			bean.setId(id_);
    			bean.setLeaveMessage(leaveMessage);
    			bean.setCreateDate(createDate);
    			bean.setConfirmDate_B(confirmDate_B);
    			bean.setConfirmDate_S(confirmDate_S);
    			Product p=new ProductDAO().get(pid);
    			bean.setProduct(p);
    			bean.setBuyerId(buyerId);
    			bean.setSellerId(seller.getId());
  
    			bean.setStatus(status);
            }
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return bean;
	}
 
 
    
    public List<Order> list(long uid,String excludedStatus) {
        return list(uid,excludedStatus,0, Short.MAX_VALUE);
    }
     
    public List<Order> list(long uid, String excludedStatus, int start, int count) {
    	List<Order> beans = new ArrayList<Order>();
    	String sql;
    	sql = "select * from order_ where (buyerId = ?  or sellerId = ?) and status != ? order by id desc ";
    	try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
    		
    		ps.setLong(1, uid);
    		ps.setLong(2, uid);
    		ps.setString(3, excludedStatus);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		ProductDAO productDAO = new ProductDAO();
    		
    		while (rs.next()) {
    			Order bean = new Order();
    			String leaveMessage = rs.getString("leaveMessage");
    			String status = rs.getString("status");
    			Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
    			Date confirmDate_S = DateUtil.t2d( rs.getTimestamp("confirmDate_S"));
    			Date confirmDate_B=DateUtil.t2d( rs.getTimestamp("confirmDate_B"));
    		    long sellerId=rs.getLong("sellerId");
    		    long  buyerId = rs.getLong("buyerId");
    			int id = rs.getInt("id");
    			int pid=rs.getInt("pid");
    			
    			bean.setId(id);
    			bean.setLeaveMessage(leaveMessage);
    			bean.setCreateDate(createDate);
    			bean.setConfirmDate_B(confirmDate_B);
    			bean.setConfirmDate_S(confirmDate_S);
    			
    			Product p=new ProductDAO().get(pid);
    			productDAO.setFirstProductImage(p);
    			
    			bean.setProduct(p);
    			bean.setBuyerId(buyerId);
    			bean.setSellerId(sellerId);
  
  
    			bean.setStatus(status);
    			beans.add(bean);
    		}
    	} catch (SQLException e) {
    		
    		e.printStackTrace();
    	}
    	return beans;
    }
    
    //每天刷新一次订单，订单一旦创建，若状态七天内未达到success或finish，自动变为fail
    public void refresh() {
    	String sql = "select * from order_ order by id desc";
    	try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			Order bean = new Order();
    			
    			String leaveMessage = rs.getString("leaveMessage");
    			String status = rs.getString("status");
    			Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
    			Date confirmDate_S = DateUtil.t2d( rs.getTimestamp("confirmDate_S"));
    			Date confirmDate_B=DateUtil.t2d( rs.getTimestamp("confirmDate_B"));
    		    long sellerId=rs.getLong("sellerId");
    			long  buyerId = rs.getLong("buyerId");
    			int id_ = rs.getInt("id");
    			int pid=rs.getInt("pid");
    			
    			String newStatus = status;
    			if(status.equals(dealing) || status.equals(d_seller)) {
    				Date today = new Date();
    				int dayDiff = (int)((today.getTime() - createDate.getTime()) / (1000*60*60*24));
    				if(dayDiff >= 7) {
    					newStatus = fail;
    				}
//    				System.out.println(dayDiff);
    			}
    			
    			bean.setId(id_);
    			bean.setLeaveMessage(leaveMessage);	
    			bean.setCreateDate(createDate);
    			bean.setConfirmDate_B(confirmDate_B);
    			bean.setConfirmDate_S(confirmDate_S);
    			Product p=new ProductDAO().get(pid);
    			bean.setProduct(p);
    			bean.setBuyerId(buyerId);
    			bean.setSellerId(sellerId);
    			bean.setStatus(newStatus);
    			
    			if(!newStatus.equals(status)) {
    				update(bean);
    				System.out.println("更新订单号" + bean.getId() + "状态:" + status + "->" + newStatus);
    			}
    		}
		} catch (SQLException e) {
		    		
    		e.printStackTrace();
    	}
    }
 
}