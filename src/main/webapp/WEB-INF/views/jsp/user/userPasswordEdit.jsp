<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.password"/>&nbsp;<spring:message code="myhub.label.update"/></title>
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
                        $('#frmPwdUpdate').validate({
                            submitHandler: function(form) {
                            	MyHubApp.data.updatePassword();
                            },
                            // 규칙
                            rules: {
                            	beforePassword: {
                                    required: true
                                },
                                afterPassword: {
                                    required: true,
                                    minlength: 6
                                },
                                cfafterPassword: {
                                    required: true,
                                    equalTo: '#afterPassword'
                                }
                            },
                            
                            // 메시지
                            messages: {
                            	beforePassword: {
                                    required: '<spring:message code="myhub.label.input.password"/>'
                                },
                                afterPassword: {
                                    required: '<spring:message code="myhub.label.input.password"/>',
                                    minlength: jQuery.format('<spring:message code="myhub.label.input.minimum.characters"/>')
                                },
                                cfafterPassword: {
                                    required: '<spring:message code="myhub.label.input.confirm.password"/>',
                                    equalTo: '<spring:message code="myhub.label.input.password.match"/>'
                                }
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
                        
                    },
                    
                    // 비밀번호 수정
                    updatePassword: function() {
                    	var url = commonObj.config.contextPath.concat('/user/changePassword');
                        var pars = $('#frmPwdUpdate').serialize();
                            
                        commonObj.data.ajax(url, {pars: pars, async: false, 
                            onsucc: function(res) {
                            	var resultCd = res.resultCd;
                                if (resultCd === commonObj.constants.result.FAIL) {
                                    alert(res.resultMsg);
                                    return false;
                                } else {
                                	alert(res.resultMsg);
                                	window.close();
                                }
                            },
                            onerr: function(res) {
                                alert('<spring:message code="myhub.error.common.fail"/>');
                            }
                        });
                    }
                },
                
                event: {
                    init: function() {
                        // 취소
                        $('#btnCancel').on('click', function() {
                            window.close();
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
        <div class="container">
            <!-- form -->
            <form class="form-horizontal" id="frmPwdUpdate" name="frmPwdUpdate" onsubmit="return false;">
                <input type="hidden" name="email" id="email" value="${email}">
                <div class="form-group">
                    <label for="password" class="col-sm-3 control-label"><spring:message code="myhub.label.before.password"/></label>
                    <div class="col-sm-5">
                        <input type="password" class="form-control" id="beforePassword" name="beforePassword" maxlength="12" value="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-3 control-label"><spring:message code="myhub.label.password"/></label>
                    <div class="col-sm-5">
                        <input type="password" class="form-control" id="afterPassword" name="afterPassword" maxlength="12" value="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="cfPassword" class="col-sm-3 control-label"><spring:message code="myhub.label.confirm.password"/></label>
                    <div class="col-sm-5">
                        <input type="password" class="form-control" id="cfafterPassword" name="cfafterPassword" maxlength="12" value="">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-primary" id="btnUpdate"><spring:message code="myhub.label.update"/></button>
                        <button class="btn btn-default" id="btnCancel"><spring:message code="myhub.label.cancel"/></button>
                    </div>
                </div>
            </form>
            <!-- /form -->
        </div>
        
        <!-- common html include -->
        <%@ include file="/WEB-INF/views/jsp/common/include/commonHtml.jsp" %>
        
        <!-- common js include -->
        <%@ include file="/WEB-INF/views/jsp/common/include/bootstrapJs.jsp" %>
    </body>
</html>