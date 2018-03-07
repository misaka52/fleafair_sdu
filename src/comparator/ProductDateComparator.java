package comparator;
 
import java.util.Comparator;

import bean.Product;
 
public class ProductDateComparator implements Comparator<Product>{
 
    @Override
    public int compare(Product p1, Product p2) {
    	System.out.println(p1.getCreateDate() + "\n" + p2.getCreateDate());
    	try {
    		p1.getCreateDate().compareTo(p2.getCreateDate());
    	} catch(Exception e) {
    		System.out.println("出错了");
    		e.printStackTrace();
    	}
        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }
 
}