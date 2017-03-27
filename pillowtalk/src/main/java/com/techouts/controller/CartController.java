package com.techouts.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.techouts.dao.ProductDao;
import com.techouts.domain.Address;
import com.techouts.domain.Category;
import com.techouts.domain.Order;
import com.techouts.domain.Product;
import com.techouts.domain.User;
import com.techouts.service.EmailService;

/**
 * @author Janardhan
 *
 */
@Controller
public class CartController {
	@Autowired
	private ProductDao product_dao;
	

	private static final Logger logger = Logger.getLogger(CartController.class);

	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/home")
	public ModelAndView displayHome(ModelMap model, HttpServletRequest request) {
	
		logger.info("MESSAGE FROM HOME: ");
		Map<Category, List<Category>> categories = product_dao.getCategories();
		ServletContext context = request.getServletContext();
		context.setAttribute("categories", categories);
        
		return new ModelAndView("home");
	}

	@RequestMapping("/main")
	public ModelAndView displayAllProducts(@RequestParam String id) {
		logger.info("MESSAGE FROM DISPLAY ALL PRODUCT " + id);
		List<Product> products = product_dao.allProductsList(id);

		return new ModelAndView("product_list_page", "products", products);
	}

	@RequestMapping("/c")
	public ModelAndView displayProducts(@RequestParam String id) {
		logger.info("MESSAGE FROM DISPLAY PRODUCT " + id);
		List<Product> products = product_dao.productsList(id);
		logger.info("MESSAGE FROM DISPLAY PRODUCT " + products.get(2));

		return new ModelAndView("product_list_page", "products", products);
	}

	@RequestMapping("/p")
	public ModelAndView displayProductDetails(@RequestParam String id) {
		logger.info("MESSAGE FROM DISPLAY PRODUCT Details" + id);
		Product product = product_dao.productDetails(id);
		logger.info("MESSAGE FROM DISPLAY PRODUCT Details" + product.toString());

		return new ModelAndView("product_details", "product", product);
	}

