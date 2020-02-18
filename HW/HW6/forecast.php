<?php
	$street = $city = $state = $lat = $lon = "";
	$json = $detail_json = null;
	$time = "";

	if ($_SERVER["REQUEST_METHOD"] == "POST") {
		$street = $_POST["street"];
		$city = $_POST["city"];
		$state = $_POST["state"];
		$lat = $_POST['lat'];
		$lon = $_POST['lon'];
		$time = $_POST['time'];

		if (isset($_POST["street"]) && isset($_POST["city"]) && isset($_POST["state"])) {
			$json = getPosition($street, $city, $state);
		}
		else if (!isset($_POST["time"])){
			$json = getWeatherInfo($lat, $lon);
		}
		else 
			$json = getWeatherDetail($lat,$lon,$time);
		exit(json_encode($json));
	}


	function getPosition($strt, $cty, $stt){
		$geo_url = "https://maps.googleapis.com/maps/api/geocode/xml?address=".urlencode($strt).",".urlencode($cty).",".urlencode($stt)."&key=your_google_api";
		$xmlDoc = new DOMDocument();
		$xml = file_get_contents($geo_url);
		$xmlDoc = simplexml_load_string($xml);
		$lat = $xmlDoc->result->geometry->location->lat;
		$lon = $xmlDoc->result->geometry->location->lng;
		return getWeatherInfo($lat, $lon);
	}

	function getWeatherInfo($lat, $lon){
		$wea_url = "https://api.darksky.net/forecast/your_darkskey_api/".urlencode($lat).",".urlencode($lon);
		$json = json_decode(file_get_contents($wea_url));
		return $json;
	}

	function getWeatherDetail($lat, $lon, $time){
		$dtl_url = "https://api.darksky.net/forecast/your_darkskey_api/".urlencode($lat).",".urlencode($lon).",".urlencode($time)."?exclude=minutely";
		$json = json_decode(file_get_contents($dtl_url));
		return $json;
	}
?>


