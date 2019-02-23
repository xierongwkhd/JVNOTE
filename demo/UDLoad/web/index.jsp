<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<html>
<head>
<base href="<%=basepath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
</head>
<body>
<h1>上传1</h1>
<form action="<c:url value="/Upload1Servlet"/>" method="post" enctype="multipart/form-data">
    用户名；<input type="text" name="username"/><br/>
    照　片：<input type="file" name="zhaoPian"/><br/>
    <input type="submit" value="上传"/>
</form>
</body>
</html>
