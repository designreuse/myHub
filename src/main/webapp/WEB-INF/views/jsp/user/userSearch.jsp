<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.find.password"/></title>
        
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
                <!-- email find area -->
                <blockquote>
                    <p><spring:message code="myhub.label.find.email"/></p>
                </blockquote>
                
                <div id="alertEmailArea"></div>
                
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
                <!-- /email find area -->

                <!-- passowrd find area-->
                <blockquote>
                    <p><spring:message code="myhub.label.find.password"/></p>
                </blockquote>
                
                <div id="alertPsdArea"></div>
                
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
                <!-- /passowrd find area-->
    </body>
</html>