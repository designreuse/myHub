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
        
            var MyHubApp = {
                pageInit: function() {
                    'use strict';
                    
                    // jqgrid init
                    this.jqgrid.init();
                    
                    // data init
                    this.data.init();
                    
                    // event init
                    this.event.init();
                },
                
                jqgrid: {
                	gridXhr: null,
                	
                	init: function() {
                	    $('#gridList').jqGrid({
                	        mtype: "POST",
                	        datatype: '',
                	        colNames: [
                                '', 
                                '<spring:message code="myhub.label.name"/>',
                                '<spring:message code="myhub.label.email"/>',
                                '<spring:message code="myhub.label.gender"/>',
                                '<spring:message code="myhub.label.birthday"/>',
                                '<spring:message code="myhub.label.phone"/>',
                                '사용자 권한',
                                '<spring:message code="myhub.label.crtDt"/>',
                                '비밀번호 수정일',
                                '로그인 실패횟수',
                                '로그인  실패일자'
                            ],
                            colModel: [
                                {name:'userKey', index:'userKey', hidden:true, key:true},
                                {name:'userName', index:'userName', width:10, align:'left'},
                                {name:'email', index:'email', align:'left', width:10},
                                {name:'gender', index:'gender', width:5, align:'center'},  
                                {name:'birthday', index:'birthday', width:10, align:'center'},
                                {name:'phoneNo', index:'phoneNo', width:10, align:'center'},
                                {name:'priv', index:'priv', width:10, align:'center'},
                                {name:'crtDt', index:'crtDt', width:10, align:'center'},
                                {name:'passwordModDt', index:'passwordModDt', width:10, align:'center'},
                                {name:'loginFailCount', index:'loginFailCount', width:10, align:'center'},
                                {name:'loginFailDt', index:'loginFailDt', width:10, align:'center'}
                            ],
                            //width: 1140,
                            height: 500,            // 세로높이
                            sortname: 'crtDt',       // 정렬컬럼
                            sortorder: 'asc',       // 정렬순서
                            sortable: true,     // 컬럼 순서 변경
                            multiselect: true,  // 로우 다중 선택
                            shrinkToFit: true,  // 컬럼 넓이로만 width 설정
                            scrollOffset: 0,    // 우측 스크롤 여부
                            autowidth: true,           // width와 동시에 사용 안됨
                            viewrecords: true,  // records의 View여부
                            gridview: false,     // 처리속도 향상 ==> treeGrid, subGrid, afterInsertRow(event)와 동시 사용불가
                            scroll: 0,      // 휠 페이징 사용 1
                            recordpos: "right",     // 우측좌측 기준변경 records 카운트의 위치 설정
                            pager: "gridPager",             // 하단 페이지처리 selector
                            rowList: [10, 20, 30],           // 한번에 가져오는 row개수
                            loadtext: 'Data Loading From Server',     // 로드 되는 Text 문구
                            rowNum: 10,         // 최초 가져올 row 수
                            emptyrecords: '조회된 데이터가 존재하지 않습니다.',     // 데이터 없을시 표시 
                            jsonReader: {     
                                page: 'resultData.page',
                                total: 'resultData.total',
                                records: 'resultData.records',
                                root: 'resultData.list',
                                repeatitems: false,
                                id: 'resultData.list.userKey'
                            },
                            loadBeforeSend: function(xhr, settings) {
                            	MyHubApp.jqgrid.gridXhr = xhr;
                            },
                            loadError: function(xhr, status, err) {
                            	if(xhr.status === 0 || xhr.statusText === 'abort') {
                                    return false;
                                }
                                alert(xhr.statusText);
                            },
                            loadComplete: function(data) {
                            	// 서버 에러 발생 케이스
                                var resultCd = data.resultCd;
                                if (resultCd === commonObj.constants.result.FAIL) {
                                    alert(data.resultMsg);
                                    return false;
                                }
                            },
                            afterInsertRow: function(rowid, aData) {
                            	if (aData.gender === 'M') {
                            		$('#gridList').setCell(rowid, 'gender', '<spring:message code="myhub.label.gender.male"/>');
                            	} else {
                            		$('#gridList').setCell(rowid, 'gender', '<spring:message code="myhub.label.gender.female"/>');
                            	}
                            	
                            	$('#gridList').setCell(rowid, 'birthday', commonObj.data.util.getBirthDay(aData.birthday, '.'));
                            	$('#gridList').setCell(rowid, 'phoneNo', commonObj.data.util.getMoblPhoneNo(aData.phoneNo, '-'));
                            	$('#gridList').setCell(rowid, 'crtDt', commonObj.date.timestampToDate(aData.crtDt));
                            	$('#gridList').setCell(rowid, 'passwordModDt', commonObj.date.timestampToDate(aData.passwordModDt));
                            	$('#gridList').setCell(rowid, 'loginFailDt', commonObj.date.timestampToDate(aData.loginFailDt));
                            },
                            onCellSelect: function(rowid, iCol, cellcontent, e) {
                                
                            },
                            onSortCol:  function() {
                            	MyHubApp.jqgrid.abort();
                            	$('#gridList').setGridParam({
                                    url: commonObj.config.contextPath.concat('/admin/userManage/getUserList'),
                                    datatype: 'json',
                                    page : 0,
                                    postData: {
                                        gender: $('#gender').val(),
                                        searchType: $('#searchType').val(),
                                        searchWord: $.trim($('#searchWord').val())
                                    }
                                }).trigger("reloadGrid");
                            }
                        });
                	    MyHubApp.jqgrid.search();
                	},
                	
                	// abort(xhr)
                	abort: function() {
                		if(MyHubApp.jqgrid.gridXhr) {
                			MyHubApp.jqgrid.gridXhr.abort();
                			MyHubApp.jqgrid.gridXhr = null;
                        }
                	},
                	
                	// 검색
                	search: function() {
                		this.abort();
                        $('#gridList').setGridParam({
                            url: commonObj.config.contextPath.concat('/admin/userManage/getUserList'),
                            datatype: 'json',
                            page : 0,
                            postData: {
                            	gender: $('#gender').val(),
                                searchType: $('#searchType').val(),
                                searchWord: $.trim($('#searchWord').val())
                            }
                        }).trigger("reloadGrid");
                	}
                },
                
                data: {
                    init: function() {
                        
                    }
                },
                
                event: {
                    init: function() {
                        // 검색
                    	$('#btnUserSearch').on('click', function() {
                    	    MyHubApp.jqgrid.search();
                        });
                        
                    	$('#searchWord').on('keydown', function(e) {
                    		if (e.keyCode === 13) {
                    			MyHubApp.jqgrid.search();	
                    		}
                        });
                    	
                    	// 성별 변경
                    	$('#gender').on('change', function() {
                    		MyHubApp.jqgrid.search();
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
                    <form class="form-inline" role="form" onsubmit="return false;">
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
                        <button class="btn btn-primary" id="btnUserSearch"><spring:message code="myhub.label.search"/></button>
                    </form>
                </div>
                <!-- /search area -->
                <br>
                
                <!-- grid area -->
                <div>
	                <table id="gridList"></table>
	                <div id="gridPager"></div>
                </div>
                <!-- /grid area -->
                
                <!-- action area -->
                <div align="right">
                    <button class="btn btn-info" id="btnChangeAuth">권한변경</button>
                    <button class="btn btn-info" id="btnLockChange">계정 잠금 해제</button>
                </div>
                <!-- /action area -->
                
                <!-- footer -->
                
                <!-- /footer -->
            </div>
        </div>
        
        <!-- common html include -->
        <%@ include file="/WEB-INF/views/jsp/common/include/commonHtml.jsp" %>
        
        <!-- common js include -->
        
    </body>
</html>