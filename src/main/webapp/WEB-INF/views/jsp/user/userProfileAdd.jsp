<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title>프로필 등록</title>
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
        <script src="<c:url value='/js/jquery/jquery-ui.js'/>"></script>
        <script src="<c:url value='/js/jquery/jquery.fileupload.js'/>"></script>
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
        <!-- application -->
        <script src="<c:url value='/js/common.js'/>"></script>
        <script src="<c:url value='/js/ajaxfileupload.js'/>"></script>
        <!--  =========================================================== -->
        
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
                        
                    },
                
                    /* // 이미지 미리보기용 함수
                    fileUpload: function() {
                    	$('#profileImg').fileupload({
                            url : commonObj.config.contextPath.concat('/user/profileUpload'),
                            dataType: 'json',
                            //replaceFileInput: false,
                            add: function(e, data){
                            	log(data);
                            	
                                var uploadFile = data.files[0];
                                if (!(/png|jpe?g|gif/i).test(uploadFile.name)) {
                                    alert('png, jpg, gif 만 가능합니다');
                                    goUpload = false;
                                } else if (uploadFile.size > 5000000) { // 5mb
                                    alert('파일 용량은 5메가를 초과할 수 없습니다.');
                                }
                                data.submit();
                            },
                            progressall: function(e,data) {
                                var progress = parseInt(data.loaded / data.total * 100, 10);
                                $('#progress .bar').css(
                                    'width',
                                    progress + '%'
                                );
                            },
                            done: function (e, data) {
                                var code = data.result.code;
                                var msg = data.result.msg;
                                if(code=='0') {
                                    alert(msg);
                                } else {
                                    alert(code + " : " + msg);
                                } 
                            },
                            fail: function(){
                                alert("서버와 통신 중 문제가 발생했습니다");
                            }
                        });
                    }, */
                    
                    profileUpload: function() {
                    	var attachFile = $.trim($('#attachFile').val());
                        if (attachFile.length === 0) {
                            alert('파일을 선택하세요.');
                            $('#attachFile').focus();
                            return false;
                        }
                        if (!(/png|jpe?g|gif/i).test(attachFile)) {
                            alert('png, jpg, gif 만 가능합니다');
                            $('#attachFile').focus();
                            return false;
                        }
                        
                        var url = commonObj.config.contextPath.concat('/user/profileUpload');
                        var pars = commonObj.data.form.getConvertObjToForm($('#frmProfileAdd'));
                        
                        $.ajaxFileUpload({
                            url: url,
                            fileElementId: 'attachFile',
                            data: pars,
                            success: function(res) {
                            	alert('정상처리되었습니다.');
                            	opener.MyHubApp.popup.userProfileAdd.callBack();
                            	window.close();
                            },
                            error: function(x,e) {
                                alert('처리 중 오류가 발생하였습니다.');
                            }
                        });
                    	
                    }
                },
                
                event: {
                    init: function() {
                    	// 업로드 
                    	$('#btnUpload').on('click', function() {
                    		MyHubApp.data.profileUpload();
                        });
                    	
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
                
                // http://dimdim.tistory.com/34
                // http://noritersand.tistory.com/265
            });
        
        </script>
        
    </head>
    <body>
        <div class="container">
            <!-- 미리보기용
            <form class="form-horizontal" id="frmAdd" name="frmAdd" onsubmit="return false;">
                <input type="hidden" name="userKey" id="userKey" value="${userKey}">
                
                <div class="form-group">
                    <label for="password" class="col-sm-3 control-label">이미지</label>
                    <div class="col-sm-5">
                        <input type="file" class="form-control" id="profileImg" name="profileImg" maxlength="12" value="">
                    </div>
                </div>
                <div class="progress">
                    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:0%;">
                        <span class="sr-only">60% Complete</span>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" id="btnCancel"><spring:message code="myhub.label.cancel"/></button>
                    </div>
                </div>
            </form>
             -->
            
            <form class="form-horizontal" id="frmProfileAdd" name="frmProfileAdd" enctype="multipart/form-data" onsubmit="return false;">
                <input type="hidden" name="userKey" id="userKey" value="${userKey}">
                
                <div class="form-group">
                    <label for="password" class="col-sm-3 control-label">이미지</label>
                    <div class="col-sm-5">
                        <input type="file" class="form-control" id="attachFile" name="attachFile">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-primary" id="btnUpload"><spring:message code="myhub.label.create"/></button>
                        <button class="btn btn-default" id="btnCancel"><spring:message code="myhub.label.cancel"/></button>
                    </div>
                </div>
            </form>
            
            
        </div>
    </body>
</html>