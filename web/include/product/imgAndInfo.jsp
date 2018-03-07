<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<script>
 
$(function(){
    $(".buyLink").click(function(){
        var page = "forecheckLogin";
        $.get(
                page,
                function(result){
                    if("success"==result){
                    	if(confirm("确认购买?")){
                    		location.href= $(".buyLink").attr("href");
                    	}
                    }
                    else{
                        $("#loginModal").modal('show');                     
                    }
                }
        );      
        return false;
    });
     
    $("button.loginSubmitButton").click(function(){
        var name = $("#name").val();
        var password = $("#password").val();
         
        if(0==name.length||0==password.length){
            $("span.errorMessage").html("请输入账号密码");
            $("div.loginErrorMessageDiv").show();           
            return false;
        }
         
        var page = "foreloginAjax";
        $.get(
                page,
                {"id":name,"password":password},
                function(result){
                    if("success"==result){
                        location.reload();
                    }
                    else{
                        $("span.errorMessage").html("账号密码错误");
                        $("div.loginErrorMessageDiv").show();                       
                    }
                }
        );          
         
        return true;
    });
     
    $("img.smallImage").mouseenter(function(){
        var bigImageURL = $(this).attr("bigImageURL");
        $("img.bigImg").attr("src",bigImageURL);
    });
     
    $("img.bigImg").load(
        function(){
            $("img.smallImage").each(function(){
                var bigImageURL = $(this).attr("bigImageURL");
                img = new Image();
                img.src = bigImageURL;
                 
                img.onload = function(){
                    console.log(bigImageURL);   
                    $("div.img4load").append($(img));
                };
            });     
        }
    );
});
 
</script>
 
<div class="imgAndInfo">
 
    <div class="imgInimgAndInfo">
        <img src="img/productSingle/${p.firstProductImage.id}.jpg" class="bigImg">
        <div class="smallImageDiv">
            <c:forEach items="${p.productSingleImages}" var="pi">
                <img src="img/productSingle_small/${pi.id}.jpg" bigImageURL="img/productSingle/${pi.id}.jpg" class="smallImage">
            </c:forEach>
        </div>
        <div class="img4load hidden" ></div>
    </div>
     
    <div class="infoInimgAndInfo">
         
        <div class="productTitle">
            ${p.name}
        </div>
            
        <div class="promotionDiv">
             <span class="promotionPriceDesc">价格 </span>
             <span class="promotionPriceYuan">¥</span>
             <span class="promotionPrice">
                 <fmt:formatNumber type="number" value="${p.price}" minFractionDigits="2"/>
             </span>               
        </div>   
         
        <div class="buyDiv">
            <a class="buyLink" href="forebuyone?pid=${p.id}&uid=${user.id}"><button class="buyButton">立即购买</button></a>
<!--             <a href="#nowhere" class="addCartLink"><button class="addCartButton"><span class="glyphicon glyphicon-shopping-cart"></span>加入购物车</button></a> -->
        </div>
    </div>
     
    <div style="clear:both"></div>
     
</div>