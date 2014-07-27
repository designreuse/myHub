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
        
        <link href="<c:url value='/css/bootstrap/bootstrap.min.css'/>" rel="stylesheet" media="screen">
        <link href="<c:url value='/css/jquery/jquery-ui.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/jquery/ui.jqgrid.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/jquery/ui.multiselect.css'/>" rel="stylesheet">
        <link href="<c:url value='/css/carousel.css'/>" rel="stylesheet" media="screen">
        
        <script src="<c:url value='/js/jquery/jquery.js'/>"></script>
        <script src="<c:url value='/js/jquery/jquery-ui.js'/>"></script>
        <script src="<c:url value='/js/jquery/grid/grid.locale-kr.js'/>"></script>
        <script src="<c:url value='/js/jquery/grid/jquery.jqGrid.src.js'/>"></script>
        <script src="<c:url value='/js/angular/angular.js'/>"></script>
        <script src="<c:url value='/js/underscore/underscore.js'/>"></script>
        <script src="<c:url value='/js/bootstrap/bootstrap.js'/>"></script>
        <script src="<c:url value='/js/jquery/jquery.validate.js'/>"></script>
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