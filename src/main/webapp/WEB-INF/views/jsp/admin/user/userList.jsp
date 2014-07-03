<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.list"/></title>
        <meta charset="utf-8">
        <!-- IE쿼크모드(호환성보기) 설정, 최신버젼으로 렌더링  -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="login">
        <meta name="author" content="kbtapjm">
        <link rel="shortcut icon" href="<c:url value='/images/icon/favicon.png' />">
        
        <!-- Bootstrap -->
        <link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet" media="screen">
        <!-- carousel -->
        <link href="<c:url value='/css/carousel.css'/>" rel="stylesheet">
        
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
                        this.getUserList();
                    },
                    
                    getUserList: function() {
                    	var url = commonObj.config.contextPath.concat('/admin/userManage/getUserList');
                        var pars = $('#frmSearch').serialize();
                        
                        commonObj.data.ajax(url, {pars: pars, async: true, 
                            onsucc: function(res) {
                                var resultCd = res.resultCd;
                                if (resultCd === commonObj.constants.result.FAIL) {
                                    alert(res.resultMsg);
                                    return false;
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
                
                <!-- label -->
                <blockquote>
                    <p><spring:message code="myhub.label.user.list"/></p>
                </blockquote>
                <!-- /label -->
                
                <!-- search area -->
                <div align="right">
                    <form name="frmSearch" id="frmSearch" class="form-inline" role="form">
                        <div class="form-group">
                            <select class="form-control" id="gender" name="gender">
                                <option value=""><spring:message code="myhub.label.select"/></option>
                                <option value="M"><spring:message code="myhub.label.gender.male"/></option>
                                <option value="F"><spring:message code="myhub.label.gender.female"/></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" id="searchType" name="searchType">
                                <option value="name">이름</option>
                                <option value="email">이메일</option>
                                <option value="birthday">생년월일</option>
                                <option value="phoneNo">전화번호</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="searchWord" id="searchWord" placeholder="검색어를 입력 하세요.">
                        </div>
                        <button type="submit" class="btn btn-primary">검색</button>
                    </form>
                </div>
                <!-- /search area -->
                <br>
                
                <!-- list area -->
                <table class="table table-striped">
                <thead>
                    <tr>
	                    <th>#</th>
	                    <th>First Name</th>
	                    <th>Last Name</th>
	                    <th>Username</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td>Mark</td>
                        <td>Otto</td>
                        <td>@mdo</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>@fat</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>Larry</td>
                        <td>the Bird</td>
                        <td>@twitter</td>
                    </tr>
                </tbody>
                </table>
                <!-- /list area -->
                
                <!-- footer -->
                
                <!-- /footer -->
            </div>
        </div>
        
        <!-- common html include -->
        <%@ include file="/WEB-INF/views/jsp/common/include/commonHtml.jsp" %>
        
        <!-- common js include -->
        <%@ include file="/WEB-INF/views/jsp/common/include/bootstrapJs.jsp" %>
    </body>
</html>