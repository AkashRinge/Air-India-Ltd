<%-- 
    Document   : index
    Created on : 29 Jun, 2017, 12:05:18 PM
    Author     : joker96
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<head>
<title>Upload files</title>
</head>
<body>
 
    <div style="padding:5px; color:red;font-style:italic;">
       ${errorMessage}
    </div>
    
    <h2>Upload File</h2>
 
    <form method="post" action="${pageContext.request.contextPath}/uploadToDB" enctype="multipart/form-data">
        Select file to upload:
        <br />
        <input type="file" name="myFile"  />
        <br />
        <input type="submit" value="Upload" />
        <br />
    </form>   
    <a href="${pageContext.request.contextPath}/fileList">List of files</a>

</body>
</html>