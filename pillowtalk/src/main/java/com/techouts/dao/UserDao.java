package com.techouts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.techouts.controller.RegisterLoginController;
import com.techouts.domain.User;

public class UserDao {


        private JdbcTemplate jt;
		private static final Logger logger = Logger
				.getLogger(UserDao.class);

		public void setJt(JdbcTemplate jt) {
			this.jt = jt;
		}

		/**
		 * This method is used to check email existence in the DB
		 * 
		 * @param email
		 * @return true if email already exist in the database otherwise return
		 *         false
		 */
		public boolean checkUserName(String email) {
			String sql = "select * from user where email = '" + email + "'";
			List<String> uname = jt.query(sql, new RowMapper<String>() {

				public String mapRow(ResultSet rs, int row) throws SQLException {
					// TODO Auto-generated method stub

					String uname = rs.getString(1);

					return uname;
				}
			});

			return (uname.isEmpty() ? false : true);
		}

		/**
		 * This method is used to save details of the user into DB
		 * 
		 * @param users
		 * @return 0 if data insertion is failed
		 */
		public int save(User users) {
			String sql = "insert into user values('" + users.getTitle() + "', '"
					+ users.getFirstname() + "', '" + users.getSurname() + "',' "
					+ users.getEmail() + "', " + users.getNumber() + ", '"
					+ users.getPassword() + "', 0, 'ROLE_USER')";

			return jt.update(sql);
		}

		/**
		 * This method is used to validate whether email and password are correct or
		 * not
		 * 
		 * @param email
		 * @param password
		 * @return Users details if email and password exists in DB otherwise null
		 */
		public User loginValidate(String email, String password) {

			logger.info(email+" "+password);

			String sql = "select * from user where email = '" + email
					+ "' and password = '" + password + "'";
			List<User> users = getAllUsersDetails(sql);
			
			return (users.isEmpty() ? new User() : users.get(0));
		}
		
		/**
		 * This method is used to get details of the Users
		 * 
		 * @param email
		 * @return Users details
		 */
		public User getUserDetails(String email) {
		JdbcTemplate template=new JdbcTemplate();
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/demo");
		dataSource.setUsername("root");
		dataSource.setPassword("techouts");
		template.setDataSource(dataSource);
			logger.info("from getUserDetails email::"+email);
			String sql = "select * from user where email = '" + email + "'";
            logger.info("q e:"+template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class)));
            User User=template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class));
            logger.info("User.getFirstname(); ::"+User.getFirstname());
			return template.queryForObject(sql, new BeanPropertyRowMapper<User>(
					User.class));
		}

		
		/**
		 * This method is used by loginValidate() and getAllUsers() to get user 
		 * details
		 * 
		 * @param sql
		 * @return all Users details stored in DB
		 */
		private List<User> getAllUsersDetails(String sql) {
			// TODO Auto-generated method stub
			System.out.println(sql);
			List<User> users = new ArrayList<User>(); 
			users = jt.query(sql, new RowMapper<User>() {
            
				public User mapRow(ResultSet rs, int row) throws SQLException {
					// TODO Auto-generated method stub
					User user = new User();

					user.setTitle(rs.getString(1));
					user.setFirstname(rs.getString(2));
					user.setSurname(rs.getString(3));
					
					user.setEmail(rs.getString(4));
					user.setNumber(rs.getLong(5));
		
                    System.out.println("user::"+user);
					return user;
				}
			});
            System.out.println("users::"+users);
			return users;
		}

		/**
		 * This method is used to Update the User details
		 * 
		 * @param users
		 * @return number of users updated if update is success otherwise 0
		 */
		public int update(User users) {
			String sql = "update user set title = '" + users.getTitle()
					+ "', firstname = '" + users.getFirstname() + "', surname = '"
					+ users.getSurname() + "', number = " + users.getNumber()
					+ ", password = '" + users.getPassword() + "' where "
					+ "email = '" + users.getEmail() + "'";

			return jt.update(sql);
		}

		
	}



