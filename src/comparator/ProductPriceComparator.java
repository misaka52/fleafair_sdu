package comparator;
 
import java.util.Comparator;

import bean.Product;
 
public class ProductPriceComparator implements Comparator<Product>{
 
    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPrice()-p2.getPrice());
    }
 
}