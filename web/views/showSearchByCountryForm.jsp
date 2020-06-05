<%--
  Created by IntelliJ IDEA.
  User: Minh Khang
  Date: 6/4/2020
  Time: 3:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search By Country</title>
</head>
<body>

<div>
    <h1>Search By Country</h1>

    <form action="/user?action=searchByCountry" method="post">
        <p>Enter Country that You Want To Search : </p>
        <br>
        <br>
        <input type="text" name="country">

        <button type="submit">Search</button>
    </form>
</div>

</body>
</html>
