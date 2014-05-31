<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

                <div class="navbar navbar-inverse navbar-static-top" role="navigation">
                    <div class="container">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="<c:url value='/main'/>">MyHub</a>
                        </div>
                        <div class="navbar-collapse collapse">
                        
                            <!-- 서비스 메뉴는 로그인 한 사용자에게만 활성화 -->
                            <security:authorize access="fullyAuthenticated">
                            <ul class="nav navbar-nav">
                                <li class="active"><a href="#">Friend</a></li>
                            </ul>
                            <form class="navbar-form navbar-left" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                </div>
                                <button type="submit" class="btn btn-default">Search</button>
                            </form>
                            </security:authorize>
                            
                            <ul class="nav navbar-nav navbar-right">
	                            <!-- 로그인한 사용자 정보 -->
	                            <security:authorize access="fullyAuthenticated">
                                <li>
	                               <a href="#">
	                                   <security:authentication property="principal.username"/>
	                               </a>
                                </li>
                                </security:authorize>
                                
                                <!-- 관리자 메뉴 -->
                                <security:authorize access="fullyAuthenticated and hasRole('ROLE_ADMIN')" >
                                <li>
                                   <a href="#"><spring:message code="myhub.label.admin"/></a>
                                </li>
                                </security:authorize>
                                
                                <!-- 언어변경은 로그인 인증 후 -->
                                <security:authorize access="fullyAuthenticated">
                                <li class="dropdowmyn">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Language <b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Korean</a></li>
                                        <li><a href="#">English</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="<c:url value='/j_spring_security_logout' />">Logout</a>
                                </li>
                                </security:authorize>

                                <!-- 로그인을 안한 상태는 로그인 버튼 활성화 -->
                                <security:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_USER" >
                                <li>
                                    <a href="<c:url value='/' />">Login</a>
                                </li>
                                </security:authorize>
                                
                            </ul>
                        </div>
                    </div>
                </div>

<security:authorize access="fullyAuthenticated">
<!-- 페이지 항상 호출 -->                
<script type="text/javascript">
	var headerFn = {
		getActiveUserList : function() {
			var url = commonObj.config.contextPath.concat('/user/getActiveUserList');
            var pars = '';
            
            commonObj.data.ajax(url, {pars: pars, async: true, 
                onsucc: function(res) {
                    var resultCd = res.resultCd;
                    if (resultCd === commonObj.constants.result.SUCCESS) {
                    	var resultData = res.resultData;
                    	
                    }
                },
                onerr: function(res) {
                    alert(res);
                }
            });
		}	
	};
	
	// 즉시실행함수
	(function() {
		headerFn.getActiveUserList();
	}());

</script>
</security:authorize>