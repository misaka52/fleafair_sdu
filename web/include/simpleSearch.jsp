<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<div >
    <a href="${contextPath}">
        <img id="simpleLogo" class="simpleLogo" src="img/site/sign.jpg">    
    </a>
     
    <form action="foresearch" method="post" > 
    <div class="simpleSearchDiv pull-right">
        <input type="text" placeholder="闲置课本 体育用品" name="keyword">
        <button class="searchButton" type="submit">搜山威</button>
        <div class="searchBelow">
            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count>=8 and st.count<=11}">
                    <span>
                        <a href="forecategory?cid=${c.id}">
                            ${c.name}
                        </a>
                        <c:if test="${st.count!=11}">             
                            <span>|</span>              
                        </c:if>
                    </span>           
                </c:if>
            </c:forEach>          
        </div>
    </div>
    </form>
    <div style="clear:both"></div>
</div>