<%@ include file="/WEB-INF/views/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title>Login</title>
        <meta charset="utf-8">
        <!-- IE쿼크모드(호환성보기) 설정, 최신버젼으로 렌더링  -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="login">
        <meta name="author" content="kbtapjm">
        
        <!-- Bootstrap -->
        <link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet" media="screen">
        <!-- Custom style signin -->
        <link href="<c:url value='/css/signin.css'/>" rel="stylesheet" media="screen">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
        
	</head>
	<body>
	   <!-- container -->
        <div class="container">
            <form class="form-signin" name="frmLogin" id="frmLogin" action="<c:url value='/j_spring_security_check' />">
                <h2 class="form-signin-heading">Please sign in</h2>
                <input type="text" id="email" name="email" class="form-control" placeholder="Email address" required autofocus>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                <label class="checkbox">
                    <input type="checkbox" value="remember-me" id="rememberMe"> Remember me
                </label>
                <label>
                    <a href="#">Forgot your password?</a>
                    &nbsp;&nbsp;&nbsp;
                    <a href="<c:url value='/user/userCreate' />">Sign in</a>
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>
        <!-- /container -->
        
        <!--  =========================================================== -->
        <!-- js lib -->
        <!--  =========================================================== -->
        <!-- jquery -->
        <script src="<c:url value='/js/bower_components/jquery/jquery.js'/>"></script>
        <!-- angularJS -->
        <script src="<c:url value='/js/bower_components/angular/angular.js'/>"></script>
        <!-- underscore -->
        <script src="<c:url value='/js/bower_components/underscore/underscore.js'/>"></script>
        <!-- bootstrap -->
        <script src="<c:url value='/js/bower_components/bootstrap/dist/js/bootstrap.js'/>"></script>
        <!-- jquery cookie -->
        <script src="<c:url value='/js/common/jquery/jquery.cookie.js'/>"></script>
        <!--  =========================================================== -->
        
        <!-- application -->
        <script src="<c:url value='/js/common/common.js'/>"></script>
        
        <script type="text/javascript">
        
            var MyHubApp = {
                pageInit: function() {
                    // data init
                    this.data.init();
                    
                    // event init
                    this.event.init();
                },
                
                data: {
                    init: function() {
                        this.getCookie();
                    },
                    
                    // 저장된 쿠키 가져오기
                    getCookie: function() {
                    	var email = commonObj.data.cookie.get('email');
                    	
                        if (email) {
                            $('#email').val(email);
                            $('#rememberMe').attr('checked', 'checked');
                        }
                    },
                    
                    // 쿠키에 E-mail  저장
                    saveCookie: function() {
                    	var email = $('#email').val();
                        
                    	if (email.trim().length != 0) {
                            commonObj.data.cookie.set('email', email, 7);   // 쿠키 저장
                        } else {
                            commonObj.data.cookie.del('email', email, -1);  // 쿠키 삭제
                        }
                    }
                },
                
                event: {
                    init: function() {
                        // Remember me
                        $('#rememberMe').on('click', function() {
                        	MyHubApp.data.saveCookie();
                        });
                    }
                }
            };
        
            $(document).ready(function() {
                try {
                    MyHubApp.pageInit();
                } catch (e){
                    log('Error : ' + e.toString());
                }
            });
        
        </script>
    </body>
</html>