package dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.User;
import util.DBUtil;
 
public class UserDAO {
 
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select count(*) from User";
 
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return total;
    }
 
    public void add(User bean) {
 
        String sql = "insert into user values(? ,? ,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setLong(1, bean.getId());
            ps.setString(2, bean.getName());
            ps.setString(3, bean.getPassword());
            
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
 
    public void update(User bean) {
 
        String sql = "update user set name= ? , password = ? where id = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.setLong(3, bean.getId());
 
            ps.execute();
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
 
    }
 
    public void delete(long id) {
 
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "delete from User where id = " + id;
 
            s.execute(sql);
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
    }
 
    public User get(long id) {
        User bean = null;
 
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
 
            String sql = "select * from user where id = " + id;
 
            ResultSet rs = s.executeQuery(sql);
 
            if (rs.next()) {
                bean = new User();
                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                bean.setId(id);
            }
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return bean;
    }
 
    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }
 
    public List<User> list(int start, int count) {
        List<User> beans = new ArrayList<User>();
 
        String sql = "select * from user order by id desc limit ?,? ";
 
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setInt(1, start);
            ps.setInt(2, count);
 
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
                User bean = new User();
                long id = rs.getLong(1);

                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return beans;
    }

	public boolean isExist(long id) {
		User user = get(id);
		return user!=null;

	}

	public User get(long id, String password) {
		User bean = null;
		 
		String sql = "select * from user where id = ? and password=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
        	ps.setLong(1, id);
        	ps.setString(2, password);
            ResultSet rs =ps.executeQuery();
 
            if (rs.next()) {
                bean = new User();
                String name = rs.getString("name");
                bean.setName(name);
                bean.setPassword(password);
                bean.setId(id);
            }
 
        } catch (SQLException e) {
 
            e.printStackTrace();
        }
        return bean;
	}
 
}