<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<div class="productDetailDiv" >
    <div class="productDetailTopPart">
        <a href="#nowhere" class="productDetailTopPartSelectedLink selected">商品详情</a>
        <a href="#nowhere" class="productDetailTopReviewLink">累计评价 ${reviewCount}</a>
    </div>
     
    <div class="productParamterPart">
<!--         <div class="productParamter">产品参数：</div> -->
         
        <div class="productParamterList">
			<span>${p.description}</span>
        </div>
        <div style="clear:both"></div>
    </div>
     
    <div class="productDetailImagesPart">
            <c:forEach items="${p.productDetailImages}" var="pi">
                <img src="img/productDetail/${pi.id}.jpg">
            </c:forEach>
    </div>
</div>