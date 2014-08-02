<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<!doctype html>
<html>
    <head>
        <title><decorator:title default="MyHub" /></title>
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
        <!-- jquery -->
        <link href="<c:url value='/css/jquery/jquery-ui.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/jquery/ui.jqgrid.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/jquery/ui.multiselect.css'/>" rel="stylesheet">
        
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
        <!-- jquery grid -->
        <script src="<c:url value='/js/jquery/jquery-ui.js'/>"></script>
        <script src="<c:url value='/js/jquery/grid/grid.locale-kr.js'/>"></script>
        <script src="<c:url value='/js/jquery/grid/jquery.jqGrid.src.js'/>"></script>
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
            $(document).ready(function() {
                try {
                    MyHubApp.pageInit();
                } catch (e){
                    log('Error : ' + e.toString());
                }
            });
        
        </script>
        <decorator:head />
    </head>
    <body>
        <div class="navbar-wrapper">
            <div class="container">
                <!-- header -->
                <%@ include file="/WEB-INF/views/jsp/common/layout/header.jsp" %>
                <!-- /header -->
                
                <!-- body -->
                <decorator:body />
                <!-- /body -->
                
                <!-- footer -->
                
                <!-- /footer -->
            </div>
        </div>
        
        <!-- common html include -->
        <%@ include file="/WEB-INF/views/jsp/common/include/commonHtml.jsp" %>

    </body>
</html>