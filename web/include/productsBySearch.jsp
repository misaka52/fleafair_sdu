<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="searchProducts">
	
	<c:forEach items="${ps}" var="p">
		<div class="productUnit" price="${p.price}">
            <div class="productUnitFrame">
                <a href="foreproduct?pid=${p.id}">
                    <img class="productImage" src="img/productSingle_middle/${p.firstProductImage.id}.jpg">
                </a>
                <span class="productPrice">¥<fmt:formatNumber type="number" value="${p.price}" minFractionDigits="2"/></span>
                <a class="productLink" href="foreproduct?pid=${p.id}">
                 ${fn:substring(p.name, 0, 50)}
                </a>
                 
                <a  class="tmallLink" href="foreproduct?pid=${p.id}">山威专卖</a>
            </div>
        </div>
	</c:forEach>
	<c:if test="${empty ps}">
		<div class="noMatch">没有满足条件的产品<div>
	</c:if>
	
		<div style="clear:both"></div>
</div>