package com.techouts.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.techouts.dao.UserDao;
import com.techouts.domain.User;




@Path(value = "/UserService")
@Component
public class RegisterRestService {
    
	
	private UserDao users_dao;
	
	public void setUsers_dao(UserDao users_dao) {
		this.users_dao = users_dao;
	}


	private static final Logger logger = Logger.getLogger(RegisterRestService.class);
	private static final String SUCCESS_RESULT = "<result>success</result>";
	private static final String FAILURE_RESULT = "<result>failure</result>";

	

	@POST
	@Path("/user")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public String createuser(@QueryParam("title") String title,
			@QueryParam("firstname") String firstname,
			@QueryParam("surname") String surname,
			@QueryParam("number") long number, @QueryParam("email") String email,
			@QueryParam("password") String password,
			@Context HttpServletResponse servletResponse) throws IOException {
        //logger.info("createuser:"+user);
		User user = new User(title, firstname, surname, email,number,password);
        logger.info("user::"+user);
		int result = users_dao.save(user);
		if (result == 1) {
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}

	
	@GET
	@Path("/users/{email}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public User getUser(@PathParam("email") String email) {
		
		System.out.print("from rest:"+email);
		System.out.println("users_dao:"+users_dao);
		UserDao dao=new UserDao();
		User user = dao.getUserDetails(email);
		return user;
	}
    
	@POST
	@Path("/updateUser")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public String UpdateUser(User user) throws IOException {
    
		logger.info("Message from JsonUser " + user + " ");
		int result = users_dao.update(user);

		if (result == 1) {
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}
	
}


