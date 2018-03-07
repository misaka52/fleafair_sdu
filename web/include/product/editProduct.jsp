<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<%@include file="../header.jsp"%>
<%@include file="../top.jsp"%>
<%@include file="../simpleSearch.jsp"%>
 
<title>商品信息修改</title>
 
<script>
$(function() {
    $("#editForm").submit(function() {
       		 if (!checkEmpty("name", "产品名称"))
				return false;
            if (!checkEmpty("Category", "类别"))
                return false;
		    if (!checkEmpty("description", "产品描述"))
                return false;
			if (!checkNumber("price", "价格"))
                return false;
			return true;
    });
});
</script>
 
<div class="workingArea">     
    <div class="panel panel-warning editDiv" style="width:400px;margin:0px auto;">
        <div class="panel-heading" >编辑</div>
        <div class="panel-body" id="productEditTable">
            <form method="post" id="editForm" action="foreupdateProduct">
                <table class="editTable" id="productEditTable" style="width:100%;">
                    <tr>
                        <td>商品名称</td>
                        <td><input id="name" name="name" value="${p.name}"
                            type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>类别</td>
                        <td><select name="Category" id="Category" >
                        	 <c:forEach items="${cs}" var="c">
                                <option <c:if test='${c.id == cid}'>selected="selected"</c:if> value="${c.id}">${c.name}</option>
                             </c:forEach>
                        	</select>
                        </td>
                    </tr>
                     <tr>
                        <td>商品描述 </td>
                        <td><input id="description" value="${p.description}" name="description" type="text"
                            class="form-control"></td>
                    </tr>
                    <tr>
                        <td>价格</td>
                        <td><input id="price"  value="${p.price}" name="price" type="text"
                            class="form-control"></td>
                    </tr>
                                         
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                        <input type="hidden" name="id" value="${p.id}">                  
                        <button type="submit" class="btn btn-success">提 交</button></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>