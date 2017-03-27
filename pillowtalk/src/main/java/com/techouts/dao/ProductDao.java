package com.techouts.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.techouts.controller.CartController;
import com.techouts.domain.Address;
import com.techouts.domain.Category;
import com.techouts.domain.Order;
import com.techouts.domain.Product;


/**
 * @author Janardhan
 *
 */
public class ProductDao {

	private  JdbcTemplate jt;
	private static final Logger logger = Logger.getLogger(CartController.class);

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	
	
	/**
	 * @return 
	 */
	public Map<Category, List<Category>> getCategories() {
		Map<Category, List<Category>> all_categories = new LinkedHashMap<Category, List<Category>>();
		String sql1 = "select category_id, category_name from category where super_id='null'";
      System.out.println("get categories");
		List<Category> category_list = jt.query(sql1,
				new RowMapper<Category>() {

					public Category mapRow(ResultSet rs, int row)
							throws SQLException {
						Category category = new Category();
						category.setCategory_id(rs.getString(1));
						category.setCategory_name(rs.getString(2));

						return category;
					}

				});

		ListIterator<Category> itr = category_list.listIterator();

		while (itr.hasNext()) {
			Category category = itr.next();
			String sql2 = "SELECT category_id, category_name FROM category "
					+ "where super_id ='" + category.getCategory_id() + "'";

			List<Category> subCategories_list = jt.query(sql2,
					new RowMapper<Category>() {

						public Category mapRow(ResultSet rs, int row)
								throws SQLException {
							Category category = new Category();
							category.setCategory_id(rs.getString(1));
							category.setCategory_name(rs.getString(2));
							return category;
						}

					});
			all_categories.put(category, subCategories_list);
		}

		logger.info(all_categories);
		return all_categories;
	}

	/**
	 * @param id
	 * @return all the products available for the main Category
	 */
	public List<Product> allProductsList(String id) {
		
		List<Product> products = new ArrayList<Product>();
		String sql = "select category_id from category where super_id='" + id
				+ "'";
		List<String> category_Ids = jt.query(sql, new RowMapper<String>() {

			public String mapRow(ResultSet rs, int row) throws SQLException {
				
				return rs.getString("category_id");
			}

		});

		ListIterator<String> itr = category_Ids.listIterator();

		while (itr.hasNext()) {
			String category_id = itr.next();
			List<Product> product = productsList(category_id);
			products.addAll(product);
		}

		return products;
	}

	
	/**
	 * @param category_id
	 * @return
	 */
	public List<Product> productsList(String category_id) {
		

		String sql = "select product_id, product_name, price, quantity from "
				+ "product where super_id = '" + category_id + "'";
		List<Product> products = getProducts(sql);

		return products;
	}

	
	public Blob getImageFromDB(String product_id) {
		
		String sql = "select image from product where product_id = '"
				+ product_id + "'";
		Blob image = jt.queryForObject(sql, Blob.class);
		logger.info("MESSAGE FROM GET IMAGE FROM DB: " + image);
		return image;
	}

	
	/**
	 * @param product_id
	 * @return
	 */
	public Product productDetails(String product_id) {
		
		String sql = "select product_id, product_name, price, quantity from "
				+ "product where product_id = '" + product_id + "'";
		List<Product> products = getProducts(sql);

		return products.get(0);
	}

	
	public List<Product> getProducts(String sql) {

		List<Product> products = jt.query(sql, new RowMapper<Product>() {

			public Product mapRow(ResultSet rs, int row) throws SQLException {
			
				Product product = new Product();
				product.setProduct_id(rs.getString(1));
				product.setProduct_name(rs.getString(2));
				product.setPrice(rs.getFloat(3));
				product.setQuantity(rs.getInt(4));

				return product;
			}
		});
		return products;
	}

	
	/**
	 * @param product_id
	 * @return
	 */
	public Product cartProducts(String product_id) {

		String sql = "select product_id, product_name, price from product where"
				+ " product_id = '" + product_id + "'";

		List<Product> products = jt.query(sql, new RowMapper<Product>() {

			public Product mapRow(ResultSet rs, int row) throws SQLException {
				
				Product product = new Product();
				product.setProduct_id(rs.getString(1));
				product.setProduct_name(rs.getString(2));
				product.setPrice(rs.getFloat(3));

				return product;
			}
		});
		return products.get(0);
	}

	
	 public void saveOrder(Order order) {
	        
	    String sql = "insert into order1 values('" + order.getOrder_id()+ "','"+order.getOrder_date()+"','"+ order.getCust_name() + "','"+ order.getPhone() + "','"+order.getEmail()+"','"+order.getBillAmt()+"')";
					
	    jt.update(sql);
	       
	 
	       
	        }
	 
	 public int saveAddress(Address address){
		   System.out.println("save address");
		   
		   String sql = "insert into address values('" + address.getName() + "', '"+ address.getEmail() + "','"
		     		+ address.getLine1()+ "', '" + address.getLine2() + "',' "
					+address.getCity()+"', '"+address.getState()+"', '"
					+address.getZip()+"', '"+address.getCountry()+"','"+address.getOrder_id()+"')";
		   
		   return jt.update(sql);

	   }
	    
	 public void saveOrder_info(String order_id,String product_id,String product_name,int quantity,double price,double amount) {
	        
		    String sql = "insert into order_info values('" + order_id+ "','" + product_id+ "','"+product_name+"',"+ quantity + ","+ price +","+ amount +")";
						
		    jt.update(sql);
		       
		 
		       
		        }
}