<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.login"/></title>
        <meta charset="utf-8">
        <!-- IE쿼크모드(호환성보기) 설정, 최신버젼으로 렌더링  -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="login">
        <meta name="author" content="kbtapjm">
        <link rel="shortcut icon" href="<c:url value='/images/icon/favicon.png' />">
        
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
        
        <!--  =========================================================== -->
        <!-- js lib -->
        <!--  =========================================================== -->
        <!-- jquery -->
        <script src="<c:url value='/js/jquery/jquery.js'/>"></script>
        <!-- angularJS -->
        <script src="<c:url value='/js/angular/angular.js'/>"></script>
        <!-- underscore -->
        <script src="<c:url value='/js/underscore/underscore.js'/>"></script>
        <!-- bootstrap -->
        <script src="<c:url value='/js/bootstrap/bootstrap.js'/>"></script>
        <!-- jquery cookie -->
        <script src="<c:url value='/js/jquery/jquery.cookie.js'/>"></script>
        <!--  =========================================================== -->
        
        <!-- application -->
        <script src="<c:url value='/js/common.js'/>"></script>
        
        <script type="text/javascript">
        
            var MyHubApp = {
                pageInit: function() {
                	'use strict';
                	
                    // data init
                    this.data.init();
                    
                    // event init
                    this.event.init();
                },
                
                data: {
                    init: function() {
                        this.getCookie();
                        this.isSecurityExpired();
                        
                        // locale
                        //$('#locale').val('${sessionScope["org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE"]}');
                    },
                    
                    // 저장된 쿠키 가져오기
                    getCookie: function() {
                        var email = commonObj.data.cookie.get('email');
                        
                        if (email) {
                            $('#email').val(email);
                            $('#rememberMe').attr('checked', 'checked');
                        }
                    },
                    
                    // 쿠키에  저장
                    saveCookie: function() {
                        var email = $('#email').val();
                        
                        if (email.trim().length != 0) {
                            commonObj.data.cookie.set('email', email, 7);   // 쿠키 저장
                        } else {
                            commonObj.data.cookie.del('email', email, -1);  // 쿠키 삭제
                        }
                    },
                    
                    // 로그인
                    login: function() {
                    	var email = $('#email').val();
                        if($.trim(email).length === 0) {
                        	commBootObj.alertModalMsg('<spring:message code="myhub.label.input.email.address"/>');
                            $('#email').focus();
                            return false;
                        }
                        var password = $('#password').val();
                        if($.trim(password).length === 0) {
                        	commBootObj.alertModalMsg('<spring:message code="myhub.label.input.password"/>');
                            $('#password').focus();
                            return false;
                        }
                        
                        // 계정 락 여부 체크
                        var url = commonObj.config.contextPath.concat('/isAccountLocked');
                        var pars = 'email='.concat(email);
                            
                        commonObj.data.ajax(url, {pars: pars, async: false, 
                            onsucc: function(res) {
                            	if (res.resultCd === commonObj.constants.result.FAIL) {
                            		commBootObj.alertModalMsg(res.resultMsg);
                            		$('form[name=frmLogin]').attr('onsubmit', 'return false;');
                            		return false;	
                            	}
                            	
                            	// 락이 걸린 경우가 아닌 경우 로그인 시도
                            	$('form[name=frmLogin]').attr('onsubmit', 'return true;');
                            	$('form[name=frmLogin]').attr('target', 'loginTarget');
                            	$('form[name=frmLogin]').attr('action', '<c:url value="/j_spring_security_check" />');
                                $('form[name=frmLogin]').attr('method', 'POST');
                                $('form[name=frmLogin]').submit();
                            },
                            onerr: function(res) {
                            	commBootObj.alertModalMsg('<spring:message code="myhub.error.common.fail"/>');
                            }
                        });
                    },
                    
                    // 인증(토큰)만료 여부 체크
                    isSecurityExpired: function() {
                    	if ('${resultCd}' === commonObj.constants.result.FAIL) {
                    		commBootObj.alertModalMsg('${resultMsg}');
                    	}
                    },
                    
                    // 스프링 시큐리티 인증 통과 후 결과
                    loginSuccessResult: function(result) {
                        if (result === null) return false;
                        
                        var resultCd = result.resultCd;
                        var resultMsg = result.resultMsg;
                        
                        switch(resultCd) {
                        case '0000':
                        	location.href = '<c:url value="/main"/>';
                            break;
                        case '9001': // 계정만료, 비밀번호 변경
                        	var buttonOpts = {
                                first: {
                                    fn: function() {
                                    	$("#passwordEditModal").modal({
                                            backdrop: 'static',
                                            keyboard: true,
                                            show: true
                                        });
                                    	$('#beforePassword').focus();
                                    }
                                },
                                
                                second: {
                                	fn: function() {                                                    
                                    }
                                }
                            };
                        
                        	commBootObj.alertModalMsg(resultMsg, buttonOpts);
                            break;
                            
                        case '9002': // 계정만료 날짜 알림 -> 메인페이지
                            var buttonOpts = {
                          		first: {
                          		    text: '<spring:message code="myhub.label.ok"/>',
                          		    fn: function() {
                          		        location.href = '<c:url value="/main"/>';              
                          		    }
                                }
                            };
                            commBootObj.alertModalMsg(resultMsg, buttonOpts);
                            break;
                            
                        case '9003': // 로그인 실패
                            commBootObj.alertModalMsg(resultMsg);
                            break;
                            
                        case '9999': // 서버 오류
                            commBootObj.alertModalMsg(resultMsg);
                            break;
                        }
                    },
                    
                    // 계정 변경(임시)
                    setUserAccount: function() {
                    	var userVal = $('#userChange').val();
                    	
                    	switch(Number(userVal)) {
                    	case 1:
                    		$('#email').val('kbtapjm@gmail.com');
                    		$('#password').val('111222');
                    		break;
                    	case 2:
                    		$('#email').val('tapjm@naver.com');
                            $('#password').val('qwer1234');
                            break;
                    	case 3:
                    		$('#email').val('tapjm@hanmail.net');
                            $('#password').val('111222');
                            break;
                    	case 4:
                    		$('#email').val('kbtapjm@nate.com');
                            $('#password').val('111222');
                            break;
                    	}
                    },
                    
                    // 언어 변경
                    localeChange: function() {
                    	var locale = $('#locale').val();
                    	if (locale.length === 0) {
                            return false;
                        }

                        location.href = '<c:url value="/login?lang="/>'.concat(locale);
                    },
                    
                    // 비밀번호 수정
                    updatePassword: function() {
                    	$('#passwordEditModal').modal('hide');
                    }
                },
                
                event: {
                    init: function() {
                        // Remember me
                        $('#rememberMe').on('click', function() {
                            MyHubApp.data.saveCookie();
                        });
                        
                        // Login
                        $('#btnLogin').on('click', function() {
                            MyHubApp.data.login();
                        });
                        
                        // 유저 정보 세팅
                        $('#userChange').on('change', function() {
                        	MyHubApp.data.setUserAccount();
                        });
                        
                        // 언어변경
                        $('#locale').on('change', function() {
                            MyHubApp.data.localeChange();
                        });
                        
                        // 비밀번호 수정
                        $('#btnPasswordUpdate').on('click', function() {
                            MyHubApp.data.updatePassword();
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
        
	</head>
	<body>
	   <iframe name="loginTarget" id="loginTarget" src="about:blank" width="0" height="0" allowtransparency="true" frameborder="0"></iframe>
	   
	   <!-- container -->
        <div class="container">
            <form class="form-signin" id="frmLogin" name="frmLogin" onsubmit="return false;">
                <h4 class="form-signin-heading"><spring:message code="myhub.label.signin"/></h4>
                <input type="text" id="email" name="email" class="form-control" placeholder="Email address">
                <input type="password" id="password" name="password" class="form-control" placeholder="Password">
                <label class="checkbox">
                    <input type="checkbox" value="remember-me" id="rememberMe"> <spring:message code="myhub.label.remember.id"/>
                </label>
                <!-- Remember me 
                <label class="checkbox">
                    <input type="checkbox" value="true" name="_spring_security_remember_me" id="_spring_security_remember_me">
                    Remember-me
                </label>
                 -->
                <label>
                    <a href="<c:url value='/user/userSearch' />"><spring:message code="myhub.label.forgot.password"/></a>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="<c:url value='/user/userAdd' />"><spring:message code="myhub.label.signup"/></a>
                </label>
                <button class="btn btn-lg btn-primary btn-block" id="btnLogin"><spring:message code="myhub.label.login"/></button>
                <br>
                <select class="form-control" id="locale" name="locale">
                    <option value=""><spring:message code="myhub.label.language"/></option>
                    <option value="ko_KR"><spring:message code="myhub.label.language.korean"/></option>
                    <option value="en_US"><spring:message code="myhub.label.language.english"/></option>
                </select>
                <br>
                <select class="form-control" id="userChange" name="userChange">
                    <option value=""><spring:message code="myhub.label.select"/></option>
                    <option value="1">admin(kbtapjm@gmail.com)</option>
                    <option value="2">tapjm(tapjm@naver.com)</option>
                    <option value="3">tapjm(tapjm@hanmail.net)</option>
                    <option value="4">kbtapjm(kbtapjm@nate.com)</option>
                </select>
            </form>
        </div>
        <!-- /container -->
        
        <!-- common html include -->
        <%@ include file="/WEB-INF/views/jsp/common/include/commonHtml.jsp" %>
        
        <!-- common js include -->
        <%@ include file="/WEB-INF/views/jsp/common/include/bootstrapJs.jsp" %>
    </body>
</html>