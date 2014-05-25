<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.find.password"/></title>
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
                        // initialize
                    },
                    
                    passwordSearch: function() {
                    	var email = $.trim($('#email').val());
                        if (email.length === 0) {
                            commBootObj.alerts({
                            	renderId:'alertPsdArea',
                            	msg: '<spring:message code="myhub.label.input.email.address"/>',
                            	type: commBootObj.constants.alerts.WARNING
                            });
                            $('#email').focus();
                            
                            return false;
                        }
                        
                        // ajax call
                        var url = commonObj.config.contextPath.concat('/user/passwordSearch');
                        var pars = $('#frmPsd').serialize();
                        
                        commonObj.data.ajax(url, {pars: pars, async: true, 
                            onsucc: function(res) {
                            	var type = '';
                                
                                // 결과에 따른 처리가 필요시
                                var resultCd = res.resultCd;
                                if (resultCd === commonObj.constants.result.SUCCESS) {
                                	type = commBootObj.constants.alerts.SUCCESS;
                                } else {
                                	type = commBootObj.constants.alerts.WARNING;
                                }
                                
                                commBootObj.alerts({
                                    renderId:'alertPsdArea',
                                    msg: res.resultMsg,
                                    type: type
                                });
                            },
                            onerr: function(res) {
                                alert(res);
                            }
                        });
                    },
                    
                    emailSearch: function() {
                    	var phoneNo = $.trim($('#phoneNo').val());
                        if (phoneNo.length === 0) {
                            commBootObj.alerts({
                                renderId:'alertEmailArea',
                                msg: '<spring:message code="myhub.label.input.phone"/>',
                                type: commBootObj.constants.alerts.WARNING
                            });
                            $('#phoneNo').focus();
                            
                            return false;
                        }
                        
                        // ajax call
                        var url = commonObj.config.contextPath.concat('/user/emailSearch');
                        var pars = $('#frmEmail').serialize();
                        
                        commonObj.data.ajax(url, {pars: pars, async: true, 
                            onsucc: function(res) {
                                var type = '';
                                
                                // 결과에 따른 처리가 필요시
                                var resultCd = res.resultCd;
                                if (resultCd === commonObj.constants.result.SUCCESS) {
                                    commBootObj.alerts({
                                        renderId:'alertEmailArea',
                                        msg: res.resultData,
                                        type: commBootObj.constants.alerts.SUCCESS
                                    });
                                } else {
                                    commBootObj.alerts({
                                        renderId:'alertEmailArea',
                                        msg: res.resultMsg,
                                        type: commBootObj.constants.alerts.WARNING
                                    });
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
                    	// 이메일 찾기
                        $('#btnEmailSearch').on('click', function() {
                            MyHubApp.data.emailSearch();
                        });
                    	
                    	// 비밀번호 찾기
                        $('#btnPasswordSearch').on('click', function() {
                        	MyHubApp.data.passwordSearch();    
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
                <!-- header -->
                <%@ include file="/WEB-INF/views/jsp/common/layout/header.jsp" %>
                <!-- /header -->
                <!-- form Strart -->
                <blockquote>
                    <p>
                        이메일찾기
                    </p>
                </blockquote>
                <!-- Alert area -->
                <div id="alertEmailArea"></div>
                <!-- /Alert area -->
                
                <form class="form-horizontal" id="frmEmail" name="frmEmail" onsubmit="return false;">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label"><spring:message code="myhub.label.phone"/></label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="phoneNo" name="phoneNo" placeholder="phoneNo" maxlength="11" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-primary" id="btnEmailSearch"><spring:message code="myhub.label.search"/></button>
                        </div>
                    </div>
                </form>
                <!-- form End -->

                <!-- form Strart -->
                <blockquote>
                    <p>
                        <spring:message code="myhub.label.find.password"/>
                    </p>
                </blockquote>
                <!-- Alert area -->
                <div id="alertPsdArea"></div>
                <!-- /Alert area -->
                
                <form class="form-horizontal" id="frmPsd" name="frmPsd" onsubmit="return false;">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label"><spring:message code="myhub.label.email"/></label>
                        <div class="col-sm-3">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email" maxlength="50" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-primary" id="btnPasswordSearch"><spring:message code="myhub.label.search"/></button>
                            <button class="btn btn-default" id="btnCancel"><spring:message code="myhub.label.cancel"/></button>
                        </div>
                    </div>
                </form>
                <!-- form End -->
            </div>
        </div>
    </body>
</html>