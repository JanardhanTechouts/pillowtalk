<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
table {
	width: 100%;
}

table th {
	text-align: left;
	color: #000;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
}

table td {
	font-size: 15px;
}

.updatebtn {
	background-color: #fff;
	border: 1px solid #C3B3B3;
	color: green;
	/* 	text-transform: uppercase; */
	padding: 4px 6px;
	cursor: pointer;
}

.removebtn {
	background-color: #fff;
	border: 1px solid #C3B3B3;
	color: green;
	/* 	text-transform: uppercase; */
	padding: 4px 6px;
	cursor: pointer;
}

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

<script>
	var quantitySelected;

	$(document)
			.ready(
					function() {

						$("td.changeData select.quantity option").each(
								function() {
									if ($(this).val() == $(this).parent().attr(
											"name")) {
										$(this).attr("selected", "selected");
									}
								});

						$('.removebtn')
								.on(
										"click",
										function() {

											var product_id = $(this).attr(
													"name");

											$
													.ajax({
														// 														type : 'post',
														url : 'removeFromCart',
														context : this,
														data : {
															id : product_id,
														},
														success : function(
																response) {
															var data = response
																	.split(",");

															if (data[0] == "true") {
																$(this)
																		.parents(
																				'tr')
																		.next()
																		.remove();
																$(this)
																		.parents(
																				'tr')
																		.remove();

																$("#total")
																		.load(
																				location.href
																						+ " #total");
																$("#item_count")
																		.load(
																				location.href
																						+ " #item_count");
																$("#subtotal")
																		.load(
																				location.href
																						+ " #subtotal");
																$("#delivery")
																		.load(
																				location.href
																						+ " #delivery");
																$("#grandtotal")
																		.load(
																				location.href
																						+ " #grandtotal");

															} else {

																alert("ERROR");
															}

															if (data[1] == "empty") {

																$(
																		'#replaceCart')
																		.html(
																				'<p><a>Sign in</a> to save items in your account, or to view items already in your account.<p>Your cart is currently empty.</p><br><br><a href="home" class="grayBtn">START SHOPPING</a>');
															}

														}
													});
										});

						$('select.quantity').on("change", function() {
							quantitySelected = $(this).val();
						});

						$('.updatebtn')
								.on(
										"click",
										function() {

											var product_id = $(this).attr(
													"name");
											// 											alert(product_id); 
											var quant = $(this).parent().prev()
													.val();
											/* alert(quant); */

											$
													.ajax({
														// 														type : 'post',
														url : 'updateCart',
														context : this,
														data : {
															id : product_id,
															quantity : quant,
														// 															alert("update:"+id+" "+quant);
														},
														success : function(
																response) {
															var data = response
																	.split(",");

															if (data[0] == "true") {
																$(this)
																		.parents(
																				'td')
																		.next(
																				'td')
																		.next()
																		.text(
																				data[1])

																$("#total")
																		.load(
																				location.href
																						+ " #total");
																$("#item_count")
																		.load(
																				location.href
																						+ " #item_count");
																$("#subtotal")
																		.load(
																				location.href
																						+ " #subtotal");
																$("#delivery")
																		.load(
																				location.href
																						+ " #delivery");
																$("#grandtotal")
																		.load(
																				location.href
																						+ " #grandtotal");

															} else {

																$('#item_count')
																		.text(
																				"("
																						+ data[1]
																						+ " items)");
															}

														}
													});
										});

					});
</script>

</head>
<body>

	<h1 class="h1Heading">Your Shopping Bag</h1>
	<div id="replaceCart">
		<table id="cartTable">
			<tr>
				<th>ITEM</th>
				<th>QUANTITY</th>
				<th>ITEM PRICE</th>
				<th>TOTAL</th>
			</tr>
			<tr>
				<td colspan=4><hr></td>
			</tr>
			<c:forEach var="item" items="${cart_products}">
				<tr>
					<td><img src="getImage?id=${item.product_id}"
						style="width: 60px; height: 80px;" />${item.product_name}</td>

					<td class="changeData"><select class="quantity"
						name="${item.quantity}">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
					</select> <a><button class="updatebtn" name="${item.product_id}">Update</button></a>
						<a><button class="removebtn" name="${item.product_id}">x
								Remove</button></a></td>

					<td>Rs&nbsp;${item.price }</td>

					<td class="changeTotal">Rs&nbsp;${item.price * item.quantity }</td>

				</tr>
				<tr>
					<td colspan=4><hr></td>
				</tr>
			</c:forEach>
		</table>
		<span style="position: absolute; right: 27.5%;">Sub-total :<span
			id="subtotal" style="position: absolute; left: 355%;">Rs&nbsp;${total}</span></span>
		<br> <span style="position: absolute; right: 27.5%;">Delivery
			:<span id="delivery" style="position: absolute; left: 410%;">
				<c:choose>
					<c:when test="${total < 1000 }">Rs50</c:when>
					<c:otherwise>Rs&nbsp;0.0</c:otherwise>
				</c:choose>
		</span>
		</span> <br>
		<hr>
		<br> <span
			style="position: absolute; right: 27.5%; font-size: 22px;">Total
			:<span id="grandtotal" style="position: absolute; left: 400%;">
				<c:choose>
					<c:when test="${total < 1000 }">Rs&nbsp;${total+50}</c:when>
					<c:otherwise>Rs&nbsp;${total}</c:otherwise>
				</c:choose>
		</span>
		</span> <br> <br> <a href="checkout"><button class="largebtn">checkout</button></a>
	</div>
</body>

</html>