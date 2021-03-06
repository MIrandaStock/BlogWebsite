<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>首页-注册</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 引入 Bootstrap -->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/css/app.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="/resources/images/favicon.ico">
</head>
<body>
	<div class="wrapper">
		<jsp:include page="components/head.jsp"></jsp:include>
		<div class="row" style="margin-left:80px;margin-top: 40px;height: 265px;margin-bottom: 30px ">
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">
						<a href="/" style="color: white">主页</a>
						<a href="" style="color: white">/&nbsp;注册</a>
					</div>
					<div class="panel-body">
						<form role="form" id="form" method="post">
							<div class="form-group">
								<label for="username">用户名</label> <input type="text"
									class="form-control" id="username" name="username"
									placeholder="用户名,只能是2-16位的a-z,A-Z,0-9组合">
							</div>
							<div class="form-group">
								<label for="password">密码</label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="密码">
							</div>
							<div class="form-group">
								<label for="email">邮箱</label> <input type="email"
									class="form-control" id="email" name="email" placeholder="邮箱">
							</div>
							<button type="button" class="btn btn-primary" id="reg_btn" style="background-color: darkred;">注册</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
  </div>
</div>
    <div id="container" style="position: absolute">
        <br>
        <p style="color: #778087;">
            <strong>
                <a href="/about" style="color: white;margin-left: 40px">关于我们</a>
                &nbsp;
            </strong>
            <span class="pull-right">
            <a href="/admin/login" target="_blank" style="color: white;margin-right: 40px">登录后台</a>&nbsp;&nbsp;
        </span>
        </p>
        <p style="color: white;margin-left: 40px"><a data-v-2b9fe4cd="">©2019 BLOG</a></p>
        <p style="color: white;margin-left: 40px">♥ Hope you can find your world here</p>
    </div>
	<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
	<script src="/resources/js/jquery.js"></script>
	<!-- 引入 Bootstrap -->
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="/resources/js/register.js"></script>
</body>
</html>