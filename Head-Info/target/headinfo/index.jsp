<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<body>
	<h2>Head Info</h2>

	<c:if test="${sessionScope.github_access_token == null}">
		<p>
			<b>Please login to <a
				href="${pageContext.request.contextPath}/github">Github</a></b>
		</p>
	</c:if>




	<h3>Enter person email/name to find:</h3>
	<form method="get" action="${pageContext.request.contextPath}/search">
		<p>
			<b>Search term: </b><br> <input name="searchTerm" type="text"
				size="40" />
		</p>
		<input type="submit" value="Search" />
	</form>
</body>
</html>
