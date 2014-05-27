<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.signup"/></title>
        <meta charset="utf-8">
        <!-- IE쿼크모드(호환성보기) 설정, 최신버젼으로 렌더링  -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="login">
        <meta name="author" content="kbtapjm">
        <link rel="shortcut icon" href="<c:url value='/images/icon/favicon.png' />">
        
        <!-- cache setup -->
        <META http-equiv="Expires" content="-1"> 
        <META http-equiv="Pragma" content="no-cache"> 
        <META http-equiv="Cache-Control" content="No-Cache"> 
        
        <!-- Bootstrap -->
        <link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet" media="screen">
        
        <!-- Carousel -->
        <link href="<c:url value='/css/carousel.css'/>" rel="stylesheet" media="screen">
        
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
        <!-- jquery validate -->
        <script src="<c:url value='/js/jquery/jquery.validate.js'/>"></script>
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
                        this.validate();
                    },
               
                    // validation func
                    validate: function() {
                        $('#frmCreate').validate({
                            submitHandler: function(form) {
                                MyHubApp.data.userCreate(true);
                            },
                            // 규칙
                            rules: {
                                email: {
                                    required: true,
                                    email: true,
                                    emailDuplicateCheck: true
                                },
                                password: {
                                    required: true,
                                    minlength: 6
                                },
                                cfPassword: {
                                    required: true,
                                    equalTo: '#password'
                                },
                                userName: 'required',
                                birthday: {
                                    required: true,
                                    minlength: 8, 
                                    number: true
                                },
                                phoneNo: {
                                    required: true,
                                    minlength: 10, 
                                    number: true
                                },
                                gender: 'required',
                                agree: 'required'
                            },
                            
                            // 메시지
                            messages: {
                                email: {
                                    required: '<spring:message code="myhub.label.input.email.address"/>',
                                    email: '<spring:message code="myhub.label.input.vaild.email.address"/>',
                                    emailDuplicateCheck: '<spring:message code="myhub.label.input.email.exists"/>'
                                },
                                password: {
                                    required: '<spring:message code="myhub.label.input.password"/>',
                                    minlength: jQuery.format('<spring:message code="myhub.label.input.minimum.characters"/>')
                                },
                                cfPassword: {
                                    required: '<spring:message code="myhub.label.input.confirm.password"/>',
                                    equalTo: '<spring:message code="myhub.label.input.password.match"/>'
                                },
                                userName: '<spring:message code="myhub.label.input.name"/>',
                                birthday: {
                                    required: '<spring:message code="myhub.label.input.birthdy"/>',
                                    minlength: jQuery.format('<spring:message code="myhub.label.input.minimum.characters"/>'),
                                    number: '<spring:message code="myhub.label.input.only.numbers"/>'
                                },
                                phoneNo: {
                                    required: '<spring:message code="myhub.label.input.phone"/>',
                                    minlength: jQuery.format('<spring:message code="myhub.label.input.minimum.characters"/>'),
                                    number: '<spring:message code="myhub.label.input.only.numbers"/>'
                                },
                                gender: '<spring:message code="myhub.label.select.gender"/>',
                                agree: '<spring:message code="myhub.label.check.privacy.aggree"/>'
                            },
                            // 에러 클래스
                            errorClass: 'control-label',
                            
                            // 에러 요소(생성할 Tag)
                            errorElement: 'span',
                            
                            // 포커스인 
                            highlight: function(element, errorClass, validClass) {
                                $(element).parents('.form-group').addClass('has-error');
                                $(element).parents('.form-group').removeClass('has-success');
                            },
                            // 포커스 아웃
                            unhighlight: function(element, errorClass, validClass) {
                                $(element).parents('.form-group').removeClass('has-error');
                                $(element).parents('.form-group').addClass('has-success');
                            }
                        });
                        
                        // email 중복체크
                        $.validator.addMethod('emailDuplicateCheck', function(email) {
                        	var url = commonObj.config.contextPath.concat('/user/getUserByEmail');
                        	var pars = 'email='.concat(email);
                        	
                            var ret = true;
                        	commonObj.data.ajax(url, {pars: pars, async: false, 
                                onsucc: function(res) {
                                	// 중복시
                                	if (res === true) {
                                    	ret = false;
                                    } 
                                },
                                onerr: function(res) {
                                	return false;
                                }
                            });
                        	
                        	return ret;
                        }, '');
                    },
                    
                    // 유저 등록
                    userCreate: function(vaild) {
                    	
                    	// 일반 폼 검증
                    	if (!vaild) {
                    		var email = $.trim($('#email').val());
                    		if (email.length === 0) {
                    			alert('<spring:message code="myhub.label.input.email.address"/>');
                    			$('#email').focus();
                    			return false;
                    		}
                    		// 이메일 검증
                    		if (!commonObj.data.vaild.email(email)) {
                    			alert('<spring:message code="myhub.label.input.vaild.email.address"/>');
                                $('#email').focus();
                                return false;
                    		}
                    		var password = $.trim($('#password').val());
                            if (password.length === 0) {
                                alert('<spring:message code="myhub.label.input.password"/>');
                                $('#password').focus();
                                return false;
                            }
                            if (password.length < 6) {
                            	alert('<spring:message code="myhub.label.input.minimum.characters" arguments="6" />');
                                $('#password').focus();
                                return false;
                            }
                            var cfPassword = $.trim($('#cfPassword').val());
                            if (cfPassword.length === 0) {
                                alert('<spring:message code="myhub.label.input.confirm.password"/>');
                                $('#cfPassword').focus();
                                return false;
                            }
                            var userName = $.trim($('#userName').val());
                            if (userName.length === 0) {
                                alert('<spring:message code="myhub.label.input.name"/>');
                                $('#userName').focus();
                                return false;
                            }
                            var birthday = $.trim($('#birthday').val());
                            if (birthday.length === 0) {
                                alert('<spring:message code="myhub.label.input.birthdy"/>');
                                $('#birthday').focus();
                                return false;
                            }
                            if (birthday.length !== 8) {
                                alert('<spring:message code="myhub.label.input.minimum.characters" arguments="8"/>');
                                $('#birthday').focus();
                                return false;
                            }
                            if (!commonObj.data.vaild.number(birthday)) {
                                alert('<spring:message code="myhub.label.input.only.numbers"/>');
                                $('#birthday').focus();
                                return false;
                            }
                            var gender = $.trim($('#gender').val());
                            if (gender.length === 0) {
                                alert('<spring:message code="myhub.label.select.gender"/>');
                                $('#gender').focus();
                                return false;
                            }
                            var agree = $('#agree').is(':checked');
                            if (!agree) {
                            	alert('<spring:message code="myhub.label.check.privacy.aggree"/>');
                                $('#agree').focus();
                                return false;
                            }
                    	}
                    	
                    	// set userId
                    	var email = $('#email').val();
                    	var userId = email.substring(0, email.indexOf('@'));
                    	$('#userId').val(userId);
                        
                    	// ajax call
                    	var url = commonObj.config.contextPath.concat('/user/userCreate');
                    	var pars = $('#frmCreate').serialize();
                    	
                        commonObj.data.ajax(url, {pars: pars, async: true, 
                            onsucc: function(res) {
                            	var resultCd = res.resultCd;
                                if (resultCd === commonObj.constants.result.FAIL) {
                                	alert(res.resultMsg);
                                	return false;
                                } 
                                
                                alert(res.resultMsg);
                                location.href = '<c:url value="/"/>';
                            },
                            onerr: function(res) {
                            	alert(res);
                            }
                        });
                    }
                },
                
                event: {
                    init: function() {
                        
                        // 등록(jQuery validator 사용시에는 이벤트 등록 안함)
                        $('#btnCreate').on('click', function() {
                        	//MyHubApp.data.userCreate(false);
                        });
                        
                        // 취소
                        $('#btnCancel').on('click', function() {
                            location.href = '<c:url value="/"/>';
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
        <div class="navbar-wrapper">
            <div class="container">
                <!-- header -->
                <%@ include file="/WEB-INF/views/jsp/common/layout/header.jsp" %>
                <!-- /header -->

                <!-- form Strart -->
                <blockquote>
                    <p>
                        <spring:message code="myhub.label.signup"/>
                        <!-- Spring EL  
                        <spring:eval expression="@prop['mail.id']" />
                         -->
                    </p>
                </blockquote>
                <form class="form-horizontal" id="frmCreate" name="frmCreate" onsubmit="return false;">
                    <input type="hidden" name="userId" id="userId" value="">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label"><spring:message code="myhub.label.email"/></label>
                        <div class="col-sm-3">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email" maxlength="50" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label"><spring:message code="myhub.label.password"/></label>
                        <div class="col-sm-3">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Password" maxlength="12" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cfPassword" class="col-sm-2 control-label"><spring:message code="myhub.label.confirm.password"/></label>
                        <div class="col-sm-3">
                            <input type="password" class="form-control" id="cfPassword" name="cfPassword" placeholder="Password" maxlength="12" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userName" class="col-sm-2 control-label"><spring:message code="myhub.label.name"/></label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="userName" name="userName" placeholder="UserName" maxlength="50" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="birthday" class="col-sm-2 control-label"><spring:message code="myhub.label.birthday"/></label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="birthday" name="birthday" placeholder="Birthday" maxlength="8" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="birthday" class="col-sm-2 control-label"><spring:message code="myhub.label.phone"/></label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="phoneNo" name="phoneNo" placeholder="phoneNo" maxlength="11" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="birthday" class="col-sm-2 control-label"><spring:message code="myhub.label.gender"/></label>
                        <div class="col-sm-3">
                            <select class="form-control" id="gender" name="gender">
                                <option value=""><spring:message code="myhub.label.select"/></option>
                                <option value="M"><spring:message code="myhub.label.gender.male"/></option>
                                <option value="F"><spring:message code="myhub.label.gender.female"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="birthday" class="col-sm-2 control-label"><spring:message code="myhub.label.personal.received"/></label>
                        <div class="col-sm-3">
                            <input type="checkbox" id="agree" name="agree"> 
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-primary" id="btnCreate"><spring:message code="myhub.label.save"/></button>
                            <button class="btn btn-default" id="btnCancel"><spring:message code="myhub.label.cancel"/></button>
                        </div>
                    </div>
                </form>
                <!-- form End -->   
            </div>
        </div>
    </body>
</html>