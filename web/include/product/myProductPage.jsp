<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<script>
var ReloadConfirm=false;
var ReloadConfirmid=0;
var productName="";
var productStatus="";
$(function(){
	$("div#productListDiv[status='dealing']").hide();
	$("a[productStatus]").click(function(){
		productStatus = $(this).attr("productStatus");	
		$("div.orderType div").removeClass("selectedOrderType");
		$(this).parent("div").addClass("selectedOrderType");
		if(productStatus=="" || productStatus=="all" ){
		      $("div#productListDiv[status='all']").show();
			  $("div#productListDiv[status='dealing']").hide();
		}else{
  			  $("div#productListDiv[status='dealing']").show();
		      $("div#productListDiv[status='all']").hide();
		//if(deleteOrder){
		//	$("table.orderListItemTable[isdelete='true']").hide();}	
		}
	});
	$("#addForm").submit(function() {
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
	$("a").click(function(){
		var deleteLink = $(this).attr("deleteLink");
		console.log(deleteLink);
		if("true"==deleteLink){
			var confirmDelete = confirm("确定删除商品？");
			if(confirmDelete)
				return true;
			return false;
			
		}
	});
	$("a.CancelAndReloadLink").click(function(){
		ReloadConfirmid = $(this).attr("pid");
		productName = $(this).attr("pname");
		$("#product_name").text(productName);
		ReloadConfirm = false;
		$("#ReloadConfirmModal").modal("show");
	});
	$("#ReloadConfirmButton").click(function(){
		ReloadConfirm = true;
		$("#ReloadConfirmModal").modal('hide');
	});	
	
	$('#ReloadConfirmModal').on('hidden.bs.modal', function (e) {
		if(ReloadConfirm){
			var page="forereloadproduct";
			$.post(
				    page,
				    {"pid":ReloadConfirmid},
				    function(result){
						if("success"==result){
							alert("商品重新上架成功！");
							location.reload(true);
						}
						else{
							location.href="login.jsp";
						}
				    }
				);
			
		}
	})		
		
});
</script>
<title>商品管理</title>
<div class="workingArea" style="margin:40px 50px;">
	<div class="orderType">
		<div class="selectedOrderType"><a productStatus="all" href="#nowhere">所有商品</a></div>
		<div><a  productStatus="selling" href="#nowhere">交易中商品</a></div>
	</div>
    
    <div id="productListDiv" status="all">
   		 <div class="listDataTableDiv" style="min-height:220px;" ><table 
            class="table table-striped table-bordered table-hover  table-condensed" id="productListTable" status="all">
            <thead>
                <tr class="success">
                    <th>ID</th>
                    <th>图片</th>
                    <th width="400px">商品名称</th>
                    <th>商品描述</th>
                    <th width="80px">价格</th>
                    <th width="80px">图片管理</th>
                    <th width="42px">编辑</th>
                    <th width="60px">商品状态</th>
                    <th width="180px">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ps}" var="p">
                    <tr >
                        <td>${p.id}</td>
                        <td>
                         
                        <c:if test="${!empty p.firstProductImage}">
                            <img width="40px"  src="img/productSingle/${p.firstProductImage.id}.jpg">
                        </c:if>
                         
                        </td>
                        <td>${p.name}</td>
                        <td>${p.description}</td>
                        <td>${p.price}</td>
                        <td><a href="admin_productImage_list?pid=${p.id}"><span
                                class="glyphicon glyphicon-picture"></span></a></td>
                        <td><a href="foreedit?id=${p.id}"><span
                                class="glyphicon glyphicon-edit"></span></a></td>
                       	<c:if test="${p.status=='dealing' }">
								 <td>交易中</td>									
						</c:if>
                        <c:if test="${p.status=='selling' }">
								 <td>销售中</td>									
						</c:if>
                        <c:if test="${p.status=='fail' }">
								 <td>交易失败</td>									
						</c:if>
						<c:if test="${p.status=='success' }">
								 <td>交易成功</td>									
						</c:if>
                        <c:if test="${p.status=='dealing' }">
								 <td><a class="CancelAndReloadLink" reload="true"  pid="${p.id}" pname="${p.name}" href="#nowhere">
									<button class="orderListItemConfirm"> 取消交易并重新上架 </button></a>
							     </td>									
						</c:if>
                        <c:if test="${p.status=='selling' || p.status=='success' }">
								 <td><a deleteLink="true"
                                   href="foredeleteProduct?id=${p.id}"><span class="     glyphicon glyphicon-trash"></span></a>
                                </td>									
						</c:if>
                        <c:if test="${p.status=='fail' }">
								 <td><a class="CancelAndReloadLink" reload="true"  pid="${p.id}" pname="${p.name}" href="#nowhere">
									<button class="orderListItemConfirm"> 商品重新上架 </button></a>					
                                </td>									
						</c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>       
         </div>
    
       <div class="pageDiv" style="text-align:center;">
            <%@include file="../admin/adminPage.jsp"%>
        </div>
    </div>
  
  
    <div id="productListDiv" status="dealing">
    	<div class="listDataTableDiv" style="min-height:220px;" >
          <table 
            class="table table-striped table-bordered table-hover  table-condensed"  >
            <thead>
                <tr class="success">
                    <th>ID</th>
                    <th>图片</th>
                    <th>商品名称</th>
                    <th>商品描述</th>
                    <th width="80px">价格</th>
                    <th width="200px">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${Dealps}" var="dp">
                    <tr >
                        <td>${dp.id}</td>
                        <td>
                         
                        <c:if test="${!empty dp.firstProductImage}">
                            <img width="40px" src="img/productSingle/${dp.firstProductImage.id}.jpg">
                        </c:if>
                         
                        </td>
                        <td>${dp.name}</td>
                        <td>${dp.description}</td>
                        <td>${dp.price}</td>
                        <td>
                        	<a class="CancelAndReloadLink" reload="true"  pid="${dp.id}" pname="${dp.name}" href="#nowhere">
									<button class="orderListItemConfirm"> 取消交易并重新上架 </button>
							</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
         </table>
   	    </div>
    </div>
            <div class="modal fade" id="ReloadConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
               <div class="modal-dialog">
                 <div class="modal-content">
                    <div class="modal-header">
                         <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span>
                               <span class="sr-only">Close</span></button>
                            <h4 class="modal-title">确定该商品重新上架？</h4>
                    </div>
                    <div class="modal-body">
                            <p>商品名称</p>
                            <p id="product_name"></p>
                    </div>
                    <div class="modal-footer">
                           <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                           <button class="btn btn-primary " id="ReloadConfirmButton" type="button">确定</button>
                    </div>
                  </div><!-- /.modal-content -->
               </div><!-- /.modal-dialog -->
            </div>
    <div class="panel panel-warning addDiv" style="width:400px;margin:0px auto;">
        <div class="panel-heading">新增商品</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="foreaddProduct">
                <table class="addTable" id="addProductTable">
                    <tr>
                        <td>商品名称</td>
                        <td><input id="name" name="name" type="text"
                            class="form-control"></td>
                    </tr>
                    <tr>
                        <td>类别</td>
                        <td><select name="Category" id="Category" >
                        	 <c:forEach items="${cs}" var="c">
                                <option <c:if test='${c.id == firstCtgry.id}'>selected="selected"</c:if> value="${c.id}">${c.name}</option>
                             </c:forEach>
                        	</select>
                        </td>
                    </tr>
                    <tr>
                        <td>商品描述</td>
                        <td><input id="description" name="description" type="text"
                            class="form-control"></td>
                    </tr>
                    <tr>
                        <td>价格</td>
                        <td><input id="price"  value="" name="price" type="text"
                            class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${c.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
 
</div>