<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
 
<%
	long telNumber = Long.parseLong(request.getParameter("sellerId"));
%>
<div style="height:400px;padding:100px 0px 0px 150px;">
	<font size="5">
		<p>购买成功，请尽快和卖家联系，获取详细交易信息。</p>
		<p>卖家电话：<%=telNumber %></p>
	</font>
</div>