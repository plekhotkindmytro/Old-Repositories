<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="uwc.headinfo.model.GithubUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Info about user</title>
</head>
<body>
	<p>
		<a href="/">Search for another
			person</a>
	</p>


	<h1>Github user data:</h1>
	<table border="1">
		<tr>
			<th>Login</th>
			<th>Avatar</th>
			<th>Commits count</th>
		</tr>
		<c:forEach items="${githubUserInfo}" var="githubUser">
			<tr>
				<td style="vertical-align: top;">${githubUser.login}</td>
				<td style="vertical-align: top;"><img
					src="${githubUser.avatarUrl}"></td>
				<td style="vertical-align: top;"><c:choose>
						<c:when test="${fn:length(githubUser.eventList) gt 0}">
							<span style="color: blue">Normal Social Activity</span>
						</c:when>
						<c:when test="${fn:length(githubUser.eventList) gt 30}">
							<span style="color: green">Good Social Activity</span>
						</c:when>
						<c:otherwise>
							<span style="color: red">Bad Social Activity</span>
						</c:otherwise>
					</c:choose></td>
				<td>
					<table>
						<tr>
							<th>Type</th>
							<th>Repository Name</th>
						</tr>
						<c:forEach items="${githubUser.eventList}" var="eventList">

							<tr>
								<td>${eventList.type}</td>
								<td>${eventList.repoName}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>