<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title><spring:message code="myhub.label.list"/></title>
        
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
                                '<spring:message code="myhub.label.privillege"/>',
                                '<spring:message code="myhub.label.crtDt"/>',
                                '<spring:message code="myhub.label.uptPwdDt"/>',
                                '<spring:message code="myhub.label.loginFileCnt"/>',
                                '<spring:message code="myhub.label.loginFileDt"/>'
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
                            height: 250,            // 세로높이
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
        
        </script>
        
    </head>
    <body>
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
                                <option value="name"><spring:message code="myhub.label.name"/></option>
                                <option value="email"><spring:message code="myhub.label.email"/></option>
                                <option value="birthday"><spring:message code="myhub.label.birthday"/></option>
                                <option value="phoneNo"><spring:message code="myhub.label.phone"/></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="searchWord" id="searchWord">
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
                <br>
                
                <!-- action area -->
                <div align="right">
                    <button class="btn btn-info" id="btnChangeAuth">권한변경</button>
                    <button class="btn btn-info" id="btnLockChange">계정 잠금 해제</button>
                </div>
                <!-- /action area -->
    </body>
</html>