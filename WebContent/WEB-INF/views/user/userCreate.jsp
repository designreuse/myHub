<%@ include file="/WEB-INF/views/common/include/taglibs.jsp" %>

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
                        	var url = '/user/getUserByEmail';
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
                    	// validation check
                    	if (!vaild) {
                    		// 일반 폼 검증
                    	}
                    	
                    	// set userId
                    	var email = $('#email').val();
                    	var userId = email.substring(0, email.indexOf('@'));
                    	$('#userId').val(userId);
                        
                    	// ajax call
                    	var pars = $('#frmCreate').serialize();
                    	var url = '/user/userSave';
                    	
                        commonObj.data.ajax(url, {pars: pars, async: true, 
                            onsucc: function(res) {
                            	var status = res.status;
                            	
                            	if (status === 'SUCCESS') {
                            		location.href = '<c:url value="/login"/>';
                            	} else{
                            		alert(res.message);
                            	}
                            },
                            onerr: function(res) {
                            	alert(res);
                            }
                        });
                    }
                },
                
                event: {
                    init: function() {
                        
                        // 등록
                        $('#btnCreate').on('click', function() {
                            // create
                        	//MyHubApp.data.userCreate(false);
                        });
                        
                        // 취소
                        $('#btnCancel').on('click', function() {
                            location.href = '<c:url value="/login"/>';
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
                <!-- Header Start -->
                <div class="navbar navbar-inverse navbar-static-top" role="navigation">
                    <div class="container">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="#">MyHub</a>
                        </div>
                        <div class="navbar-collapse collapse">
                            <ul class="nav navbar-nav">
                                <li class="active"><a href="#">Home</a></li>
                                <li><a href="#friend">Friend</a></li>
                                <li><a href="#contact">My</a></li>
                            </ul>
                            <form class="navbar-form navbar-left" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                </div>
                                <button type="submit" class="btn btn-default">Search</button>
                            </form>
                            <ul class="nav navbar-nav navbar-right">
                                <li>
                                    <a href="#">Memo</a>
                                </li>
                                <li class="dropdowmyn">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Language <b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Korean</a></li>
                                        <li><a href="#">English</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- Heaser End -->

                <!-- form Strart -->
                <blockquote>
                    <p><spring:message code="myhub.label.signup"/></p>
                </blockquote>
                <form class="form-horizontal" role="form" id="frmCreate" name="frmCreate" onsubmit="return false;">
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