<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<script>
$(function(){
     
    <c:if test="${!empty msg}">
    $("span.errorMessage").html("${msg}");
    $("div.registerErrorMessageDiv").css("visibility","visible");       
    </c:if>
     
    $(".registerForm").submit(function(){
    	var id = $("#id").val().trim();
    	var name = $("#name").val().trim();
    	if(0==id.length){
            $("span.errorMessage").html("请输入账号");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }
    	if(isNaN(id) || 11!=id.length || id.charAt(0) != '1'){
   		 $("span.errorMessage").html("请输入正确格式的手机号");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
  	 	}
        if(0==$("#name").val().length){
            $("span.errorMessage").html("请输入昵称");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }
        if(0==$("#password").val().length){
            $("span.errorMessage").html("请输入密码");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }       
        if(0==$("#repeatpassword").val().length){
            $("span.errorMessage").html("请输入重复密码");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }       
        if($("#password").val() !=$("#repeatpassword").val()){
            $("span.errorMessage").html("重复密码不一致");
            $("div.registerErrorMessageDiv").css("visibility","visible");           
            return false;
        }       
 
        return true;
    });
})
</script>
 
<form method="post" action="foreregister" class="registerForm">
 
<div class="registerDiv">
    <div class="registerErrorMessageDiv">
        <div class="alert alert-danger" role="alert">
          <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
            <span class="errorMessage"></span>
        </div>        
    </div>
 
    <table class="registerTable" align="center">
        <tr>
            <td class="registerTableLeftTD">账号</td>
            <td  class="registerTableRightTD"><input id="id" name="id" placeholder="请使用手机号为账号，以便交易" > </td>
        </tr>    
        <tr>
            <td class="registerTableLeftTD">昵称</td>
            <td  class="registerTableRightTD"><input id="name" name="name" placeholder="设置你的昵称" > </td>
        </tr>   
        <tr>
            <td class="registerTableLeftTD">登陆密码</td>
            <td class="registerTableRightTD"><input id="password" name="password" type="password"  placeholder="设置您的登陆密码" > </td>
        </tr>
        <tr>
            <td class="registerTableLeftTD">密码确认</td>
            <td class="registerTableRightTD"><input id="repeatpassword" type="password"   placeholder="请再次输入你的密码" > </td>
        </tr>
                 
        <tr>
            <td colspan="2" class="registerButtonTD">
                <a href="registerSuccess.jsp"><button>提   交</button></a>
            </td>
        </tr>             
    </table>
</div>
</form>