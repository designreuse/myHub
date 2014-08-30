<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title>Connected User</title>
        
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
                                '<spring:message code="myhub.label.email"/>',
                                '권한',
                                '로그인 일자',
                                '마지막 요청 일자',
                                '세션아이디',
                            ],
                            colModel: [
                                {name:'userName', index:'userName', width:1, align:'center', sortable:false},
                                {name:'authorities', index:'authorities', width:1, align:'center', sortable:false},
                                {name:'loginDt', index:'loginDt', width:1, align:'center', sortable:false},
                                {name:'lastRequest', index:'lastRequest', width:1, align:'center', sortable:false},
                                {name:'sessionId', index:'sessionId', width:1, align:'center', sortable:false}
                            ],
                            //width: 1140,
                            height: 250,            // 세로높이
                            sortname: 'logingDt',       // 정렬컬럼
                            sortorder: 'asc',       // 정렬순서
                            sortable: true,     // 컬럼 순서 변경
                            multiselect: false,  // 로우 다중 선택
                            shrinkToFit: true,  // 컬럼 넓이로만 width 설정
                            scrollOffset: 0,    // 우측 스크롤 여부
                            autowidth: true,           // width와 동시에 사용 안됨
                            viewrecords: true,  // records의 View여부
                            gridview: false,     // 처리속도 향상 ==> treeGrid, subGrid, afterInsertRow(event)와 동시 사용불가
                            scroll: 0,      // 휠 페이징 사용 1
                            recordpos: "right",     // 우측좌측 기준변경 records 카운트의 위치 설정
                            loadtext: 'Data Loading From Server',     // 로드 되는 Text 문구
                            emptyrecords: '조회된 데이터가 존재하지 않습니다.',     // 데이터 없을시 표시 
                            rownumbers: true,   // 로우 번호
                            jsonReader: {     
                                page: 'resultData.page',
                                total: 'resultData.total',
                                records: 'resultData.records',
                                root: 'resultData.list',
                                repeatitems: false
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
                    search: function(type) {
                        MyHubApp.jqgrid.abort();
                        $('#gridList').setGridParam({
                            url: commonObj.config.contextPath.concat('/admin/userManage/getActiveUserList'),
                            datatype: 'json',
                            page : type === 'reload' ? $('#gridList').jqGrid('getGridParam','page') : 0,
                            postData: {
                                
                            }
                        }).trigger("reloadGrid");
                    }
                },
                
                data: {
                    init: function() {
                        // init
                    }
                },
                
                event: {
                    init: function() {
                        // 닫기
                        $('#btnClose').on('click', function() {
                            window.close();
                        });
                        
                        // 세션 정보 갱신(5초마다)
                        <security:authorize access="fullyAuthenticated">;
                        window.setInterval('MyHubApp.jqgrid.search();', (5 * 1000));
                        </security:authorize>;
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
        
            <!-- grid area -->
            <div>
                <table id="gridList"></table>
            </div>
            <!-- /grid area -->
            <br>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10" align="center">
                    <button class="btn btn-default" id="btnClose"><spring:message code="myhub.label.close"/></button>
                </div>
            </div>

    </body>
</html>