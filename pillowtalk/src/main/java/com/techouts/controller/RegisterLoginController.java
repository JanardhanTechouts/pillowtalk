package com.techouts.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;





	

	import java.net.MalformedURLException;
	import java.net.URL;

	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;

	import org.apache.log4j.Logger;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.ApplicationEventPublisher;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.ModelMap;
	import org.springframework.validation.BindingResult;
	import org.springframework.validation.Errors;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.ResponseBody;
	import org.springframework.web.context.request.WebRequest;
	import org.springframework.web.servlet.ModelAndView;
    import com.techouts.dao.UserDao;
    import com.techouts.domain.User;

	
	/**
	 * @author Janardhan
	 *
	 */
	@Controller
	public class RegisterLoginController {

		@Autowired
		private UserDao users_dao;
		@Autowired
		ApplicationEventPublisher eventPublisher;
        
		private static final Logger logger = Logger
				.getLogger(RegisterLoginController.class);

		/**
		 * Invoked when user clicked Login/Register
		 * 
		 * @return Model of the login-register view page
		 */
		@RequestMapping(value = "/login")
		public ModelAndView displayLogin(
				@RequestParam(value = "error", required = false) String error,
				@RequestParam(value = "logout", required = false) String logout) {

			// Navigates to "loginregister" page
			ModelAndView model = new ModelAndView();
			if (error != null) {
				model.addObject("error", "Invalid username and password!");
			}

			if (logout != null) {
				model.addObject("message", "You've been logged out successfully.");
			}
			return new ModelAndView("loginregister", "command", new User());
		}

		/**
		 * Invoked when an AJAX call for checking the email occurred
		 * 
		 * @param email
		 * @return true if email is already existed in DB otherwise false
		 */
		@RequestMapping("/checkEmailInDB")
		public @ResponseBody String emailDBValidation(
				@RequestParam("user_email") String email) {

			boolean status = users_dao.checkUserName(email);
			String response = "false";
			logger.info("STATUS is: " + status);
			if (status) {
				response = "true";
			}
			logger.info("RESPONSE is: " + response);
			return response;
		}

		/**
		 * Invoked when user clicked the Register button
		 * 
		 * @param request
		 * @param response
		 * @param users
		 * @return Model of register-login view page after saving user details in DB
		 */
		@RequestMapping(value = "/save", method = RequestMethod.POST)
		public ModelAndView save(HttpServletRequest request, 
				@ModelAttribute("users") User users) { 
			String msg; // Used to store message

			// check whether email already registered in the DB
			boolean check = users_dao.checkUserName(users.getEmail());

			if (check) {
				/* Executed when check is true that is email already registered */
				msg = "User Name already exists, click login to login to your "
						+ "account or register with different User Name.";
			} else {
				/* Executed when check is false that is email not registered */
				int status = users_dao.save(users);
				if (status > 0) {
					msg = "Registered Succeessfully..";
				} else {
					msg = "Registration Failed..";
				}
			}
			request.setAttribute("message", msg);
			return new ModelAndView("loginregister", "command", new User());
		}

		@RequestMapping(value = "/login", method = RequestMethod.POST)
		public ModelAndView login(@ModelAttribute("users") User users,
				HttpServletRequest request, HttpServletResponse response) {

			logger.info("message from login mapping");
			System.out.println(users.getEmail()+" "+ users.getPassword());
			users = users_dao.loginValidate(users.getEmail(), users.getPassword());

			ModelAndView model = new ModelAndView();

			if (users.getFirstname() != null) {
				HttpSession session = request.getSession();
				session.setAttribute("email", users.getEmail());

				session.setAttribute("name", users.getFirstname());

				model.setViewName("home");

				return model;
			} else {
				model.addObject("error", "Invalid username or password!");
				model.addObject("command", new User());
				model.setViewName("loginregister");

				return model;
			}
		}

		@RequestMapping("/profilepage")
		public ModelAndView view(@ModelAttribute("SpringWeb1") User users,
				ModelMap model, HttpServletRequest request,
				HttpServletResponse response) {
System.out.println("profile");
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
            System.out.println("email::"+email);
			if (email != null) {
				User user = users_dao.getUserDetails(email);
                
				model.addAttribute("title", user.getTitle());
				model.addAttribute("firstname", user.getFirstname());
				model.addAttribute("surname", user.getSurname());
				model.addAttribute("number", user.getNumber());
				model.addAttribute("password", user.getPassword());
				System.out.println("user::"+user);
				return new ModelAndView("profilepage", "command", model);
			} else {

				URL url = null;
				try {
					url = new URL(request.getRequestURL().toString());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String path = url.getPath();
				String sub = path.substring(17);
				String msg = "please Login first";

				request.setAttribute("action", sub);
				request.setAttribute("message", msg);
				return new ModelAndView("loginregister", "command", new User());
			}

		}

		@RequestMapping(value = "/profilepage", method = RequestMethod.POST)
		public ModelAndView viewProfile(HttpServletRequest request,
				HttpServletResponse response, @ModelAttribute("users") User users,
				ModelMap model) {
  System.out.println("profile...");
			users = users_dao.loginValidate(users.getEmail(), users.getPassword());
            
			if (users.getFirstname() != null) {
				HttpSession session = request.getSession();
				session.setAttribute("email", users.getEmail());

				return new ModelAndView("redirect:/profilepage");
			} else {
				String msg = "Invalid User Name or Password..";
				request.setAttribute("message", msg);
				return new ModelAndView("loginregister", "command", new User());
			}

		}

		@RequestMapping("/logoutpage")
		public ModelAndView logout(HttpServletRequest request,
				HttpServletResponse response) {
			HttpSession session = request.getSession();
			session.invalidate();
			String msg = "Successfully logged out";

			request.setAttribute("message", msg);
			return new ModelAndView("loginregister", "command", new User());
		}
	
}