<!DOCTYPE html>
<html>
	<head>
		<title>Weather Search</title>
		<meta charset="utf-8">
		<meta name = "referrer" content="no-referrer">
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		<style type="text/css">

			#text_title{
   				color: white;
   				font-family: serif;
   				font-style: italic;
   				font-weight: 400;
   				font-size: 48px;
   				padding-top: 10px;
   				/*font-family: ;*/
  			}

  			#box{
  				text-align: center;
				background-color: #32AC39;
				border-radius: 10px;
				width: 830px;
				height: 250px;
				margin: 0 auto;
				position: relative;
  			}

  			.input_form{
  				margin-top: 10px;
  			}

  			.text_type{
  				color: white;
  				font-size: 20px;
  				font-family: serif;
  				font-weight: 800;
  			}

  			.input_type1{
  				width: 130px;
  				margin-left: 5px;
  			}

  			input[disabled] {
  				background-color: #CDF3CF;
  				opacity:1;
  			}

  			.input_type2{
  				width: 130px;
  				margin-left: 18.5px;
  			}

  			#form1{
  				position: relative;
  				margin-left: 60px;
  				text-align: left;
  				margin-top: -30px;
  				width: 350px;
  				height: 100px;
  			}

  			.select_type{
  				width: 250px;
  			}

  			.select_form{
  				margin-top: 10px;
  				color:#111;
  			}

  			#straight_line{
  				position: relative;
  				width: 5px;
  				height: 125px;
  				background-color: white;
  				border-radius: 5px;
  				margin-left: 470px;
  				margin-top: -100px;
  			}

  			#form2{
  				position: relative;
  				margin-left: 550px;
  				margin-top: -120px;
  				width: 300px;
  				height: 140px;
  				color: white;
  				font-weight: 600;
  				font-size: 20px;
  			}

  			#check_box{
  				color: white;
  				font-size: 20px;
  				font-family: serif;
  				font-weight: 800;
  			}

  			#search_clear{
  				margin-left: -100px;
  			}

  			#alert{
  				text-align: center;
  				box-sizing: border-box;
  				border: 1px solid black;
  				width: 400px;
  				margin: 30px auto;
  				align-self: center;
  			}
  			#some_details img{
  				width: 20px;
  				height: 20px;
  			}

  			#forecast_weather{
				margin: 30px auto;
				text-align: left;
				width: 500px;
				height: 320px;
				background-color: #60C3F4;
				border-radius: 10px;
				color: white;
			}
			#city_name {
				padding-top: 25px;
				margin-left: 20px;
				font-size: 32px;
				font-weight: 700;
			}

			#time_zone {
				margin-left: 20px;
				font-size: 14px;
			}

			#temperature {
				font-size: 100px;
				margin-left: 20px;
				font-weight: 700;
				padding-top: 5px;
			}
			#F-sign0 {
				font-size: 40px;
				padding-left: 20px;
			}

			#t_icon0 {
				margin-top: -110px;
			}

			#t_icon0 img{
				width: 15px;
				height: 15px;
				margin-left: 240px;
			}

			#summary {
				margin-top: 70px;
				margin-left: 20px;
				font-size: 38px;
				font-weight: 600;
				font-family: serif;
			}

			#some_details {
				padding-left: 10px;
				padding-right: 10px;
			}

			#some_details table {
				width: 480px;
				text-align: center;
				font-size: 26px;
				font-weight: 800;
			}

			.contexts {
				margin-top: -10px;
			}

			#some_details img{
				width: 30px;
				height: 30px;
			}

			#daily_table {
				background-color: #A0C8EE;
				border: 2px solid #58A1CA;
				border-collapse: collapse;
				text-align: center;
				font-family: serif;
				width: 900px;
				font-weight: 900;
				margin: 20px auto;
				color: white;
			}

			#daily_table tr th, #daily_table tr td{
				border: 2px solid #58A1CA;
			}

			#daily_table a{
				color: white;
				text-decoration: none;
			}
			#DWD {
				text-align: center;
				margin-top: 25px;
				font-size: 38px;
				font-weight: 600;

			}
			#detail_form {
				margin: 25px auto;
				text-align: left;
				width: 550px;
				height: 500px;
				background-color: #A8D0D9;
				border-radius: 10px;
				color:white;
			}
			#detail_summary{
				padding-top: 80px;
				margin-left: 40px;
				font-size: 34px;
				font-family: serif;
				font-weight: 700;
			}
			#detail_temperature {
				margin-left: 40px;
				font-size: 120px;
				font-weight: 700;
				margin-top: 15px;
			}
			#F-sign1 {
				margin-left: 20px;
				font-size: 85px;
			}
			#t_icon1 {
				margin-top: -130px;
				margin-left: 160px;
			}
			#t_icon1 img{
				width: 15px;
				height: 15px;
			}
			#detail_weather_pic img{
				width: 280px;
				height: 280px;
				margin-left: 250px;
				margin-top: -190px;
			}
			.detail_info {
				font-size: 24px;
				font-weight: 600;
				margin-top: -30px;
			}
			.detail_info p{
				height: 10px;
			}
			#precip{
				margin-left: 240px;
			}
			#rainChance {
				margin-left: 205px;
			}
			#wdspd {
				margin-left: 240px;
			}
			#humidity {
				margin-left: 266px;
			}
			#visibility {
				margin-left: 273px;
			}
			#rise_set {
				margin-left: 200px;
			}
			#DHW {
				text-align: center;
				margin-top: 40px;
				font-size: 38px;
				font-weight: 600;
				color: black;
			}
			#arrow_img{
				width: 50px;
				height: 50px;
				margin: 0 50%;
			}
			#chart_div{
				margin: 0 auto;
				text-align: left;
				display: none;
				width: 800px;
			}
		</style>
	</head>

	<body>
		<div id = "box">
			<form id = "search_form" method = "post" onsubmit = "return false" action = "<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
			<h1 id = "text_title">Weather Search</h1>
			<div id = "form1">
				<div class = "input_form">
					<span class = "text_type">Street</span>
					<input class = "input_type1" id = "street" type = "text" name = "street" required="required" value = "">
				</div>
				<div class = "input_form">
					<span class ="text_type">City</span>
					<input class = "input_type2" id = "city" type = "text" name = "city" required="required" value = "">
				</div>
				<div class = "select_form">
					<span class = "text_type">State</span>
					<select class = "select_type" name = "state" id = "state">
						<option value="default" selected="selected">State</option>
						<option disabled="disabled">------------------------------------</option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
						<option value="CO">Colorado</option>
						<option value="CT">Connecticut</option>
						<option value="DE">Delaware</option>
						<option value="DC">District Of Columbia</option>
						<option value="FL">Florida</option>
						<option value="GA">Georgia</option>
						<option value="HI">Hawaii</option>
						<option value="ID">Idaho</option>
						<option value="IL">Illinois</option>
						<option value="IN">Indiana</option>
						<option value="IA">Iowa</option>
						<option value="KS">Kansas</option>
						<option value="KY">Kentucky</option>
						<option value="LA">Louisiana</option>
						<option value="ME">Maine</option>
						<option value="MD">Maryland</option>
						<option value="MA">Massachusetts</option>
						<option value="MI">Michigan</option>
						<option value="MN">Minnesota</option>
						<option value="MS">Mississippi</option>
						<option value="MO">Missouri</option>
						<option value="MT">Montana</option>
						<option value="NE">Nebraska</option>
						<option value="NV">Nevada</option>
						<option value="NH">New Hampshire</option>
						<option value="NJ">New Jersey</option>
						<option value="NM">New Mexico</option>
						<option value="NY">New York</option>
						<option value="NC">North Carolina</option>
						<option value="NC">North Dakota</option>
						<option value="OH">Ohio</option>
						<option value="OK">Oklahoma</option>
						<option value="OR">Oregon</option>
						<option value="PA">Pennsylvania</option>
						<option value="RI">Rhode Island</option>
						<option value="SC">South Carolina</option>
						<option value="SD">South Dakota</option>
						<option value="TN">Tennessee</option>
						<option value="TX">Texas</option>
						<option value="UT">Utah</option>
						<option value="VT">Vermont</option>
						<option value="VA">Virginia</option>
						<option value="WA">Washington</option>
						<option value="WV">West Virginia</option>
						<option value="WI">Wisconsin</option>
						<option value="WY">Wyoming</option>
					</select>
				</div>
			</div>
			<div id = "straight_line"></div>
			<div id = "form2">
				<div>
					<input type = "checkbox" id = "check_box"  value= "" onchange = "getCurrentLocation()">Current Location
					<input type = "hidden" id = "lat" name = "lat">
					<input type = "hidden" id = "lon" name = "lon">
				</div>
			</div>
			<div id = "search_clear">
				<input id = "search" name = "search" type = "submit" value = "search" onclick = "searchForm()">
				<input id = "clear" name = "clear" type = "button" value = "clear" onclick = "clearPage()">
			</div>
		</div>
		<div id = "alerttext"></div>
		<div id = "results"></div>
	</body>
	<script type="text/javascript">

		var geojson = null;
		var search = document.getElementById("search");
		var form = document.getElementById("search_form");
		var input_street = document.getElementById("street");
		var input_city = document.getElementById("city");
		var select_state = document.getElementById("state");
		var lat = document.getElementById("lat");
		var lng = document.getElementById("lon");
		var loc_city;
		var xhttp = new XMLHttpRequest();
		var results;
		var details;
		var data_T = new Array();
		xhttp.open("GET", "http://ip-api.com/json", false);
		xhttp.send();
		if (xhttp.readyState == 4 && xhttp.status == 200){
			geojson = JSON.parse(xhttp.responseText);
			//console.log(geojson);
			//console.log(lat);
			//console.log(lon);
		}

		function getCurrentLocation(){
			var current_checked = document.getElementById("check_box").checked;
			//console.log(current_checked);
			if (current_checked){
				input_street.value = "";
				input_city.value = "";
				select_state.options[0].selected = true;
				input_street.disabled = true;
				input_city.disabled = true;
				select_state.disabled = true;
				lat = geojson["lat"];
				lon = geojson["lon"];
			}
			else {
				input_street.disabled = false;
				input_city.disabled = false;
				select_state.disabled = false;
				document.getElementById("lat").value = "";
				document.getElementById("lon").value = "";
			}
		}

		function searchForm(){
			var ifCurrent = false;
			var current_checked = document.getElementById("check_box").checked;
			//console.log(current_checked);
			if (current_checked){
				ifCurrent = true;
				//submitForm(ifCurrent);
				//return;
			}
			if (ifCurrent == false)
			{
				if (input_street.value == "" || input_city.value == "" || select_state.selectedIndex == 0)
					document.getElementById("alerttext").innerHTML = "<div id = 'alert'>Please check the input address.</div>";
			}
			//else submitForm(ifCurrent);
		}

		var clear = document.getElementById("clear");
		function clearPage(){
			form.reset();
			input_street.disabled = false;
			input_city.disabled = false;
			select_state.disabled = false;
			document.getElementById("alerttext").innerHTML = "";
			var results_write = document.getElementById("results");
			while(results_write && results_write.firstChild){
				results_write.removeChild(results_write.firstChild);
			}
		}

		form.addEventListener("submit", function(event) {
			//var ifvalid = false;
			event.preventDefault();
			var current_checked = document.getElementById("check_box").checked;
			//console.log(current_checked);
			if (current_checked){
				//ifvalid = true;
				document.getElementById("lat").value = lat;
				document.getElementById("lon").value = lon;
				loc_city = geojson["city"];
				//submitForm(ifCurrent);
				//return;
			}
			else{
				loc_city = input_city.value;
				document.getElementById("lat").value = "";
				document.getElementById("lon").value = "";
			}
			//console.log(loc_city);
			var url = form.action;
			var params = "";
			var data = new FormData(form);
			for (entry of data) {
				params += entry[0] + "=" + encodeURIComponent(entry[1]) + "&";
			}
			params = params.slice(0, -1);
			console.log(params);
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.open("POST", url, false);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlhttp.send(params);
			console.log(xmlhttp.responseText);
			results = JSON.parse(xmlhttp.responseText);
			console.log(results);
			lat = results["latitude"];
			lon = results["longitude"];
			showResults(results);
		}, false);

		function showResults(results){
			if (results == null) return;
			document.getElementById("alerttext").innerHTML = "";
			var results_write = document.getElementById("results");
			while(results_write && results_write.firstChild){
				results_write.removeChild(results_write.firstChild);
			}
			var html_text = "";
			html_text += "<div id = 'forecast_weather' >";
			html_text += "<div id = 'city_name'>"+ loc_city +"</div>";
			html_text += "<div id = 'time_zone'>"+ results["timezone"] +"</div>";
			html_text += "<div id = 'temperature'>"+ results["currently"]["temperature"] + "<span id = 'F-sign0'>F</span></div>";
			html_text += "<div id = 't_icon0'><img src = 'https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png'></div>";
			html_text += "<div id = 'summary'>" + results["currently"]["summary"] + "</div>";

			html_text += "<div id = 'some_details'><table>";

		if (results["currently"]["humidity"] != 0)
			html_text += "<tr><td><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png' title='Humidity'></td>";
		if (results["currently"]["pressure"] != 0)
			html_text += "<td><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png' title='Pressure'></td>";
		if (results["currently"]["windSpeed"] != 0)
			html_text += "<td><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png' title='WindSpeed'></td>";
		if (results["currently"]["visibility"] != 0)
			html_text += "<td><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png' title='Visibility'></td>";
		if (results["currently"]["cloudCover"] != 0)
			html_text += "<td><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png' title='CloudCover'></td>";
		if (results["currently"]["ozone"] != 0)
			html_text += "<td><img src = 'https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png' title='Ozone'></td></tr>";

		if (results["currently"]["humidity"] != 0)
			html_text += "<tr><td><div class = 'contexts'>"+results["currently"]["humidity"] +"</div></td>";
		if (results["currently"]["pressure"] != 0)
			html_text += "<td><div class = 'contexts'>"+results["currently"]["pressure"] +"</div></td>";
		if (results["currently"]["windSpeed"] != 0)
			html_text += "<td><div class = 'contexts'>"+results["currently"]["windSpeed"] +"</div></td>";
		if (results["currently"]["visibility"] != 0)
			html_text += "<td><div class = 'contexts'>"+results["currently"]["visibility"] +"</div></td>";
		if (results["currently"]["cloudCover"] != 0)
			html_text += "<td><div class = 'contexts'>"+results["currently"]["cloudCover"] +"</div></td>";
		if (results["currently"]["ozone"] != 0)
			html_text += "<td><div class = 'contexts'>"+results["currently"]["ozone"] +"</div></td></tr>";
			html_text += "</table></div></div>";

			var day_details = results["daily"]["data"];
			//console.log(day_details);
			html_text += "<div><table border = '1' id = 'daily_table'> <tr>";
			html_text += "<th>Date</th> <th>Status</th> <th>Summary</th> <th>TemperatureHigh</th> <th>TemperatureLow</th> <th>Wind Speed</th><tr>";

			for (var that_day of day_details){
				console.log(that_day);
				var stts = that_day["icon"];
				var smmry = that_day["summary"];
				var thigh = that_day["temperatureHigh"];
				var tlow = that_day["temperatureLow"];
				var wdspd = that_day["windSpeed"];
				var raw_time = that_day["time"];
				var std_time = toHour(raw_time);
				var adjst_time = timeAdjust(std_time);
				var time = convertTime(raw_time * 1000, adjst_time);
				console.log(raw_time);
				//console.log(time);

				html_text += "<td>"+ time +"</td>";
				switch(stts){
					case "clear-day": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png' width = '40px' height = '40px'></td>";break;
					case "clear-night": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png' width = '40px' height = '40px'></td>";break;
					case "rain": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-04-512.png' width = '40px' height = '40px'></td>";break;
					case "snow": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-19-512.png' width = '40px' height = '40px'></td>";break;
					case "sleet": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-07-512.png' width = '40px' height = '40px'></td>";break;
					case "wind": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png' width = '40px' height = '40px'></td>";break;
					case "fog": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png' width = '40px' height = '40px'></td>";break;
					case "cloudy": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-01-512.png' width = '40px' height = '40px'></td>";break;
					case "partly-cloudy-day": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png' width = '40px' height = '40px'></td>";break;
					case "partly-cloudy-night": html_text += "<td><img src ='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png' width = '40px' height = '40px'></td>";break;
				}
				html_text += "<td>"+"<a href='#' onclick='javascript:postDetails("+ lat +","+ lon +","+raw_time + ")'>"+smmry+"</td>";
				html_text += "<td>"+thigh+"</td>";
				html_text += "<td>"+tlow+"</td>";
				html_text += "<td>"+wdspd+"</td>";
				html_text += "</tr>";
			}
			html_text += "</table>"
			html_text += "</div>";
			document.getElementById("results").innerHTML = html_text;
		}

		function convertTime(time10, adjst_time){
			var date_time = new Date();
			date_time.setTime(time10);
			var year = date_time.getFullYear();
			var month = date_time.getMonth() + 1;
			var date = date_time.getDate();
			if (adjst_time > 0){
				date += 1;
			}
			month = strd(month);
			date = strd(date);
			return year + "-" + month + "-" + date;
		}

		function strd(num){
			if (num < 10) return "0"+""+num;
			return num;
		}

		function postDetails(lat, lon, time){
			var url = form.action;
			var params = "";
			//console.log(time);
			params += "lat=" + encodeURIComponent(lat) + "&";
			params += "lon=" + encodeURIComponent(lon) + "&";
			params += "time=" + time;
			//params = params.slice(0, -1);
			console.log(params);
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.open("POST", url, false);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlhttp.send(params);
			details = JSON.parse(xmlhttp.responseText);
			console.log(details);
			showDetails(details);
		}

		function showDetails(details){
			if (details == null) return;
			var results_write = document.getElementById("results");
			while(results_write && results_write.firstChild){
				results_write.removeChild(results_write.firstChild);
			}
			var smmry = details["currently"]["summary"];
			var temperature = details["currently"]["temperature"];
			temperature = Math.floor(temperature);
			var wdspd = details["currently"]["windSpeed"];
			var humidity = details["currently"]["humidity"] * 100;
			humidity = Math.ceil(humidity);
			var visibility = details["currently"]["visibility"];
			var stts = details["currently"]["icon"];
			var precip = "";
			var rainChance = details["currently"]["precipProbability"] * 100;

			if (details["currently"]["precipIntensity"] <= 0.001)
				precip = "None";
			else if (details["currently"]["precipIntensity"] <= 0.015)
				precip = "Very Light";
			else if (details["currently"]["precipIntensity"] <= 0.05)
				precip = "Light";
			else if (details["currently"]["precipIntensity"] <= 0.1)
				precip = "Moderate";
			else precip = "heavy";

			var std_time = details["currently"]["time"];
			std_time = toHour(std_time);
			var adjst_time = timeAdjust(std_time);
			console.log("当地时间" + std_time);
			console.log("修正时间" + adjst_time);

			var rise_time = details["daily"]["data"][0]["sunriseTime"];
			var set_time = details["daily"]["data"][0]["sunsetTime"];
			rise_time = toHour(rise_time) + adjst_time;
			set_time = toHour(set_time) + adjst_time;
			console.log(rise_time);
			console.log(set_time);
			var rise_M, set_M;
			if (rise_time >= 12){
				rise_M = "PM";
			}
			else rise_M = "AM";
			rise_time += 12;
			rise_time %= 12;
			if (set_time >= 12){
				set_M = "PM";
			}
			else set_M = "AM";
			set_time += 12;
			set_time %= 12;


			var html_text = "";
			html_text += "<div id = 'DWD'>Daily Weather Detail</div>";
			html_text += "<div id = 'detail_form'>";
			html_text += "<div id = 'detail_summary'>"+ smmry + "</div>";
			html_text += "<div id = 'detail_temperature'>"+ temperature + "<span id = 'F-sign1'>F</span></div>";
			html_text += "<div id = 't_icon1'><img src ='https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png'></div>"

			switch(stts){
				case "clear-day": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png'>";break;
				case "clear-night": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png'>";break;
				case "rain": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png'>";break;
				case "snow": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png'>";break;
				case "sleet": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png'>";break;
				case "wind": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png'>";break;
				case "fog": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png'>";break;
				case "cloudy": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png'>";break;
				case "partly-cloudy-day": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png'>";break;
				case "partly-cloudy-night": html_text += "<div id = 'detail_weather_pic'><img src ='https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png'>";break;
			}
			html_text += "<div class = 'detail_info'>";
			html_text += "<p id = 'precip'>Precipitsion: <span>"+ precip + "</span></p>";
			html_text += "<p id = 'rainChance'>Chance of Rain: <span>"+ rainChance + "</span><span class = 'units'> %</span></p>";
			html_text += "<p id = 'wdspd'>Wind Speed: <span>"+ wdspd + "</span><span class = 'units'> mph</span></p>";
			html_text += "<p id = 'humidity'>Humidity: <span>"+ humidity + "</span><span class = 'units'> %</span></p>";
			html_text += "<p id = 'visibility'>Visibility: <span>"+ visibility + "</span><span class = 'units'> mi</span></p>";
			html_text += "<p id = 'rise_set'>Sunrise / Sunset: <span>"+ rise_time + " </span><span class = 'units'>"+ rise_M+"/ </span>";
			html_text += "<span>"+ set_time + " <span class = 'units'>"+ set_M+"</span></p>";
			html_text +="</div></div></div>";

			html_text += "<div id = 'DHW'>Day's Hourly Weather</div>";
			html_text += "<div id = 'arrow'><a href ='#' onclick = 'javascript: showChart()'> <img id = 'arrow_img'src = 'https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png'></a></div>";
			html_text += "<div id = 'chart_div'></div>";

			document.getElementById("results").innerHTML = html_text;

			var hour_tem = details["hourly"]["data"];
			for(var i = 0; i < 24; i++){
				data_T[i] = hour_tem[i]["temperature"];
			}
			console.log(data_T);
			google.charts.load('current', {packages: ['corechart', 'line']});
			google.charts.setOnLoadCallback(drawChart);
		}

		function toHour(time){
			date_time = new Date(parseInt(time * 1000));
			return date_time.getHours();
		}

		function timeAdjust(time){
			if (time == 0) return 0;
			if (time > 12) return (24 - time);
			return (time * -1);	
		}

		function showChart(){
			var img_src = document.getElementById("arrow_img").src;
			console.log(img_src);
			if (img_src == 'https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png'){
				document.getElementById("arrow_img").setAttribute('src', 'https://cdn0.iconfinder.com/data/icons/navigation-set-arrows-part-one/32/ExpandLess-512.png');
				document.getElementById("chart_div").style.display = "block";
			}
			else {
				document.getElementById("arrow_img").setAttribute('src', 'https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png');
				document.getElementById("chart_div").style.display = "none";
			}
		}

		function drawChart(){
			var data = new google.visualization.DataTable();
			data.addColumn('number', 'X');
			data.addColumn('number', 'T');
			data.addRows([
				[0, data_T[0]],   [1, data_T[1]],  [2, data_T[2]],  [3, data_T[3]],  [4, data_T[4]],  [5, data_T[5]],
        		[6, data_T[6]],  [7, data_T[7]],  [8, data_T[8]],  [9, data_T[9]],  [10, data_T[10]], [11, data_T[11]],
        		[12, data_T[12]], [13, data_T[13]], [14, data_T[14]], [15, data_T[15]], [16, data_T[16]], [17, data_T[17]],
        		[18, data_T[18]], [19, data_T[19]], [20, data_T[20]], [21, data_T[21]], [22, data_T[22]], [23, data_T[23]]
				]);
			var options = {
        		hAxis: {
         	 		title: 'Time',
         	 		titleTextStyle: {
         	 			color: 'black',
         	 			fontSize: 12,
         	 			fontName: 'serif',
         	 			italic: true
         	 			}
       			},
        		vAxis: {
          			title: 'Temperature',
          			titleTextStyle:{
          				fontName: 'serif',
          				italic: true,
          				fontSize: 12
          				}
        		},
        		width: 800,
        		height: 200,
        		colors: ['#A8D0D9']
      			};
      		var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
      		chart.draw(data, options);
		}
	</script>
</html>