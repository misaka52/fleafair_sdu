<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


<script>
var deleteOrder = false;
var sellerConfirm=false;
var buyerConfirm=false;

var deleteOrderid = 0;
var sellerConfirmid=0;
var buyerConfirmid=0;
var productName="";
var orderStatus=" ";
$(function(){
	$("a[orderStatus]").click(function(){
		orderStatus = $(this).attr("orderStatus");	
		$("div.orderType div").removeClass("selectedOrderType");
		$(this).parent("div").addClass("selectedOrderType");
		if(orderStatus=="" || orderStatus=="all" ){
		   $("table.orderListItemTable").show();
		}else if( orderStatus=="buyer" ){
			 $("table.orderListItemTable[id='buyTable']").show();
			  $("table.orderListItemTable[id='sellTable']").hide();
		}else{
			$("table.orderListItemTable[id='sellTable']").show();
			$("table.orderListItemTable[id='buyTable']").hide();
		}
		if(deleteOrder){
			$("table.orderListItemTable[isdelete='true']").hide();}	
	});
	
	$("a.deleteOrderLink").click(function(){
	
		deleteOrderid = $(this).attr("oid");
		deleteOrder = false;
		$("#deleteConfirmModal").modal("show");
	});
	
	$("button.deleteConfirmButton").click(function(){
		deleteOrder = true;
		$("#deleteConfirmModal").modal('hide');
	});	
	
	$('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
		if(deleteOrder){
			var page="foredeleteOrder";
			$.post(
				    page,
				    {"oid":deleteOrderid},
				    function(result){
						if("success"==result){
							$("table.orderListItemTable[oid="+deleteOrderid+"]").attr("isdelete","true");
							$("table.orderListItemTable[isdelete='true']").hide();
						}
						else{
							location.href="login.jsp";
						}
				    }
				);
			
		}
	})
		
	 $("a.sellerConfirmLink").click(function(){
		sellerConfirmid = $(this).attr("oid");
		productName = $(this).attr("pname");
		$("#product_S").text(productName);
		sellerConfirm = false;
		$("#sellerConfirmModal").modal("show");
	});
	
	$("#sellerConfirmButton").click(function(){
		sellerConfirm = true;
		$("#sellerConfirmModal").modal('hide');
	});	
	
	$('#sellerConfirmModal').on('hidden.bs.modal', function (e) {
		if(sellerConfirm){
			var page="foreorderConfirmed";
			$.post(
				    page,
				    {"oid":sellerConfirmid},
				    function(result){
						if("success"==result){
							alert("订单确认成功！");
							location.reload(true);
						}
						else{
							location.href="login.jsp";
						}
				    }
				);
		}
	})		
	
	$("a.buyerConfirmLink").click(function(){
		buyerConfirmid = $(this).attr("oid");
		productName = $(this).attr("pname");
		$("#product_B").text(productName);
		buyerConfirm = false;
		$("#buyerConfirmModal").modal("show");
	});
	
	$("#buyerConfirmButton").click(function(){
		buyerConfirm = true;
		$("#buyerConfirmModal").modal('hide');
	});	
	
	$('#buyerConfirmModal').on('hidden.bs.modal', function (e) {
		if(buyerConfirm){
			var page="foreconfirmPay";
			$.post(
				    page,
				    {"oid":buyerConfirmid},
				    function(result){
						if("success"==result){
							alert("收货确认成功！");
							location.reload(true);
						}
						else{
							location.href="login.jsp";
						}
				    }
				);
			
		}
	})		
	$(".ask2delivery").click(function(){
		var link = $(this).attr("link");
		$(this).hide();
		page = link;
		$.ajax({
			   url: page,
			   success: function(result){
				alert("卖家已秒发，刷新当前页面，即可进行确认收货")
			   }
			});
		
	});
});

</script>
	
