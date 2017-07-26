<%-- 
    Document   : fileList
    Created on : 5 Jul, 2017, 1:36:39 PM
    Author     : joker96
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Blob"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="oracleconnection.OracleConnection" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>List of files in Database</h3>
        <%
            int n = OracleConnection.recordcount();
            ArrayList<Integer> ids = OracleConnection.idList();
            String name[] = new String[n];
            String path = request.getContextPath();
            for(int i=0; i<n; i++)
            {
                name[i] = OracleConnection.retrieveName(ids.get(i));
                out.print(ids.get(i));
                out.print("\t<a target='_blank' href='" + path + "/download?id="+ ids.get(i) + "'>" + name[i] + "</a>");
                out.print("<br>");
            }
        %>
    </body>
</html>
