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
                            <ul class="nav navbar-nav">
                                <li class="active"><a href="#">Friend</a></li>
                            </ul>
                            <form class="navbar-form navbar-left" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                </div>
                                <button type="submit" class="btn btn-default">Search</button>
                            </form>
                            <ul class="nav navbar-nav navbar-right">
                                
                                <!-- 로그인한 사용자 정보 -->
                                <security:authorize access="fullyAuthenticated">
                                <li>
	                               <a href="#">
	                                   <security:authentication property="principal.username"/>
	                               </a>
                                </li>
                                </security:authorize>
                                
                                
                                
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
                            </ul>
                        </div>
                    </div>
                </div>