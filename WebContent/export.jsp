<%@ page session="true" import="de.htwg_konstanz.ebus.framework.wholesaler.api.bo.*,de.htwg_konstanz.ebus.framework.wholesaler.api.boa.*,de.htwg_konstanz.ebus.wholesaler.demo.util.Constants,java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>eBusiness Framework Demo - Orders</title>
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<link rel="stylesheet" type="text/css" href="default.css">
<link rel="stylesheet" type="text/css" href="layout.css">
</head>

<body>

<%@ include file="header.jsp" %>
<%@ include file="error.jsp" %>
<%@ include file="authentication.jsp" %>
<%@ include file="navigation.jspfragment" %>

<h1>Export: Export Product Catalog</h1>
<table>
	<tr>
		<td>Whole Catalog</td>
		<td><a href="<%= response.encodeURL("controllerservlet?action=export&view=BMECAT") %>">BMECAT</a></td>
		<td><a href="<%= response.encodeURL("controllerservlet?action=export&view=XHTML") %>">xhtml</a></td>
	</tr>
	<tr>
	
		<td>
		<form id="searchForm" method="post" action="controllerservlet?action=export">
			<input type="hidden" name="view" id="view"/>
			<input type="text" name="search"/>
			
		</form> 
		</td>
		<script>
			function submitScript(view) {
				document.getElementById("view").value = view;
				document.getElementById("searchForm").submit();
			}
		</script>
		<td><a onclick="submitScript('BMECAT');">BMECAT</a></td>
		<td><a href="<%= response.encodeURL("controllerservlet?action=export&view=XHTML") %>">xhtml</a></td>
	</tr>
</table>


<p>
<input type=button name=go-back value=" back " onclick="javascript:history.back()">
</p>
</body>
</html>
