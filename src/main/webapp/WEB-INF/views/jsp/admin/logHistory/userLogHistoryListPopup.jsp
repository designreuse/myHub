<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.loghistory.list"/></title>
        
        <script type="text/javascript">
        
            var MyHubApp = {
            	userKey: '${userKey}',	
            		
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
                            recordpos: 'right',     // 우측좌측 기준변경 records 카운트의 위치 설정
                            pager: 'gridPager',             // 하단 페이지처리 selector
                            rowList: [100, 200, 300],           // 한번에 가져오는 row개수
                            loadtext: 'Data Loading From Server',     // 로드 되는 Text 문구
                            rowNum: 100,         // 최초 가져올 row 수
                            emptyrecords: '조회된 데이터가 존재하지 않습니다.',     // 데이터 없을시 표시 
                            //caption: '로그이력',
                            jsonReader: {     
                                page: 'resultData.page',
                                total: 'resultData.total',
                                records: 'resultData.records',
                                root: 'resultData.list',
                                repeatitems: false,
                                id: 'resultData.list.userKey'
                            },
                            rownumbers: true,   // 로우 번호
                            pgbuttons: true,    // 하다 다음페이지 버튼 노출 여부
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
                                if (iCol != 1) $('#gridList').setSelection(rowid);
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
                                searchType: 'userKey',
                                searchWord: MyHubApp.userKey
                            }
                        }).trigger('reloadGrid');
                    }
                },
                
                data: {
                    init: function() {
                    	
                    },
                    
                    // 이력삭제
                    logHistoryDelete: function() {
                        var selarrrow = $('#gridList').jqGrid('getGridParam','selarrrow');
                        if (selarrrow.length == 0) {
                            alert('삭제 할 로그를 선택하세요.');
                            return false;
                        }
                        
                        if(!confirm('로그를 삭제 하시겠습니까?')) return false;
                        
                        var paramArr = [];
                        for (var i = 0; i < selarrrow.length; i++) {
                            var rowData = $('#gridList').jqGrid('getRowData', selarrrow[i]);
                            
                            paramArr.push({logHistoryKey: rowData.logHistoryKey});
                        }
                        
                        var url = commonObj.config.contextPath.concat('/admin/logHistory/logHistoryDelete');
                        var pars = JSON.stringify(paramArr);
                            
                        commonObj.data.ajax(url, {pars: pars, async: false, contentType: 'application/json', 
                            onsucc: function(res) {
                                if (res.resultCd === commonObj.constants.result.FAIL) {
                                    commBootObj.alertModalMsg(res.resultMsg);
                                    return false;   
                                }
                                
                                MyHubApp.jqgrid.search();
                            },
                            onerr: function(res) {
                                commBootObj.alertModalMsg('<spring:message code="myhub.error.common.fail"/>');
                            }
                        });
                    },
                    
                    // 로그 전체 삭제
                    logHistoryDeleteAll: function() {
                        if(!confirm('사용자의 로그를 전체 삭제 하시겠습니까?')) return false;
                        
                        var url = commonObj.config.contextPath.concat('/admin/logHistory/logHistoryDeleteAll');
                        var pars = 'userKey='.concat(MyHubApp.userKey);
                            
                        commonObj.data.ajax(url, {pars: pars, async: true, 
                            onsucc: function(res) {
                                if (res.resultCd === commonObj.constants.result.FAIL) {
                                    commBootObj.alertModalMsg(res.resultMsg);
                                    return false;   
                                }
                                
                                MyHubApp.jqgrid.search();
                            },
                            onerr: function(res) {
                                commBootObj.alertModalMsg('<spring:message code="myhub.error.common.fail"/>');
                            }
                        });
                    }
                },
                
                event: {
                    init: function() {
                        // 로그타입
                        $('#logType').on('change', function() {
                            MyHubApp.jqgrid.search();
                        });
                        
                        // 닫기
                        $('#btnClose').on('click', function() {
                           window.close();
                        });
                        
                        // 이력삭제
                        $('#btnDelete').on('click', function() {
                            MyHubApp.data.logHistoryDelete();
                        });
                        
                        // 전체삭제
                        $('#btnDeleteAll').on('click', function() {
                        	MyHubApp.data.logHistoryDeleteAll();
                        });
                    }
                }
            };
        
        </script>
        
    </head>
    <body>
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
                    <button class="btn btn-primary" id="btnDelete"><spring:message code="myhub.label.delete" /></button>
                    <button class="btn btn-danger" id="btnDeleteAll">전체삭제</button>
                    <button class="btn btn-info" id="btnClose"><spring:message code="myhub.label.close" /></button>
                </div>
                <!-- /action area -->
    </body>
</html>