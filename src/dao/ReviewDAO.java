package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Product;
import bean.Review;
import bean.User;
import util.DBUtil;
import util.DateUtil;
 
public class ReviewDAO {
 
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select count(*) from Review";
 
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return total;
    }
    public int getTotal(int pid) {
    	int total = 0;
    	try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
    		
    		String sql = "select count(*) from Review where pid = " + pid;
    		
    		ResultSet rs = s.executeQuery(sql);
    		while (rs.next()) {
    			total = rs.getInt(1);
    		}
    	} catch (SQLException e) {
    		
    		e.printStackTrace();
    	}
    	return total;
    }
 
    public void add(Review bean) {
        String sql = "insert into Review values(null,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setString(1, bean.getContent());
            ps.setLong(2, bean.getBuyer().getId());
            ps.setInt(3, bean.getProduct().getId());
            ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
            ps.setLong(5, bean.getSeller().getId());
            
            ps.execute();
 
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
    }
 
    public void delete(int id) {
 
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "delete from Review where id = " + id;
 
            s.execute(sql);
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
    }
 
    public Review get(int id) {
        Review bean = new Review();
 
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select * from Review where id = " + id;
 
            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                int pid = rs.getInt("pid");
                long buyerId = rs.getLong("buyerId");
                long sellerId = rs.getLong("sellerId");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                
                String content = rs.getString("content");
                
                Product product = new ProductDAO().get(pid);
                User buyer = new UserDAO().get(buyerId);
                User seller = new UserDAO().get(sellerId);
                
                bean.setContent(content);
                bean.setCreateDate(createDate);
                bean.setProduct(product);
                bean.setBuyer(buyer);
                bean.setSeller(seller);
                bean.setId(id);
            }
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return bean;
    }
 
    public List<Review> list(long uid) {
        return list(uid, 0, Short.MAX_VALUE);
    }
 
    public int getCount(long uid) {
        String sql = "select count(*) from Review where sellerId = ? ";
 
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setLong(1, uid);
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
               return rs.getInt(1);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return 0;    	
    }
    public List<Review> list(long uid, int start, int count) {
        List<Review> beans = new ArrayList<Review>();
 
        String sql = "select * from Review where sellerId = ? order by id desc limit ?,? ";
 
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setLong(1, uid);
            ps.setInt(2, start);
            ps.setInt(3, count);
 
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
                Review bean = new Review();
                int id = rs.getInt(1);
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                
                String content = rs.getString("content");
                Product product = new ProductDAO().get(rs.getInt("pid"));
                User seller = new UserDAO().get(uid);
                User buyer = new UserDAO().get(rs.getLong("buyerId"));
                
                
                bean.setContent(content);
                bean.setCreateDate(createDate);
                bean.setId(id);     
                bean.setProduct(product);
                bean.setSeller(seller);
                bean.setBuyer(buyer);
                
                beans.add(bean);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return beans;
    }
 
}