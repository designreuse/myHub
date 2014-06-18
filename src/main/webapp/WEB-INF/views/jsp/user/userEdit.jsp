<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.user.edit"/></title>
        
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
                        this.validate();
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
                        $('#userName').val(data.userName);
                        $('#birthday').val(data.birthday);
                        $('#phoneNo').val(data.phoneNo);
                        $('#gender').val(data.gender);
                    },
               
                    // validation func
                    validate: function() {
                        $('#frmUpdate').validate({
                            submitHandler: function(form) {
                                MyHubApp.data.userUpdate(true);
                            },
                            // 규칙
                            rules: {
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
                                gender: 'required'
                            },
                            
                            // 메시지
                            messages: {
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
                                gender: '<spring:message code="myhub.label.select.gender"/>'
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
                    
                    // 유저 수정
                    userUpdate: function(vaild) {
                        // ajax call
                        var url = commonObj.config.contextPath.concat('/user/userUpdate');
                        var pars = $('#frmUpdate').serialize();
                        
                        commonObj.data.ajax(url, {pars: pars, async: true, 
                            onsucc: function(res) {
                                var resultCd = res.resultCd;
                                if (resultCd === commonObj.constants.result.FAIL) {
                                    alert(res.resultMsg);
                                    return false;
                                } 
                                
                                alert(res.resultMsg);
                                location.href = '<c:url value="/user/userInfo"/>';
                            },
                            onerr: function(res) {
                                alert(res);
                            }
                        });
                    }
                },
                
                event: {
                    init: function() {
                        // 취소
                        $('#btnCancel').on('click', function() {
                        	location.href = '<c:url value="/user/userInfo"/>';
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
                    <p><spring:message code="myhub.label.user.edit"/></p>
                </blockquote>
                <!-- /label -->
                
                <!-- form -->
                <form class="form-horizontal" id="frmUpdate" name="frmUpdate" onsubmit="return false;">
                    <input type="hidden" class="form-control" id="userKey" name="userKey" value="">
                    <input type="hidden" class="form-control" id="email" name="email" value="">
                
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label"><spring:message code="myhub.label.email"/></label>
                        <div class="col-sm-3">
                            <p class="form-control-static" id="emailText"></p>
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
                        <label for="phoneNo" class="col-sm-2 control-label"><spring:message code="myhub.label.phone"/></label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="phoneNo" name="phoneNo" placeholder="phoneNo" maxlength="11" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="gender" class="col-sm-2 control-label"><spring:message code="myhub.label.gender"/></label>
                        <div class="col-sm-3">
                            <select class="form-control" id="gender" name="gender">
                                <option value=""><spring:message code="myhub.label.select"/></option>
                                <option value="M"><spring:message code="myhub.label.gender.male"/></option>
                                <option value="F"><spring:message code="myhub.label.gender.female"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-primary" id="btnUpdate"><spring:message code="myhub.label.save"/></button>
                            <button class="btn btn-default" id="btnCancel"><spring:message code="myhub.label.cancel"/></button>
                        </div>
                    </div>
                </form>
                <!-- /form -->
    </body>
</html>