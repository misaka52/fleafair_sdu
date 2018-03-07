package servlet;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.util.HtmlUtils;

import bean.Category;
import bean.Order;
import bean.Product;
import bean.ProductImage;
import bean.Review;
import bean.User;
import comparator.ProductDateComparator;
import comparator.ProductPriceComparator;
import dao.CategoryDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.ProductImageDAO;
import util.Page;

public class ForeServlet extends BaseForeServlet {
	
	public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = new CategoryDAO().list();
		new ProductDAO().fill(cs);
		request.setAttribute("cs", cs);
		return "home.jsp";
	}
	
	public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
		long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        System.out.println(name);
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);
        System.out.println(name);
        boolean exist = userDAO.isExist(id);
         
        if(exist){
            request.setAttribute("msg", "用户账号已存在");
            return "register.jsp";  
        }
         
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        userDAO.add(user);
         
        return "@registerSuccess.jsp";  
    }   
	
	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		long id = Long.parseLong(request.getParameter("id"));
//	    name = HtmlUtils.htmlEscape(name);
	    String password = request.getParameter("password");     
	     
	    User user = userDAO.get(id,password);
	      
	    if(null==user){
	        request.setAttribute("msg", "账号密码错误");
	        return "login.jsp"; 
	    }
	    request.getSession().setAttribute("user", user);
	    return "@forehome"; 
	}   
	
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
	    request.getSession().removeAttribute("user");
	    return "@forehome"; 
	}   
	
	public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
	    Product p = productDAO.get(pid);
	     
	    List<ProductImage> productSingleImages = productImageDAO.list(p, ProductImageDAO.type_single);
	    List<ProductImage> productDetailImages = productImageDAO.list(p, ProductImageDAO.type_detail);
	    p.setProductSingleImages(productSingleImages);
	    p.setProductDetailImages(productDetailImages);
	           
	    //获取对改产品的用户的所以评价
	    List<Review> reviews = reviewDAO.list(p.getUser().getId());
	    request.setAttribute("reviews", reviews);
	    
	    request.setAttribute("reviewCount", reviews.size());
	    request.setAttribute("p", p);
	    return "product.jsp";       
	}   
	public String myproduct(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user =(User) request.getSession().getAttribute("user");
	     String status="all";    
	     List<Product> ps = productDAO.list(user,status, page.getStart(),page.getCount());
	     status="dealing";
	     List<Product>  Dealps = productDAO.list(user, status);	 
	     status="fail";
	     List<Product> failProduct = productDAO.list(user, status);
	     int total = productDAO.getTotal(user);
	     List<Category> cs = categoryDAO.list(page.getStart(),page.getCount());
	     Category c=new Category();
	     if (cs!= null && cs.size() >= 1)
	    	 c=cs.get(0);
	     
	     
         page.setTotal(total);
         System.out.println(page.getTotal() + "," + page.getTotalPage());
	     request.setAttribute("ps", ps);	  
	     request.setAttribute("Dealps", Dealps);	  
         request.setAttribute("cs", cs);
         request.setAttribute("firstCtgry", c);	
         request.setAttribute("page", page);
         request.setAttribute("failProduct", failProduct);
	         
	    return "myproduct.jsp";       
	}
	public String addProduct(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("Category"));
        Category c = categoryDAO.get(cid);
        User user =(User) request.getSession().getAttribute("user"); 
        String name= request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        String description = request.getParameter("description");
        Date createDate = new Date();
 
        Product p = new Product();
 
        p.setCategory(c);
        p.setUser(user);
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description);
        p.setCreateDate(createDate);
        p.setStatus("selling");
        
        productDAO.add(p);
        return "@foremyproduct";
    }
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
	    int id = Integer.parseInt(request.getParameter("id"));
	    Product p = productDAO.get(id);
	    List<Category> cs = categoryDAO.list(page.getStart(),page.getCount());
	    
	    request.setAttribute("cid", p.getCategory().getId());
        request.setAttribute("cs", cs);
	    request.setAttribute("p", p);
	    return "include/product/editProduct.jsp";     
	}
	 public String updateProduct(HttpServletRequest request, HttpServletResponse response, Page page) {
		 	int cid = Integer.parseInt(request.getParameter("Category"));
	        Category c = categoryDAO.get(cid);
	        String name= request.getParameter("name");
	        float price = Float.parseFloat(request.getParameter("price"));
	        String description = request.getParameter("description");
	        int id=Integer.parseInt(request.getParameter("id"));
	        
	        Product p = new Product();
	        p.setId(id);
	        p.setCategory(c);
	        p.setName(name);
	        p.setPrice(price);
	        p.setDescription(description);
	        p.setStatus("selling");
	        
	        productDAO.update(p);
	        return "@foremyproduct";
	    }
	 public String deleteProduct(HttpServletRequest request, HttpServletResponse response, Page page) {
	        int id = Integer.parseInt(request.getParameter("id"));
	        productDAO.delete(id);
	        return "@foremyproduct";
	    }
		public String reloadproduct(HttpServletRequest request, HttpServletResponse response, Page page) {
			int pid = Integer.parseInt(request.getParameter("pid"));
			Product p = productDAO.get(pid);
			p.setStatus("selling");
			productDAO.update(p);
			
			User seller =(User) request.getSession().getAttribute("user");
			Order o = orderDAO.get(seller,pid);
			o.setStatus(OrderDAO.fail);
			orderDAO.update(o);
			return "%success";     
		}
	
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
	    User user =(User) request.getSession().getAttribute("user");
	    if(null!=user)
	        return "%success";
	    return "%fail";
	}
	
	public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
		long id = Long.parseLong(request.getParameter("id"));
	    String password = request.getParameter("password");     
	    User user = userDAO.get(id,password);
	     
	    if(null==user){
	        return "%fail"; 
	    }
	    request.getSession().setAttribute("user", user);
	    return "%success";  
	}
	
	public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
	    int cid = Integer.parseInt(request.getParameter("cid"));
	     
	    Category c = new CategoryDAO().get(cid);
	    new ProductDAO().fill(c); 
	     
	    String sort = request.getParameter("sort");
	    if(null!=sort){
	    switch(sort){
	        case "date" :
	            Collections.sort(c.getProducts(),new ProductDateComparator());
	            break;             
	        case "price":
	            Collections.sort(c.getProducts(),new ProductPriceComparator());
	            break;
	        }
	    }
	     
	    request.setAttribute("c", c);
	    return "category.jsp";      
	}
	
	public String search(HttpServletRequest request, HttpServletResponse response, Page page){
	    String keyword = request.getParameter("keyword");
	    List<Product> ps= new ProductDAO().search(keyword,0,20);
	    request.setAttribute("ps",ps);
	    return "searchResult.jsp";
	}   
	
	public String buyone(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		long uid = Long.parseLong(request.getParameter("uid"));
	    
	    Order o = orderDAO.getNew(uid, pid);
	    
	    request.setAttribute("o", o);
	    return "@buySuccess.jsp?sellerId="+o.getSellerId();
	}
	
	public String bought(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user =(User) request.getSession().getAttribute("user");
		List<Order> os= orderDAO.list(user.getId(),OrderDAO.delete);	
		request.setAttribute("os", os);
	     
	    return "bought.jsp";        
	}
	
	public String confirmPay(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(OrderDAO.success);
		java.util.Date dt = new java.util.Date();
		o.setConfirmDate_B(dt);
		orderDAO.update(o);
		return "%success";     
	}
	
	public String orderConfirmed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order o = orderDAO.get(oid);
		o.setStatus(OrderDAO.d_seller);
		java.util.Date dt = new java.util.Date();
		o.setConfirmDate_S(dt);
		orderDAO.update(o);
		return "%success";
	}
	
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response, Page page){
	    int oid = Integer.parseInt(request.getParameter("oid"));
	    Order o = orderDAO.get(oid);
        o.setStatus(OrderDAO.delete);
	    orderDAO.update(o);
	    return "%success";      
	}
	
	public String review(HttpServletRequest request, HttpServletResponse response, Page page) {
	    int oid = Integer.parseInt(request.getParameter("oid"));
	    Order o = orderDAO.get(oid);
	    Product p = o.getProduct();
	    List<Review> reviews = reviewDAO.list(o.getSellerId());
	    int reviewCount = reviews.size();
	    request.setAttribute("p", p);
	    request.setAttribute("o", o);
	    request.setAttribute("reviews", reviews);
	    request.setAttribute("reviewCount", reviewCount);
	    return "review.jsp";        
	}
	
	public String doreview(HttpServletRequest request, HttpServletResponse response, Page page) {
	    int oid = Integer.parseInt(request.getParameter("oid"));
	    Order o = orderDAO.get(oid);
	    o.setStatus(OrderDAO.finish);
	    orderDAO.update(o);
	    int pid = Integer.parseInt(request.getParameter("pid"));
	    Product p = productDAO.get(pid);
	    User seller = p.getUser();
	     
	    String content = request.getParameter("content");
	     
	    content = HtmlUtils.htmlEscape(content);
	 
	    User buyer =(User) request.getSession().getAttribute("user");
	    Review review = new Review();
	    review.setContent(content);
	    review.setProduct(p);
	    review.setCreateDate(new Date());
	    review.setBuyer(buyer);
	    review.setSeller(seller);
	    reviewDAO.add(review);
	     
	    return "@forereview?oid="+oid+"&showonly=true";     
	}
}