<div class="boughtDiv">
	<div class="orderType">
		<div class="selectedOrderType"><a orderStatus="all" href="#nowhere">所有订单</a></div>
		<div><a  orderStatus="buyer" href="#nowhere">我是买家</a></div>
		<div><a  orderStatus="seller" href="#nowhere">我是卖家</a></div>
		<div class="orderTypeLastOne"><a class="noRightborder">&nbsp;</a></div>
	</div>
	<div style="clear:both"></div>
	<div class="orderListTitle">
		<table class="orderListTitleTable">
			<tr>
				<td >宝贝</td>
				<td width="200px">实付款</td>
				<td width="270px">交易操作</td>
			</tr>
		</table>
	
	</div>
	
	<div class="orderListItem">
		<c:forEach items="${os}" var="o">
           <c:if test="${o.buyerId==user.id}">
                <table class="orderListItemTable"  isdelete="false" id="buyTable" oid="${o.id}" >
                  <tr class="orderListItemFirstTR"  >
					<td colspan="2">
					<b><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></b> 
					<span>订单号: ${o.id} 
					</span>
					</td>
					<td  colspan="2"></td>
					<td colspan="1"></td>
					<td class="orderItemDeleteTD">
						<a class="deleteOrderLink" oid="${o.id}" href="#nowhere">
							<span  class="orderListItemDelete glyphicon glyphicon-trash"></span>
						</a>
                  </td>
				</tr>
				<tr class="orderItemProductInfoPartTR"  >
				<td border="0px"></td>
						<td class="orderItemProductInfoPartTD"  colspan="1"><img width="80" height="80" src="img/productSingle_middle/${o.product.firstProductImage.id}.jpg"></td> 
						<td class="orderItemProductInfoPartTD"  colspan="2">
							<div class="orderListItemProductLinkOutDiv">
								<a href="foreproduct?pid=${o.product.getId()}">${o.product.getName()}</a>
							</div>
						</td>
							<td valign="top"  width="220px" class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD"  colspan="1">
								<div class="orderListItemProductRealPrice">￥<fmt:formatNumber  minFractionDigits="2"  maxFractionDigits="2" type="number" value="${o.product.getPrice()}"/></div>
							</td>
							<td  class="orderItemProductInfoPartTD"  valign="top"  width="180px">
							<c:if test="${o.status=='d_seller' }">
								<a class="buyerConfirmLink" oid="${o.id}" pname="${o.product.getName()}" href="#nowhere">
							<div calss="lcy"><button  class="orderListItemConfirm" >确认收货</button></div>
								</a>
							</c:if>
							<c:if test="${o.status=='success' }">
 								<a class="buyerConfirmLink" oid="${o.id}" pname="${o.product.getName()}" href="#nowhere">
									<a href="forereview?oid=${o.id}"><button class="orderListItemConfirm" >评价</button></a>
								</a>
 							</c:if>
							<c:if test="${o.status=='dealing' }">
								<span>等待卖家发货</span>									
							</c:if>
							<c:if test="${o.status=='fail' }">
 								<span>交易失败</span>	
 							</c:if>
							<c:if test="${o.status=='finish' }">
 								<span>${o.getStatusDesc()}</span>	
 							</c:if>
							</td>						
				</tr>
              </table>	
           </c:if>
           <c:if test="${o.sellerId==user.id}">
              <table class="orderListItemTable"  isdelete="false" id="sellTable"  oid="${o.id}" >
				<tr class="orderListItemFirstTR" >
					<td colspan="2">
					<b><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></b> 
					<span>订单号: ${o.id} 
					</span>
					</td>
					<td colspan="2"></td>
					<td colspan="1"></td>
					<td class="orderItemDeleteTD">
						<a class="deleteOrderLink" oid="${o.id}" href="#nowhere">
							<span  class="orderListItemDelete glyphicon glyphicon-trash"></span>
						</a>
					</td>
				</tr>
				<tr class="orderItemProductInfoPartTR"  >
				<td border="0px"></td>
						<td class="orderItemProductInfoPartTD"><img width="80" height="80" src="img/productSingle_middle/${o.product.firstProductImage.id}.jpg" colspan="1"></td> 
						<td class="orderItemProductInfoPartTD" colspan="2">
							<div class="orderListItemProductLinkOutDiv">
								<a href="foreproduct?pid=${o.product.getId()}">${o.product.getName()}</a>
							</div>
						</td>
							<td valign="top"  width="220px" class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD" colspan="1">
								<div class="orderListItemProductRealPrice">￥<fmt:formatNumber  minFractionDigits="2"  maxFractionDigits="2" type="number" value="${o.product.getPrice()}"/></div>
							</td>
							<td valign="top"  class="orderListItemButtonTD orderItemOrderInfoPartTD" width="180px" >
							<c:if test="${o.status=='d_seller' }">
									<span>等待买家确认</span>	
							</c:if>
							<c:if test="${o.status=='finish' }">
 								<span>${o.getStatusDesc()}</span>
 							</c:if>
 							<c:if test="${o.status=='success' }">
 								<span>${o.getStatusDesc()}</span>
 							</c:if>
 							<c:if test="${o.status=='fail' }">
 								<span>交易失败</span>	
 							</c:if>
							<c:if test="${o.status=='dealing' }">
                           		 <a class="sellerConfirmLink" oid="${o.id}"  pname="${o.product.getName()}"  href="#nowhere">
									<button class="orderListItemConfirm"  >确定发货</button>
								 </a> 
							</c:if>
							</td>						
				</tr>
             </table>
          </c:if>	
		</c:forEach>
        	 <div class="modal fade" id="buyerConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
               <div class="modal-dialog">
                 <div class="modal-content">
                    <div class="modal-header">
                         <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span>
                               <span class="sr-only">Close</span></button>
                            <h4 class="modal-title">确定已收货？</h4>
                    </div>
                    <div class="modal-body">
                            <p>商品名称</p>
                            <p id="product_B"></p>
                    </div>
                    <div class="modal-footer">
                           <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                           <button class="btn btn-primary " id="buyerConfirmButton" type="button">确定</button>
                    </div>
                  </div><!-- /.modal-content -->
               </div><!-- /.modal-dialog -->
            </div>
            <div class="modal fade" id="sellerConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
               <div class="modal-dialog">
                 <div class="modal-content">
                    <div class="modal-header">
                         <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span>
                               <span class="sr-only">Close</span></button>
                            <h4 class="modal-title">确定交易完成？</h4>
                    </div>
                    <div class="modal-body">
                            <p>商品名称</p>
                            <p id="product_S"></p>
                    </div>
                    <div class="modal-footer">
                           <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                           <button class="btn btn-primary " id="sellerConfirmButton" type="button">确定</button>
                    </div>
                  </div><!-- /.modal-content -->
               </div><!-- /.modal-dialog -->
            </div>
        
	</div>
	
</div>