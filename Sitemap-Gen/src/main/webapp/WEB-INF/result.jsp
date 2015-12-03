<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sitemap</title>
</head>
<body>
	<h1>Sitemap</h1>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	Link to ${websiteUrl} sitemap: <a href="${ctx}/sitemap?fileName=${sitemapFileName}">${sitemapFileName}</a>
	<c:if test="${isEmailSent}">
		<p>
			Sitemap was sent on email ${email}
		</p>
	</c:if>
	<p>
		<a href="${ctx}/">Back</a>
	</p>
</body>
</html>