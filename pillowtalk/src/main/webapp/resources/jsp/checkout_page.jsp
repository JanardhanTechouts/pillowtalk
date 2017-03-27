<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.largebtn {
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
</head>
<body>

<h2>Billing Address</h2>

<form action="placeorder_page">
<table border="0" cellpadding="5" cellspacing="5">

<tr>
<td>Full Name :</td>
<td><input type="text" name="fname"></td>
</tr>
<tr>
<td>Mobile :</td>
<td><input type="text" name="mobile"></td>
</tr>
<tr>
<td>Email :</td>
<td><input type="text" name="email"></td>
</tr>
<tr>
<td>Address Line1 :</td>
<td><input type="text" name="line1"></td>
</tr>
<tr>
<td>Address Line2 :</td>
<td><input type="text" name="line2"></td>
</tr>
<tr>
<td>City :</td>
<td><input type="text" name="city"></td>
</tr>
<tr>
<td>State :</td>
<td><input type="text" name="state"></td>
</tr>
<tr>
<td>ZIP :</td>
<td><input type="text" name="zip"></td>
</tr>
<tr>
<td>Country :</td>
<td><input type="text" name="country"></td>
</tr>
<tr>

<!-- <td colspan="2"><input type="submit" value="confirm" style="background-color:#ff3b57"></td> -->

</tr>
</table>


     <h2>Shipping Address</h2>
<table border="0" cellpadding="5" cellspacing="5">
<tr>
<td>Full Name</td>
<td><input type="text"></td>
</tr>
<tr>
<td>Mobile :</td>
<td><input type="text" name="mobile"></td>
</tr>
<tr>
<td>Email :</td>
<td><input type="text" name="email"></td>
</tr>
<tr>
<td>Address Line1</td>
<td><input type="text"></td>
</tr>
<tr>
<td>Address Line2</td>
<td><input type="text"></td>
</tr>
<tr>
<td>City</td>
<td><input type="text"></td>
</tr>
<tr>
<td>State</td>
<td><input type="text"></td>
</tr>
<tr>
<td>ZIP</td>
<td><input type="text"></td>
</tr>
<tr>
<td>Country</td>
<td><input type="text"></td>
</tr>
<tr>

<!-- <td colspan="2"><input type="submit" value="save" style="background-color:#ff3b57"></td> -->
</tr>
</table>                         
       <div class="checkout-panel">
  <div class="panel-body">
    <h2 class="title">Payment</h2>
 
    <div class="progress-bar">
      <div class="step active"></div>
      <div class="step active"></div>
      <div class="step"></div>
      <div class="step"></div>
    </div>
 
    <div class="payment-method">
      <label for="card" class="method card">
        <div class="card-logos">
          <img src="resources/images/visa.png"/>
          <img src="resources/images/mastercard.png"/>
        </div>
 
        <div class="radio-input">
          <input id="card" type="radio" name="payment">
          Pay  with credit card
        </div>
      </label>
 
      <label for="paypal" class="method paypal">
        <img src="resources/images/paypal.png"/>
        <div class="radio-input">
          <input id="paypal" type="radio" name="payment">
          Pay  with PayPal
        </div>
      </label>
    </div>
 
    <div class="input-fields">
      <div class="column-1">
        <label for="cardholder">Cardholder's Name</label>
        <input type="text" id="cardholder" />
 
        <div class="small-inputs">
          <div>
            <label for="date">Valid thru &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <input type="text" id="date"/>
          </div>
 
          <div>
            <label for="verification">CVV / CVC *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <input type="password" id="verification"/>
          </div>
        </div>
 
      </div>
      <div class="column-2">
        <label for="cardnumber">Card Number&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <input type="password" id="cardnumber"/>
 
        <span class="info">* CVV or CVC is the card security code, unique three digits number on the back of your card separate from its number.</span>
      </div>
    </div>
  </div>
 
<!--   <div class="panel-footer"> -->
    
<!--     <button class="btn next-btn">proceed</button> -->
<!--   </div> -->
</div> 

<!-- <a href="placeorder_page"><button -->
<!-- 				class="largebtn">place order</button></a> -->
<input type="submit" value="place order" class="largebtn">
</form>
</body>
</html>

 
