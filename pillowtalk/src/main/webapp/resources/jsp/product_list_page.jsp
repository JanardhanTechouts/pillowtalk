<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script>
	$(document).ready(function() {

		$('.cartbtn').on("click", function() {
			var price_val = $(this).val();
			var product_id = $(this).attr("id");

			$.ajax({
				type : 'post',
				url : 'addToCart',
				data : {
					price : price_val,
					id : product_id,
				},
				success : function(response) {
					var data = response.split(",");
					$('#total').text("Rs" + data[0]);
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

	<ul class="products">
		<c:forEach var="item" items="${products}">
		
			<li><a href="p?id=${item.product_id}"> <img
					src="getImage?id=${item.product_id}"
					style="width: 120px; height: 150px;" /><br>
					${item.product_name}<br>Rs ${item.price}<br>



				<button class="cartbtn" id="${item.product_id}"
					value="${item.price}">ADD TO BAG</button> 
				
				

		</a></li>
		</c:forEach>
	</ul>

</body>
</html>