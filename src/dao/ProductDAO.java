package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Category;
import bean.Product;
import bean.ProductImage;
import bean.User;
import util.DBUtil;
import util.DateUtil;
  
public class ProductDAO {
  
    public void add(Product bean) {
 
        String sql = "insert into Product values(null,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setString(1, bean.getName());
            ps.setFloat(2, bean.getPrice());
            ps.setFloat(3, bean.getCategory().getId());
            ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
            ps.setString(5, bean.getDescription());
            ps.setLong(6, bean.getUser().getId());
            ps.setString(7, bean.getStatus());
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
  
    public void update(Product bean) {
    	 
        String sql = "update product set name= ?, price=?, cid=?,description=?,  status=? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
        	ps.setString(1, bean.getName());
            ps.setFloat(2, bean.getPrice());
            ps.setFloat(3, bean.getCategory().getId());
            ps.setString(4, bean.getDescription());
            ps.setString(5, bean.getStatus());
            ps.setInt(6, bean.getId());
            ps.execute();
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
  
    }
  
    public void delete(int id) {
  
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
  
            String sql = "delete from product where id = " + id;
  
            s.execute(sql);
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
    }
  
    public Product get(int id) {
        Product bean = new Product();
  
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
  
            String sql = "select * from Product where id = " + id;
  
            ResultSet rs = s.executeQuery(sql);
  
            if (rs.next()) {
 
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                int cid = rs.getInt("cid");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                String description = rs.getString("description");
                long uid = rs.getLong("uid");
                String status = rs.getString("status");
              
                Category category = new CategoryDAO().get(cid);
                User user = new UserDAO().get(uid);
                
                bean.setId(id);
                bean.setName(name);
                bean.setPrice(price);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setDescription(description);
                bean.setUser(user);
                bean.setStatus(status);
                
                setFirstProductImage(bean);
            }
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return bean;
    }
  
    public List<Product> list(int cid) {
        return list(cid,0, Short.MAX_VALUE);
    }
  
    public List<Product> list(int cid, int start, int count) {
        List<Product> beans = new ArrayList<Product>();
        Category category = new CategoryDAO().get(cid);
        String sql = "select * from Product where cid = ? and status = 'selling' order by id desc limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            
            while (rs.next()) {
                Product bean = new Product();
                
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                String description = rs.getString("description");
                long uid = rs.getLong("uid");
                String status = rs.getString("status");
              
                User user = new UserDAO().get(uid);
                
                bean.setId(id);
                bean.setName(name);
                bean.setPrice(price);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setDescription(description);
                bean.setUser(user);
                bean.setStatus(status);
                setFirstProductImage(bean);
                
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }
    public List<Product> list() {
        return list(0,Short.MAX_VALUE);
    }
    public List<Product> list(int start, int count) {
        List<Product> beans = new ArrayList<Product>();
 
        String sql = "select * from Product limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setInt(1, start);
            ps.setInt(2, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                Product bean = new Product();
                
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                int cid = rs.getInt("cid");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                String description = rs.getString("description");
                long uid = rs.getLong("uid");
                String status = rs.getString("status");
              
                Category category = new CategoryDAO().get(cid);
                User user = new UserDAO().get(uid);
                
                bean.setId(id);
                bean.setName(name);
                bean.setPrice(price);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setDescription(description);
                bean.setUser(user);
                bean.setStatus(status);
                
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }    
    
    public List<Product> list(User user, String s){
    	List<Product> ps = list(user, s, 0, Short.MAX_VALUE);
    	return ps;
    }
    public List<Product> list(User user,String s, int start, int count) {
    	List<Product> beans = new ArrayList<Product>();
    	long uid=user.getId();
    	String sql="";
    	if(s.equals("all"))
    		sql= "select * from product where uid = ?  order by id desc limit ?,? ";
    	else if(s.equals("dealing"))
    		sql= "select * from product where uid = ? and status = 'dealing' order by id desc limit ?,? ";
    	else
    		sql= "select * from product where uid = ? and status = 'fail' order by id desc limit ?,? ";
   	
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setLong(1, uid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            
            while (rs.next()) {
                Product bean = new Product();
                
                int id = rs.getInt("id");
                int cid=rs.getInt("cid");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                String description = rs.getString("description");
                String status = rs.getString("status");
                Category category=new CategoryDAO().get(cid);
                
                bean.setId(id);
                bean.setName(name);
                bean.setPrice(price);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setDescription(description);
                bean.setUser(user);
                bean.setStatus(status);
                setFirstProductImage(bean);
                
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
	}
    public int getTotal(User user) {
		long uid=user.getId();
		int total=0;
        String sql = "select count(*) as cnt from product where uid = ?   ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setLong(1, uid);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                total = rs.getInt("cnt");
           
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
		return total;
	}

 
    public void fill(List<Category> cs) {
        for (Category c : cs) 
            fill(c);
    }
    public void fill(Category c) {
        List<Product> ps = this.list(c.getId());
        c.setProducts(ps);
    }
 
     
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis= new ProductImageDAO().list(p, ProductImageDAO.type_single);
        if(!pis.isEmpty())
            p.setFirstProductImage(pis.get(0));     
    }
 
    public List<Product> search(String keyword, int start, int count) {
         List<Product> beans = new ArrayList<Product>();
          
         if(null==keyword||0==keyword.trim().length())
             return beans;
            String sql = "select * from Product where status='selling' and name like ? limit ?,? ";
      
            try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
                ps.setString(1, "%"+keyword.trim()+"%");
                ps.setInt(2, start);
                ps.setInt(3, count);
      
                ResultSet rs = ps.executeQuery();
      
                while (rs.next()) {
                    Product bean = new Product();
                    
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Float price = rs.getFloat("price");
                    int cid = rs.getInt("cid");
                    Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                    String description = rs.getString("description");
                    long uid = rs.getLong("uid");
                    String status = rs.getString("status");
                  
                    Category category = new CategoryDAO().get(cid);
                    User user = new UserDAO().get(uid);
                    
                    bean.setId(id);
                    bean.setName(name);
                    bean.setPrice(price);
                    bean.setCategory(category);
                    bean.setCreateDate(createDate);
                    bean.setDescription(description);
                    bean.setUser(user);
                    bean.setStatus(status);
                    
                    setFirstProductImage(bean);                
                    beans.add(bean);
                }
            } catch (SQLException e) {
      
                e.printStackTrace();
            }
            return beans;
    }
}