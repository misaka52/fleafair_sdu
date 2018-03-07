<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<%@include file="../header.jsp"%>
<%@include file="../top.jsp"%>
<%@include file="../simpleSearch.jsp"%>
<title>商品图片管理</title>
<script>
$(function(){
    $(".addFormSingle").submit(function(){
        if(checkEmpty("filepathSingle","图片文件")){
            $("#filepathSingle").value("");
            return true;
        }
        return false;
    });
    $(".addFormDetail").submit(function(){
        if(checkEmpty("filepathDetail","图片文件"))
            return true;
        return false;
    });
	$("a").click(function(){
		var deleteLink = $(this).attr("deleteLink");
		console.log(deleteLink);
		if("true"==deleteLink){
			var confirmDelete = confirm("确定删除图片？");
			if(confirmDelete)
				return true;
			return false;
			
		}
	});
});
 
</script>
 
<title>产品图片管理</title>
 
<div class="workingArea"> 
    <table class="addPictureTable" align="center">
        <tr>
            <td class="addPictureTableTD">
              <div>
                <div class="panel panel-warning addPictureDiv">
                    <div class="panel-heading">新增产品<b class="text-primary"> 单个 </b>图片</div>
                      <div class="panel-body" align="center">
                            <form method="post" class="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data">
                                <table class="addTable" id="addImageTable">
                                    <tr>
                                        <td>请选择本地图片 尺寸400X400 为佳</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input id="filepathSingle" type="file" name="filepath" />
                                        </td>
                                    </tr>
                                    <tr class="submitTR">
                                        <td align="center">
                                            <input type="hidden" name="type" value="type_single" />
                                            <input type="hidden" name="pid" value="${p.id}" />
                                            <button type="submit" class="btn btn-success">提 交</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                      </div>
                  </div>            
                <table class="table table-striped table-bordered table-hover  table-condensed"> 
                    <thead>
                        <tr class="success">
                            <th>ID</th>
                            <th>产品单个图片缩略图</th>
                            <th>删除</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${pisSingle}" var="pi">
                            <tr>
                                <td>${pi.id}</td>
                                <td>
                                <a title="点击查看原图" href="img/productSingle/${pi.id}.jpg"><img height="50px" src="img/productSingle/${pi.id}.jpg"></a>  
                                </td>
                                <td><a deleteLink="true"
                                    href="admin_productImage_delete?id=${pi.id}"><span
                                        class="     glyphicon glyphicon-trash"></span></a></td>
         
                            </tr>
                        </c:forEach>
                    </tbody>    
                </table>  
                         
              </div>          
            </td>
            <td class="addPictureTableTD">
              <div>
                 
                <div class="panel panel-warning addPictureDiv">
                    <div class="panel-heading">新增产品<b class="text-primary"> 详情 </b>图片</div>
                      <div class="panel-body"  align="center">
                            <form method="post" class="addFormDetail" action="admin_productImage_add" enctype="multipart/form-data">
                                <table class="addTable" id="addImageTable">
                                    <tr>
                                        <td>请选择本地图片 宽度790  为佳</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input id="filepathDetail"  type="file" name="filepath" />
                                        </td>
                                    </tr>
                                    <tr class="submitTR">
                                        <td align="center">
                                            <input type="hidden" name="type" value="type_detail" />
                                            <input type="hidden" name="pid" value="${p.id}" />
                                            <button type="submit" class="btn btn-success">提 交</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                      </div>
                  </div>
                  <table class="table table-striped table-bordered table-hover  table-condensed"> 
                        <thead>
                            <tr class="success">
                                <th>ID</th>
                                <th>产品详情图片缩略图</th>
                                <th>删除</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${pisDetail}" var="pi">
                                <tr>
                                    <td>${pi.id}</td>
                                    <td>
                                        <a title="点击查看原图" href="img/productDetail/${pi.id}.jpg"><img height="50px" src="img/productDetail/${pi.id}.jpg"></a>
                                    </td>
                                    <td><a deleteLink="true"
                                        href="admin_productImage_delete?id=${pi.id}"><span
                                            class="     glyphicon glyphicon-trash"></span></a></td>
             
                                </tr>
                            </c:forEach>
                        </tbody>    
                    </table>                          
              </div>          
            </td>
        </tr>
    </table>
 
</div>
 
<%@include file="../footer.jsp"%>