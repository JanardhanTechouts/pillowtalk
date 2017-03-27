<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/bootstrap.css" rel="stylesheet">

<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	
}
.bottom {
	
	background-color: #ffffff;
}

li {
	float: left;
}

li a, .dropbtn {
	display: inline-block;
	color: #363649;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

li a:hover, .dropdown:hover .dropbtn {
	background-color: none;
}

li.dropdown {
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: #363649;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover {
	background-color: #f1f1f1
}

.dropdown:hover .dropdown-content {
	display: block;
}
.middle-left{

margin-right: 12px;
    padding: 0px 456px;

}
.dropdown-content1{
    color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
	float:right;
	color: #878988;

    

}

</style>


</head>
<body>
<div class="middle-left">
			<ul id="list-left">
				<li class="list-ele-left"><a href="home"><img
						src="<c:url value="resources/images/PTLogo.jpg" />"
						alt="pillow talk"></a></li>
			</ul>
		</div>


		<div class="list-ele-right">
			<ul id="list-right">
            
				<li class="list-ele-right"><c:choose>
						<c:when test="${email != null}">
						Welcome ${name},&nbsp;&nbsp;&nbsp;<a href="logoutpage"> Logout
							</a>
							<a href=profilepage> MyAccount</a>
<!-- 							<a href=orders> orders</a> -->
						</c:when> 						
						<c:otherwise>
							<a href="login">Login/Register</a>
						</c:otherwise>
					</c:choose></li>
				<a href="cart_page"
					class="dropdown-content1" style="padding: 2px 0px;"><img src="resources/images/cart.jpg" height="42" width="42"> <span id="total">
							<c:choose>
								<c:when test="${total != null}">Rs&nbsp;${total} </c:when>
								<c:otherwise>Rs&nbsp;0.00</c:otherwise>
							</c:choose>
					</span> <span id="item_count"> <c:choose>
								<c:when test="${total_count != null}"> (${total_count}&nbsp;items) </c:when>
								<c:otherwise>(items)</c:otherwise>
							</c:choose>
					</span>
				</a>
					<div class="dropdown-content1"></div></li>
				<li class="list-ele-right"><a href="checkout"> CHECKOUT </a></li>
			</ul>
		</div>

		

		

		<div class="bottom">

			<div id="navigation">

				<hr>
				<ul id="nav-list">
                  <li><a href="home"><img src="resources/images/home.jpg" height="30" width="30"></a></li>
				 
					<c:forEach var="item" items="${categories}">
						<li class="dropdown"><a
							href="main?id=${item.key.category_id}" class="dropbtn">
								${item.key.category_name}</a>
							<div class="dropdown-content">
								<c:forEach var="list" items="${item.value}">
									<a href="c?id=${list.category_id}"> ${list.category_name}</a>
								</c:forEach>
							</div></li>
					</c:forEach>

				</ul>
			</div>

			<hr>
		</div>

	</div>

</body>
</html>