<%--
  Created by IntelliJ IDEA.
  User: Minh Khang
  Date: 6/4/2020
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h1>User Management</h1>
    <h3>
        <a href="/user?action=create">Add New User</a>
    </h3>
    <h3>
        <a href="/user?action=searchCountry">Search User By Country</a>
    </h3>
    <h3>
        <a href="/user?action=sortByName">Sort User By Name</a>
    </h3>

<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td>${user.id}</td>
                <td><a href="/user?action=view&id=${user.getId()}">${user.name}</a></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.country}"/></td>
                <td>
                    <a href="/user?action=edit&id=${user.id}">Edit</a>
                    <a href="/user?action=delete&id=${user.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
