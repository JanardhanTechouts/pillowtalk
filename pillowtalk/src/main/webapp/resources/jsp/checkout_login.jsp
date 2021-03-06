<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	position: relative;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 0px solid #000;
}
.largebtn {
	position: relative;
	top: 2px;
	left: 20%;
	background-color: #ff3b57;
	border: 1px solid #C3B3B3;
	color: #fff;
	font-size: 16px;
	padding: 12px 12px;
	text-transform: uppercase;
	text-align: center;
	text-decoration: none;
	cursor: pointer;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>
	<div id="login-box">

		<h2>Login with Username and Password</h2>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm' action="profilepage" method="POST">
<%-- 			action="<c:url value='j_spring_security_check' />" method='POST'> --%>

			<table>
				<tr>
					<td>Email:</td>
					<td><input type='text' name="email"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name="password" /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit" style="background-color: #ff3b57;border: 1px solid #C3B3B3"
						value="LOGIN" /></td>
				</tr>
			</table>

<%-- 			  <input type="hidden" name="${_csrf.parameterName}" --%>
<%-- 			value="${_csrf.token}" />  --%>

		</form>
	</div>

</body>
</html>