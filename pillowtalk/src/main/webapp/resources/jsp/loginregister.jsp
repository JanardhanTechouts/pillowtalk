<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">
#registerButton11 {
	position: relative;
	top: 2px;
	left: 85%;
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
<script>
	function formvalidation() {
		if (validateName()) {
			if (validateEmail()) {
				if (validateNumber()) {
					if (validatePassword()) {
						if (validateConfirmPassword()) {
							return true;
						} else {
							document.getElementById("confirmPassword").focus();
						}
					} else {
						document.getElementById("registerPassword").focus();
					}
				} else {
					document.getElementById("number").focus();
				}
			} else {
				document.getElementById("registerEmail").focus();
			}
		} else {
			alert("in validation");
			document.getElementById("firstname").focus();
		}
	}

	function validateName() {
		var x = document.getElementById("firstname").value;

		if (x == "") {
			document.getElementById("nameValidationMsg").innerHTML = "Name shouldn't be empty";
			document.getElementById("firstname");
			return false;
		} else if (x.match(/[^a-z A-Z]/)) {
			document.getElementById("nameValidationMsg").innerHTML = "Name shouldn only contains Charecters";
			return false;
		} else {
			document.getElementById("nameValidationMsg").innerHTML = "";
			return true;
		}

		/*  x.value = x.value.toUpperCase(); */
	}

	function validateEmail() {

		var xhttp;
		var emailID = document.getElementById("registerEmail").value;
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		atpos = emailID.indexOf("@");
		dotpos = emailID.lastIndexOf(".");

		if (emailID.match(mailformat)) {
			$
					.ajax({
						type : 'post',
						url : 'checkEmailInDB',
						data : {
							user_email : emailID,
						},
						success : function(response) {

							if (response == "true") {
								$('#emailValidationMsg')
										.text(
												"Email already registered, please enter another Email");
								$('#emailValidationMsg').prev().focus();
								return false;
							} else {
								$('#emailValidationMsg').text("");
								return true;
							}
						}
					});

		} else {
			document.getElementById("emailValidationMsg").innerHTML = "Please enter correct email ID";
			return false;
		}
	}

	function validateNumber() {
		var x = document.getElementById("number").value;

		if (isNaN(x)) {
			document.getElementById("numberValidationMsg").innerHTML = "please enter a number";
			return false;

		} else if (x.length != 10) {
			document.getElementById("numberValidationMsg").innerHTML = "Phone Number should be 10 digits";
			return false;
		} else {
			document.getElementById("numberValidationMsg").innerHTML = "";
			return true;
		}
	}

	function validatePassword() {
		var x = document.getElementById("registerPassword").value;
		var passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
		if (x.match(passw)) {
			document.getElementById("passwordValidationMsg").innerHTML = "";
			return true;
		} else {
			document.getElementById("passwordValidationMsg").innerHTML = "password should be 6-20 characters which contain at least "
					+ "<br> one numeric, one uppercase and one lowercase letter";
			return false;
		}
	}

	function validateConfirmPassword() {
		var pwd1 = document.getElementById("registerPassword").value;
		var pwd2 = document.getElementById("confirmPassword").value;

		if (pwd1.localeCompare(pwd2) != 0) {
			document.getElementById("pwdValidationMsg").innerHTML = "Password not matched";
			return false;

		} else {
			document.getElementById("pwdValidationMsg").innerHTML = "";
			return true;
		}
	}

	function checkBoxValidation() {

		if (document.getElementById("checkBox").checked) {
			document.getElementById("registerButton").disabled = false;
			document.getElementById("registerButton").value = "Register";
		} else {
			document.getElementById("registerButton").disabled = true;
			document.getElementById("registerButton").value = "Click on the Check box to activate me";
		}

	}
</script>

</head>
<body>
	<div class="content">
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>
		<br>
		<table style="width: 100%;">
			<tr>
				<td style="width: 50%;">
					<table cellpadding="5">
						<form:form method="post" action="save" name="registration" 
							onsubmit="formvalidation()">
                              
                              <tr>
                              <td><form:label path="title">Title *</label></form:label></td>
   <td> <form:select id="title" path="title" name="title">
     <option value="" disabled="disabled" selected="selected">Please select</option>
      <option value="mrs">Mrs</option>
      <option value="miss">Miss</option>
      <option value="mr">Mr</option>
      <option value="ms">Ms</option>
    </form:select></td>
    
                              </tr>
							<tr>
								<td><form:label path="firstname">First Name *</form:label></td>
								<td><form:input path="firstname" name="name"
										id="firstname" onblur="validateName()" /></td>
							</tr>
							<tr>
								<td><form:label path="surname">Surname *</form:label></td>
								<td><form:input path="surname" name="sname"
										id="surname" onblur="validateName()" /></td>
							</tr>

							<tr>
								<td colspan="2"><span id="nameValidationMsg"
									class="validationMsg"></span>
							</tr>

							<tr>
								<td><form:label path="email">Email Address *</form:label></td>
								<td><form:input path="email" id="registerEmail"
										onblur="validateEmail()" /></td>
							</tr>

							<tr>
								<td colspan="2"><span id="emailValidationMsg"
									class="validationMsg"></span>
							</tr>

							<tr>
								<td><form:label path="number">Phone Number (Mobile Preferred)</form:label></td>
								<td><form:input path="number" id="number" maxlength="10"
										onblur="validateNumber()" /></td>
							</tr>

							<tr>
								<td colspan="2"><span id="numberValidationMsg"
									class="validationMsg"></span></td>
							</tr>

							<tr>
								<td><form:label path="password">Password *</form:label></td>
								<td><form:password path="password" id="registerPassword"
										onblur="validatePassword()" /></td>
							</tr>

							<tr>
								<td colspan="2"><span id="passwordValidationMsg"
									class="validationMsg"></span></td>
							</tr>

							<tr>
								<td><form:label path="">Confirm Password *</form:label></td>
								<td><form:password path="" id="confirmPassword"
										onblur="validateConfirmPassword()" /></td>
							</tr>

							<tr>
								<td colspan="2"><span id="pwdValidationMsg"
									class="validationMsg"></span></td>
							</tr>

							<tr>
								<td colspan="2"><form:checkbox path="" id="checkBox"
										value="selected" onchange="checkBoxValidation()" />I have read and accept Pillow Talk's Privacy Policy *
									</td>
							</tr>

							<tr>
								<td colspan="2"><center>
										<input type="submit" id="registerButton1" style="background-color: #ff3b57;border: 1px solid #C3B3B3;padding: 7px 10px;"
										value="REGISTER" />
									</center></td>
							</tr>

						</form:form>
					</table>

				</td>

				<td style="width: 50%"><form:form id="loginForm" method="post"
						action="login">
						<table cellpadding="5">
							<tr>
								<td><form:label path="email">Email Address</form:label></td>
								<td><form:input id="email" name="email" path="email" /></td>
							</tr>
							<tr>
								<td><form:label path="password">Password </form:label></td>
								<td><form:password id="password" name="password"
										path="password" /></td>
							</tr>
							<tr>
								<td colspan="2"><center>
										<input type="submit" value="LOGIN"
										style="background-color: #ff3b57;border: 1px solid #C3B3B3;padding: 7px 10px;" />
									</center></td>
							</tr>

						</table>
					</form:form></td>
			</tr>
		</table>
	</div>
</body>
</html>