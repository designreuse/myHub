<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.loghistory.list"/></title>
        
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
                                '이메일',
                                '아이피주소',
                                '로그타입',
                                '로그일자',
                                '세션아이디'
                            ],
                            colModel: [
                                {name:'logHistoryKey', index:'logHistoryKey', hidden:true, key:true},
                                {name:'email', index:'email', width:10, align:'left'},
                                {name:'ipAddress', index:'ipAddress', width:10, align:'center'},
                                {name:'logType', index:'logType', width:10, align:'center'},
                                {name:'logDate', index:'logDate', width:10, align:'center'},
                                {name:'sessionId', index:'sessionId', width:20, align:'center'}
                     
                            ],
                            //width: 1140,
                            height: 500,            // 세로높이
                            sortname: 'logDate',       // 정렬컬럼
                            sortorder: 'desc',       // 정렬순서
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
                            rowList: [100, 200, 300],           // 한번에 가져오는 row개수
                            loadtext: 'Data Loading From Server',     // 로드 되는 Text 문구
                            rowNum: 100,         // 최초 가져올 row 수
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
                                var resultCd = data.resultCd;
                                if (resultCd === commonObj.constants.result.FAIL) {
                                    alert(data.resultMsg);
                                    return false;
                                }
                            },
                            afterInsertRow: function(rowid, aData) {
                            	$('#gridList').setCell(rowid, 'logDate', commonObj.date.timestampToDate(aData.logDate));
                            	
                            	if (aData.logType === 'I') {
                            		$('#gridList').setCell(rowid, 'logType', '<spring:message code="myhub.label.login"/>');
                            	} else {
                            		$('#gridList').setCell(rowid, 'logType', '<spring:message code="myhub.label.logout"/>');
                            	}
                            },
                            onCellSelect: function(rowid, iCol, cellcontent, e) {
                                
                            },
                            onSortCol: MyHubApp.jqgrid.search 
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
                    	MyHubApp.jqgrid.abort();
                        $('#gridList').setGridParam({
                            url: commonObj.config.contextPath.concat('/admin/logHistory/getLogHistoryList'),
                            datatype: 'json',
                            page : 0,
                            postData: {
                                logType: $('#logType').val(),
                                searchType: $('#searchType').val(),
                                searchWord: $.trim($('#searchWord').val())
                            }
                        }).trigger("reloadGrid");
                    }
                },
                
                data: {
                    init: function() {
                        
                    },
                    
                    logHistoryDelete: function() {
                    	
                    }
                },
                
                event: {
                    init: function() {
                        // 검색
                        $('#btnLogSearch').on('click', function() {
                            MyHubApp.jqgrid.search();
                        });
                        
                        // 검색
                        $('#searchWord').on('keydown', function(e) {
                            if (e.keyCode === 13) {
                                MyHubApp.jqgrid.search();   
                            }
                        });
                        
                        // 로그타입
                        $('#logType').on('change', function() {
                            MyHubApp.jqgrid.search();
                        });
                        
                        // 이력삭제
                        $('#btnDelete').on('change', function() {
                            MyHubApp.data.logHistoryDelete();
                        });
                    }
                }
            };
        
        </script>
        
    </head>
    <body>
                
                <!-- label -->
                <blockquote>
                    <p><spring:message code="myhub.label.loghistory.list"/></p>
                </blockquote>
                <!-- /label -->
                
                <!-- search area -->
                <div align="right">
                    <form class="form-inline" role="form" onsubmit="return false;">
                        <div class="form-group">
                            <select class="form-control" id="logType" name="logType">
                                <option value=""><spring:message code="myhub.label.select"/></option>
                                <option value="I"><spring:message code="myhub.label.login"/></option>
                                <option value="O"><spring:message code="myhub.label.logout"/></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" id="searchType" name="searchType">
                                <option value=""><spring:message code="myhub.label.select"/></option>
                                <option value="email"><spring:message code="myhub.label.email"/></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="searchWord" id="searchWord">
                        </div>
                        <button class="btn btn-primary" id="btnLogSearch"><spring:message code="myhub.label.search"/></button>
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
                <br>
                
                <!-- action area -->
                <div align="right">
                    <button class="btn btn-info" id="btnDelete"><spring:message code="myhub.label.delete" /></button>
                </div>
                <!-- /action area -->
        
    </body>
</html>