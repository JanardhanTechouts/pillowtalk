<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<style type="text/css">
#slider {
	position: relative;
	padding-top: 30px;
}

.mySlides {
	display: none;
}

#slider_leftclick {
	position: absolute;
	top: 50%;
	left: 10px;
}

#slider_rightclick {
	position: absolute;
	top: 50%;
	right: 10px;
}

.slidebtn {
	background-color: orange;
	padding: 2px;
	font-size: 20px;
	cursor: pointer
}

.home_banners {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	padding-top: 30px;
	padding-bottom: 30px;
}

.home_banners li {
	display: inline;
}
</style>

</head>
<body>

	<div id="slider">
		<a href="logi0"> <img class="mySlides"
			src="<c:url value="resources/images/slide1.jpg" />"
			style="width: 100%"></a> <a href="logi1"> <img class="mySlides"
			src="<c:url value="resources/images/slide2.jpg" />"
			style="width: 100%"></a> <a href="logi2"> <img class="mySlides"
			src="<c:url value="resources/images/slide3.jpg" />"
			style="width: 100%"></a>


		<div id="slider_leftclick">
			<input type="button" value="&lt;" class="slidebtn"
				onclick="plusDivs(-1)" />
		</div>

		<div id="slider_rightclick">
			<input type="button" value="&gt;" class="slidebtn"
				onclick="plusDivs(1)" />
		</div>
	</div>

	<script>
		var myIndex = 0;
		var slideIndex = 1;
		carousel();

		function carousel() {
			var i;
			var x = document.getElementsByClassName("mySlides");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			myIndex++;
			if (myIndex > x.length) {
				myIndex = 1
			}
			x[myIndex - 1].style.display = "block";
			setTimeout(carousel, 5000);
		}

		function plusDivs(n) {
			showDivs(slideIndex += n);
		}

		function showDivs(n) {
			var i;
			var x = document.getElementsByClassName("mySlides");
			if (n > x.length) {
				slideIndex = 1
			}
			if (n < 1) {
				slideIndex = x.length
			}
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			x[slideIndex - 1].style.display = "block";
		}
	</script>

	<br>
	<br>






</body>
</html>