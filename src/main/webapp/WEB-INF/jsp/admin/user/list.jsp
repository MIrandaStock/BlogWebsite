<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../layout/header.jsp"%>
<!-- 内容主体区域 -->
<div class="content-wrapper" style="padding: 50px 0 40px;">
	<section class="content-header">
    <h1>
      用户
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/user/list">用户</a></li>
      <li class="active">列表</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">用户列表</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <form action="/admin/user/list" method="get" class="form-inline">
          <div class="form-group" style="margin-bottom: 10px;">
            <input type="text" name="username" value="${username}" class="form-control" placeholder="用户名">
            <input type="text" name="email" value="${email}" class="form-control" placeholder="邮箱">
            <button type="submit" class="btn btn-primary btn-sm">搜索</button>
          </div>
        </form>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>#</th>
            <th>用户名</th>
            <th>邮箱</th>
            <th>积分</th>
            <th>时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="user" items="${page.list}">
            <tr>
              <td>${user.userId}</td>
              <td><a href="/user/${user.userName}" target="_blank">${user.userName}</a></td>
              <td>${user.email}</td>
              <td>${user.score}</td>
              <td><fmt:formatDate type="both" value="${user.createDate}" /></td>
              <td>
                  	<button onclick="actionBtn('${user.userId}','delete',this)" class="btn btn-xs btn-danger">删除</button>
                    <button onclick="actionBtn('${user.userId}','blacklist',this)" class="btn btn-xs btn-danger">
                      <c:choose>
                        <c:when test="${user.isBlock}"> 取消拉黑</c:when>
                        <c:otherwise> 拉黑</c:otherwise>
                      </c:choose>
                    </button>
              </td>
            </tr>
			</c:forEach>
          </tbody>
        </table>
      </div>
      <div class="panel-footer" id="paginate"></div>
    </div>
  </section>
  <script type="text/javascript">
  	$(function(){
  		$(".sidebar-menu li:eq(5)").addClass("active");
  	    var username = '${username}';
  	    var email = '${email}';
  	    var p = '${p}';//当前页数
  	  	var count = ${page.totalRow};//数据总量
  	 	var limit = ${page.pageSize};//每页显示的条数
  	 	var url = "?username="+username+"&email="+email+"&p=";//url 	 	
  	 	paginate(count,limit,p,url);
  	});
  	

  	function actionBtn(userid, action, self){
      var msg, url;
      var tip = $(self).text().replace(/[\r\n]/g, '').trim();
      if (action === 'delete') {
        url = '/admin/user/delete?id=' + userid;
        msg = '确定' + tip + '这个用户吗？';
      } else if (action === 'blacklist') {
        url = '/admin/user/blacklist?id=' + userid;
        msg = '确定' + tip + '这个用户吗？';
      }
      if (confirm(msg)) {
        $.get(url, function (data) {
          if (data.success === true) {
            toast(data.error, "success");
            setTimeout(function () {
              window.location.reload();
            }, 700);
          } else {
            toast(data.error);
          }
        })
      }
 	}
  </script>
</div>
<%@ include file="../layout/footer.jsp"%>