<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>    
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../layout/header.jsp"%>
<!-- 内容主体区域 -->
<div class="content-wrapper" style="padding: 50px 0 40px;">
	<section class="content-header">
    <h1>
     注册申请
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/node/list"> 注册申请</a></li>
      <li class="active">列表</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">注册申请&nbsp;&nbsp;列表</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>#</th>
            <th>用户名</th>
            <th>邮箱</th>
            <th>申请时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="user" items="${page.list}">
            <tr>
              <td>${user.userId}</td>
              <td>${user.userName}</td>
              <td>${user.email}</td>
              <td><fmt:formatDate type="both" value="${user.createDate}" /></td>
              <td>
                  <button onclick="actionBtn('${user.email}', 'pass', this)" class="btn btn-xs btn-warning">通过申请</button>
                  <button onclick="actionBtn('${user.email}', 'refuse', this)" class="btn btn-xs btn-danger">拒绝申请</button>
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
      $(".sidebar-menu li:eq(4)").addClass("active");
      var username = '${username}';
      var email = '${email}';
      var p = '${p}';//当前页数
      var count = ${page.totalRow};//数据总量
      var limit = ${page.pageSize};//每页显示的条数
      var url = "?username="+username+"&email="+email+"&p=";//url
      paginate(count,limit,p,url);
    });

    function actionBtn(email, action, self){
      var msg,url;
      // var tip = $(self).text().replace(/[\r\n]/g, '').trim();
      if(action === 'pass'){
        url = '/admin/node/pass?email=' + email;
        msg = '确定同意该用户的注册申请吗？';
      }else if(action === 'refuse'){
        url = '/admin/node/refuse?email=' + email;
        msg = '确定拒绝该用户的注册申请吗？';
      }
      if(confirm(msg)){
        $.get(url,function(data){
          if(data.success === true){
            toast(data.error, "success");
            setTimeout(function(){
              location.href = "/admin/node/list";
            },700);
          }else{
            toast(data.error);
          }
        })
      }
    }
  </script>
</div>
<%@ include file="../layout/footer.jsp"%>