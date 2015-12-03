<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>Sitemap Generator</title>
</head>
<body>
	<h2>Sitemap Generator</h2>

	<h3>Enter website URL:</h3>
	<form method="post" action="sitemap">
		<p>
			<b>URL: </b><br> <input name="websiteUrl" type="text" size="100" />
			<c:if test="${error != null}">
				<font color="red">${error}</font>
			</c:if>
		</p>
		<p>
			<b>Email: </b><br> <input name="email" type="email" size="100" />
			<sub>(We will send you the sitemap via email)</sub>
		</p>
		<p>
			<b>Frequency: </b><br> <select name="freq" style="width: 120px">
				<option value="" selected="">None</option>
				<option value="always">Always</option>
				<option value="hourly">Hourly</option>
				<option value="daily">Daily</option>
				<option value="weekly">Weekly</option>
				<option value="monthly">Monthly</option>
				<option value="yearly">Yearly</option>
				<option value="never">Never</option>

			</select>
		</p>

		<p>
			<b>Last modification: </b><br> 
			
			<input type="radio" name="lastmod" value="none" id="lastmod1">
			<label for="lastmod">None</label>
			<br>
			<input type="radio" name="lastmod" value="server" id="lastmod2">
			<label for="lastmod2"> Use server date/time</label> 
		</p>

		<p>
			<b>Priority: </b><br> 
			
			<input type="radio" name="priority" value="none" id="priority1">
			<label for="priority">None</label>
			<br>
			<input type="radio" name="priority" value="random" id="priority2">
			<label for="priority">Random</label> 
		</p>
		
		<input type="submit" value="Generate" />
	</form>
</body>
</html>
