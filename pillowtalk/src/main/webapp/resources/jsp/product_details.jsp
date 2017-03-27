<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
.cartbtn {
	background-color: #ff3b57;
	border: none;
	color: white;
	padding: 7px 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
}
</style>

<script>
	$(document).ready(function() {

		$('.cartbtn').on("click", function() {
			// 			alert("add to bag");
			var price_val = $(this).val();
			var product_id = $(this).attr("id");
			var quant = document.getElementById("quantityId").value;

			//var quant = $(this).parent().prev().val();
// 			alert(" test"+price_val + " " + product_id + " " + quant);
			$.ajax({

				// 				type : 'post',
				url : 'addToCart',
				data : {
					price : price_val,
					id : product_id,
					quantity : quant,
				},
				success : function(response) {
					var data = response.split(",");
					$('#total').text(data[0]);
// 								alert(data[0]+" "+data[1]);
					if (data[1] == "1") {

						$('#item_count').text("(" + data[1] + " item)");
					} else {

						$('#item_count').text("(" + data[1] + " items)");
					}

				}
			});
		});
	});
</script>

</head>
<body>
	<img src="getImage?id=${product.product_id}"
		style="width: 350px; height: 230px; float: left; border-right: 0px solid #000;" />
	<br>
	<!-- <img id="zoom_01" src="small/image1.png" data-zoom-image="large/image1.jpg"/> -->
	<p style="float: center; padding-left: 500px">
		Product Name: ${product.product_name}<br> <br> Price :
		${product.price}<br> <br> Quantity : <select id="quantityId"
			class="quantity" name="${product.quantity}">
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
			<br>
			<br>
		</select> <br> <br> <span></span>
		<button class="cartbtn" id="${product.product_id}"
			name="${product.product_id}" value="${product.price}">ADD TO
			BAG</button>
		</span> </span> <br> <br> <br> <br> <br> <br>
</body>
</html>