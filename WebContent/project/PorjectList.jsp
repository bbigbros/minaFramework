<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>프로젝트 리스트</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>회원목록2</h1>
<p><a href='add.do'>신규 프로젝트</a></p>
<c:forEach var="project" items="${projects}"> 
${project.no},
<a href='update.do?no=${project.no}'>${project.title}</a>,
${project.startDate},
${project.endDate },
${project.state}
<a href='delete.do?no=${project.no}'>[삭제]</a><br>
</c:forEach>
</body>
</html>