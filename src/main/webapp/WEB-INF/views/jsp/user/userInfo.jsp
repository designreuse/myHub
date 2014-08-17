<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.userInfo"/></title>
        
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
                        this.getUserInfo();
                    },
                                        
                    getUserInfo: function() {
                    	var url = commonObj.config.contextPath.concat('/user/getUserInfo');
                        var pars = '';
                            
                        commonObj.data.ajax(url, {pars: pars, async: false, 
                            onsucc: function(res) {
                            	if (res.resultCd === commonObj.constants.result.FAIL) {
                                    commBootObj.alertModalMsg(res.resultMsg);
                                    return false;   
                                }
                            	
                            	MyHubApp.data.setUserInfo(res.resultData);
                            },
                            onerr: function(res) {
                                commBootObj.alertModalMsg('<spring:message code="myhub.error.common.fail"/>');
                            }
                        });
                    },
                    
                    setUserInfo: function(data) {
                    	if (!data) return false;
                    	
                    	$('#userKey').val(data.userKey);
                    	$('#email').val(data.email);
                    	$('#emailText').text(data.email);
                    	$('#userName').text(data.userName);
                    	$('#birthday').text(commonObj.data.util.getBirthDay(data.birthday, '.'));
                    	$('#phoneNo').text(commonObj.data.util.getMoblPhoneNo(data.phoneNo, '-'));
                    	$('#gender').text(data.gender === 'M' ? '<spring:message code="myhub.label.gender.male"/>' : '<spring:message code="myhub.label.gender.female"/>');
                    },
                    
                    deleteUser: function() {
                    	var url = commonObj.config.contextPath.concat('/user/userDelete');
                        var pars = 'userKey='.concat($('#userKey').val());
                        
                        commonObj.data.ajax(url, {pars: pars, async: false, 
                            onsucc: function(res) {
                                if (res.resultCd === commonObj.constants.result.FAIL) {
                                    alert(res.resultMsg);
                                    return false;   
                                }
                                
                                alert(res.resultMsg);
                                location.href = '<c:url value="/j_spring_security_logout" />';
                            },
                            onerr: function(res) {
                                alert('<spring:message code="myhub.error.common.fail"/>');
                            }
                        });
                    }
                },
                
                event: {
                    init: function() {
                        // 수정
                        $('#btnEdit').on('click', function() {
                        	location.href = '<c:url value="/user/userEdit"/>';
                        });
                        
                        // 비밀번호 수정
                        $('#btnPwdUpdate').on('click', function() {
                        	MyHubHeaderApp.popup.userPasswordEdit($('#email').val());
                        });
                        
                        // 프로필 추가
                        $('#btnProfileAdd').on('click', function() {
                        	MyHubApp.popup.userProfileAdd();
                        });
                        
                        // 회원탈퇴
                        $('#btnDelete').on('click', function() {
                            var buttonOpts = {
                            	first: {
                            	    fn: function() {
                                        MyHubApp.data.deleteUser();
                                    }
                                },
                                 
                                second: {
                                    fn: function() {}
                                }
                            };
                         
                            commBootObj.alertModalMsg('<spring:message code="myhub.label.members.reave"/>', buttonOpts);
                        });
                    }
                },
                
                popup: {
                	userProfileAdd: function() {
                        commonObj.popup.open({
                            url : '<c:url value="/user/userProfileAdd"/>',
                            pars : 'userKey='.concat($('#userKey').val()),
                            title: 'userProfileAdd',
                            width : '600',
                            height : '400'
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
                <!-- label -->
                <blockquote>
                    <p><spring:message code="myhub.label.userInfo"/></p>
                </blockquote>
                <!-- /label -->
                
                <!-- form -->
                <form class="form-horizontal" id="frmInfo" name="frmInfo" onsubmit="return false;" enctype="multipart/form-data">
                    <input type="hidden" name="userKey" id="userKey" value="">
                    <input type="hidden" name="email" id="email" value="">
                    
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label"><spring:message code="myhub.label.email"/></label>
                        <div class="col-sm-3">
                            <p class="form-control-static" id="emailText"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userName" class="col-sm-2 control-label"><spring:message code="myhub.label.name"/></label>
                        <div class="col-sm-3">
                            <p class="form-control-static" id="userName"></p>
                            <!-- 권한정보 -->
                            <security:authentication property="principal.Authorities"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="birthday" class="col-sm-2 control-label"><spring:message code="myhub.label.birthday"/></label>
                        <div class="col-sm-3">
                            <p class="form-control-static" id="birthday"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="birthday" class="col-sm-2 control-label"><spring:message code="myhub.label.phone"/></label>
                        <div class="col-sm-3">
                            <p class="form-control-static" id="phoneNo"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="birthday" class="col-sm-2 control-label"><spring:message code="myhub.label.gender"/></label>
                        <div class="col-sm-3">
                            <p class="form-control-static" id="gender"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-primary" id="btnEdit"><spring:message code="myhub.label.update"/></button>
                            <button class="btn btn-info" id="btnPwdUpdate"><spring:message code="myhub.label.change.password"/></button>
                            <button class="btn btn-info" id="btnProfileAdd">프로필 등록</button>
                            <button class="btn btn-danger" id="btnDelete"><spring:message code="myhub.label.delete.user"/></button>
                        </div>
                    </div>
                </form>
                <!-- /form -->
    </body>
</html>