	@RequestMapping(value = "/getImage")
	public void getImage(HttpServletResponse response, @RequestParam String id)
			throws IOException, SQLException {

		logger.info("MESSAGE FROM Get IMAGE " + id);
		Blob image = product_dao.getImageFromDB(id);
		ServletOutputStream out = response.getOutputStream();
		
		try {
			
			InputStream io = image.getBinaryStream();
			long length = image.length();
			int buffersize = 1024;
			byte[] buffer = new byte[buffersize];
			response.setContentType("image/jpeg");
			while ((length = io.read(buffer)) != -1) {
				out.write(buffer, 0, (int) length);
			}
			io.close();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param priceStr
	 * @param product_id
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addToCart")
	@ResponseBody
	public String addToCart(@RequestParam("price") String priceStr,
			@RequestParam("id") String product_id, @RequestParam("quantity") int quantity,HttpServletRequest request,
			HttpSession session) {
        logger.info("add to cart"+quantity);
		logger.info("MESSAGE FROM CART: " + product_id);

		float price = Float.parseFloat(priceStr);
		float sum = 0;
		int total_count = 0; // to count total number of items added to cart
		int item_count = 0; // to count number of times item added to cart

		LinkedHashMap<String, Integer> product_ids_list = new LinkedHashMap<String, Integer>();

		if (session.getAttribute("total") == null || session.getAttribute("total")=="") {
			logger.info("message FIRST TIME," + item_count);
			sum = price;
			total_count++;
			item_count++;
			product_ids_list.put(product_id, item_count);
			logger.info("message FIRST TIME, " + item_count + " " + total_count);

		} else {
			logger.info("total::"+session.getAttribute("total"));
			sum = (Float) session.getAttribute("total");
			logger.info("message of id," + item_count);
			total_count = (Integer) session.getAttribute("total_count");
			logger.info("message of total," + total_count);
			product_ids_list = (LinkedHashMap<String, Integer>) session
					.getAttribute("cart_product_ids");

			if (product_ids_list.containsKey(product_id)) {
				item_count = product_ids_list.get(product_id);
			}
			sum += price;
		
			total_count++;
			item_count++;
			product_ids_list.put(product_id, item_count);
			System.out.println("item_count::"+item_count);
		}

		session.setAttribute("total", sum);
		session.setAttribute("total_count", total_count);
		logger.info("total_count::"+total_count);
		session.setAttribute("cart_product_ids", product_ids_list);
		session.setMaxInactiveInterval(1200);

		logger.info("MESSAGE FROM CART- cart products: " + product_ids_list);
		return sum + "," + total_count;

	}
    
	@SuppressWarnings("unchecked")
	@RequestMapping("/cart_page")
	public String displayCartPage(HttpSession session) {

		LinkedList<Product> cart_products = new LinkedList<Product>();
		logger.info("cart_product_ids::"+session.getAttribute("cart_product_ids"));
		
		if(session.getAttribute("cart_products_ids")== " "){
			return "emptycart_page";
		}
		LinkedHashMap<String, Integer> cart_products_ids = (LinkedHashMap<String, Integer>) session
				.getAttribute("cart_product_ids");
		if (cart_products_ids == null || cart_products_ids.isEmpty() || session.getAttribute("cart_products_ids")== "") {
			logger.info("SENT EMPTY CART PAGE ");
			return "emptycart_page";
		} else {
			for (Map.Entry<String, Integer> entry : cart_products_ids
					.entrySet()) {

				Product product = product_dao.cartProducts(entry.getKey());
				product.setQuantity(entry.getValue());
				cart_products.add(product);
			}
			session.setAttribute("cart_products", cart_products);
			return "cart_page";

		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/updateCart")
	public String updateCart(@RequestParam("id") String product_id,
			@RequestParam("quantity") String quantityStr, HttpSession session) {

		logger.info("MESSAGE FROM UPDATE CART: " + product_id + " "
				+ quantityStr);
        
		int quantity = Integer.parseInt(quantityStr);
		int total_count = (Integer) session.getAttribute("total_count");
		float total = (Float) session.getAttribute("total");
		float subTotal = 0.0f;
		LinkedList<Product> cart_products = (LinkedList<Product>) session
				.getAttribute("cart_products");
		LinkedHashMap<String, Integer> cart_product_ids = (LinkedHashMap<String, Integer>) session
				.getAttribute("cart_product_ids");

		Iterator<Product> itr = cart_products.iterator();
		logger.info(cart_products);
		while (itr.hasNext()) {
			Product product = itr.next();

			if (product.getProduct_id().equals(product_id)) {
				logger.info("MESSAGE AFTER UPDATE: " + product.getQuantity());
				float price = product.getPrice();
				int prevQuantity = product.getQuantity();
				int quantityDiff = quantity - prevQuantity;	

				subTotal = price * quantity;
				product.setQuantity(quantity);

				total_count += quantityDiff;
				total += (price * quantityDiff);
				logger.info("MESSAGE AFTER UPDATE: " + product.getQuantity());
			}

		}
		cart_product_ids.put(product_id, quantity);

		session.setAttribute("total_count", total_count);
		session.setAttribute("total", total);
		session.setAttribute("cart_products", cart_products);
		session.setAttribute("cart_product_ids", cart_product_ids);
        
		String response = "true," + subTotal;
		return response;

	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/removeFromCart")
	public String removeFromCart(@RequestParam("id") String product_id,
			HttpSession session) {

		logger.info("MESSAGE FROM REMOVE FROM CART: " + product_id);
        
		String response = "true";
		int total_count = (Integer) session.getAttribute("total_count");
		float total = (Float) session.getAttribute("total");
		LinkedList<Product> cart_products = (LinkedList<Product>) session
				.getAttribute("cart_products");
		LinkedHashMap<String, Integer> cart_product_ids = (LinkedHashMap<String, Integer>) session
				.getAttribute("cart_product_ids");

		Iterator<Product> itr = cart_products.iterator();
		logger.info(cart_products);
		while (itr.hasNext()) {
			Product product = itr.next();
			if (product.getProduct_id().equals(product_id)) {
				float price = product.getPrice();
				int quantity = product.getQuantity();

				total_count -= quantity;
				total -= (price * quantity);
				logger.info("MESSAGE AFTER REMOVE: " + total + " "
						+ total_count);
				itr.remove();
				logger.info(cart_products);
			}

		}
		cart_product_ids.remove(product_id);

		if (cart_product_ids == null || cart_product_ids.isEmpty()) {
			logger.info("MESSAGE OF EMPTY CART " + response);
			response = "true,empty";
			logger.info("MESSAGE OF EMPTY CART " + response);
		}

		session.setAttribute("total_count", total_count);
		session.setAttribute("total", total);
		session.setAttribute("cart_products", cart_products);
		session.setAttribute("cart_product_ids", cart_product_ids);
		logger.info("MESSAGE OF EMPTY CART " + response);
		return response;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkout")
	public String checkout(HttpSession session) {
		String email = (String) session.getAttribute("email");
		LinkedHashMap<String, Integer> cart_products_ids = (LinkedHashMap<String, Integer>) session
				.getAttribute("cart_product_ids");

		if (cart_products_ids == null || cart_products_ids.isEmpty()) {
			logger.info("SENT EMPTY CART PAGE ");
			return "emptycart_page";
		} else if (email != null) {
			return "redirect:checkout_page";
		} else {
			return "redirect:checkout_validate";
		}

	}
	@RequestMapping(value = "/checkout_login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("checkout_login");

		return model;

	}
	
	/**
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/checkout_page", "/checkout_validate" })
	public String displayCheckoutPage(HttpSession session) {
		logger.debug("checkout_validate");
		LinkedList<Product> cart_products = new LinkedList<Product>();
		LinkedHashMap<String, Integer> cart_products_ids = (LinkedHashMap<String, Integer>) session
				.getAttribute("cart_product_ids");
		if (cart_products_ids == null || cart_products_ids.isEmpty()) {
			logger.info("SENT EMPTY CART PAGE ");
			return "emptycart_page";
		} else {
			for (Map.Entry<String, Integer> entry : cart_products_ids
					.entrySet()) {

				Product product = product_dao.cartProducts(entry.getKey());
				product.setQuantity(entry.getValue());
				cart_products.add(product);
			}
			session.setAttribute("cart_products", cart_products);
			return "checkout_page";
		}
	}
	

	/**
	 * @param session
	 * @return placeorder_page
	 */
	@RequestMapping("/placeorder_page")
	public String displayPlaceOrder_Page(@ModelAttribute("users") User users,HttpServletRequest request,HttpSession session,ModelMap modelMap) {
	
System.out.println("mail from order::"+users.getEmail());
		Enumeration<String> sessionNames = session.getAttributeNames();
		while (sessionNames.hasMoreElements()) {
			logger.info("MESSAGE FROM PLACE ORDER PAGE 1 "
					+ sessionNames.nextElement());
		}
		
		System.out.println("total::"+session.getAttribute("total"));
		System.out.println("total_count::"+session.getAttribute("total_count"));
		System.out.println("cart_products::"+session.getAttribute("cart_products"));
		
		String id=UUID.randomUUID().toString();
		
		LinkedList<Product> products= (LinkedList<Product>) session.getAttribute("cart_products");
		for (int i = 0; i < products.size(); i++) {
			String product_id=products.get(i).getProduct_id();
			String product_name=products.get(i).getProduct_name();
			int quantity=products.get(i).getQuantity();
			double price=products.get(i).getPrice();
			double amount=quantity*price;
			System.out.println("product id:"+product_id);
			System.out.println("product name:"+product_name);
			System.out.println("product quantity:"+quantity);
			System.out.println("product price:"+price);
			System.out.println("amount::"+amount);
			
			product_dao.saveOrder_info(id,product_id,product_name,quantity,price,amount);
			
		}
		
		Float total=(Float) session.getAttribute("total");
		
	session.setAttribute("total", "");
	session.setAttribute("total_count", "");
	session.setAttribute("cart_products", "");
    session.setAttribute("cart_product_ids", "");
		
		
		
        String name=request.getParameter("fname");
        String mobile=request.getParameter("mobile");
        String email=request.getParameter("email");
    	String line1=request.getParameter("line1");
    	String line2=request.getParameter("line2");
    	String city=request.getParameter("city");
    	String state=request.getParameter("state");
    	String zip=request.getParameter("zip");
    	String country=request.getParameter("country");
    	
    	
    	Address address=new Address();
    	address.setName(name);
    	address.setEmail(email);
    	address.setLine1(line1);
    	address.setLine2(line2);
    	address.setCity(city);
    	address.setState(state);
    	address.setZip(zip);
    	address.setCountry(country);
    	address.setOrder_id(id);
    	 int count=product_dao.saveAddress(address);
    	 
    	java.util.Date dt = new java.util.Date();
 		java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		String currentTime = sdf.format(dt);
 	    	
 		logger.info("mobile::"+mobile);
    	Order order = new Order();
 		
 	    
 		modelMap.addAttribute("id",id);
 		modelMap.addAttribute("billAmt", total);
 		System.out.println("id::"+id);	
 	    order.setOrder_id(id);
 	    order.setOrder_date(currentTime);
 	    order.setCust_name(name);
 	    order.setPhone(Long.parseLong(mobile));
 	    order.setEmail(email);
 	    order.setBillAmt(total);
        product_dao.saveOrder(order);
         
         
		// Spring Bean file you specified in /src/main/resources folder
				String confFile = "mail-beans.xml";
				ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(confFile);
		        
				System.out.println("email::"+address.getEmail());
				EmailService emailAPI = (EmailService) context.getBean("email");
				String toAddr = address.getEmail();
				String fromAddr = "sjrece999@gmail.com";
		        
				// email subject
				String subject = "Hey.. This email sent by pillowtalk";
		 
				// email body
				String body = "Your order is successfully placed....Thank you for Shopping......\nYour order id is::"+id+"\nYour Bill Amount is::Rs. "+total;
				emailAPI.readyToSendEmail(toAddr, fromAddr, subject, body);
				
		return "placeorder_page";

	}
	

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName()
			+ ", you do not have permission to access this page!");
		} else {
			model.addObject("msg",
			"You do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}


}
