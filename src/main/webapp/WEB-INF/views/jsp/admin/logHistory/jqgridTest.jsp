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
                            datatype: 'local',
                            colNames: [
                                '사용여부',
                                'X축',
                                'Y축',
                                '폰트',
                                '크기',
                                '굵게',
                                '언더라인'
                            ],
                            colModel: [
                                {name:'useYn', index:'useYn', width:10, editable:true , align:"center"
                                    ,edittype:"checkbox", editoptions: {value:"Y:N"}, formatter:"checkbox", formatoptions: { disabled: false},  sortable:false},
                                {name:'xPos', index:'xPos', width:10, align:'center', editable: true, sortable:false},
                                {name:'yPos', index:'yPos', width:10, align:'center', editable: true, sortable:false},
                                {name:'fontText', index:'fontText', width:10, editable:true, edittype:'select', align:'center',
                                      editoptions:{value:MyHubApp.data.getComboData()}, sortable:false  
                                },
                                {name:'fontSize', index:'fontSize', width:10, align:'center', editable: true, sortable:false},
                                {name:'fontType', index:'fontType', width:20, editable: true , align:"center"
                                    ,edittype:"checkbox", editoptions: {value:"Y:N"}, formatter:"checkbox", formatoptions: { disabled: false}, sortable:false},
                                {name:'under', index:'under', width:20, editable: true , align:"center"
                                    ,edittype:"checkbox", editoptions: {value:"Y:N"}, formatter:"checkbox", formatoptions: { disabled: false}, sortable:false}
                            ],
                            height: 500,            // 세로높이
                            sortable: false,     // 컬럼 순서 변경
                            multiselect: false,  // 로우 다중 선택
                            shrinkToFit: true,  // 컬럼 넓이로만 width 설정
                            scrollOffset: 0,    // 우측 스크롤 여부
                            autowidth: true,           // width와 동시에 사용 안됨
                            viewrecords: true,  // records의 View여부
                            scroll: 0,      // 휠 페이징 사용 1
                            pager: "gridPager",             // 하단 페이지처리 selector
                            emptyrecords: '조회된 데이터가 존재하지 않습니다.',     // 데이터 없을시 표시 
                            rownumbers: true,   // 로우 번호
                            pgbuttons : false,
                            loadComplete: function(data) {
                                /* // 모든 그리드 항목을 수정모드로 변경처리
                                var gridData = $('#gridList').jqGrid('getDataIDs');
                                for(var i = 0; i < gridData.length; i++) {
                                    $('#gridList').jqGrid('editRow', gridData[i], true);
                                } */
                            },
                            afterInsertRow: function(rowid, aData) {
                                // 편집모드로 변환
                                $('#gridList').jqGrid('editRow', rowid, true);
                            },
                            onSelectRow: function(id){
                                /* $('#gridList').jqGrid('editRow',id,true);
                                
                                if(id && id!==lastsel2){
                                    jQuery('#rowed5').jqGrid('restoreRow',lastsel2);
                                    jQuery('#rowed5').jqGrid('editRow',id,true);
                                    lastsel2=id;
                                } */
                            }                              
                        });
                    
                    }
                },
                
                data: {
                    init: function() {
                        this.getDefaultData();
                    },
                    
                    // 디폴트 값 가져오기
                    getDefaultData: function() {
                        var url = commonObj.config.contextPath.concat('/admin/logHistory/getDefaultData');
                        var pars = '';
                            
                        commonObj.data.ajax(url, {pars: pars, async: false, 
                            onsucc: function(res) {
                                if (res.resultCd === commonObj.constants.result.FAIL) {
                                    commBootObj.alertModalMsg(res.resultMsg);
                                    return false;   
                                }
                                MyHubApp.data.setDefaultData(res.resultData);
                            },
                            onerr: function(res) {
                                commBootObj.alertModalMsg('<spring:message code="myhub.error.common.fail"/>');
                            }
                        });
                    },
                    
                    // 그리드에 추가
                    setDefaultData: function(data) {
                        var row = 0;
                        for (var i = 0; i < data.length; i++) {
                            row = $('#gridList').jqGrid('getDataIDs').length + 1;
                            $('#gridList').jqGrid('addRowData', row, data[i]);
                        }
                    },
                    
                    getComboData: function() {
                        return '돋움체:돋움체;바탕체:바탕체';
                    },
                    
                    save: function() {
                        // 모든 그리드 항목을 수정모드로 변경처리
                        
                        // 그리드가 편집모드이기 때문에 모든 데이터를 저장
                        var selRows = $('#gridList').getDataIDs();
                        var colModel = $('#gridList').getGridParam().colModel;
                        
                        var gridData = [];
                         
                        for(var i = 0; i < selRows.length; i++) {
                            for(var j = 1; j < colModel.length; j++) {
                                
                                log(i + ' >>  '+ j);
                                $('#gridList').jqGrid('saveCell', selRows[i], j);
                            }
                           
                            var rowData = $('#gridList').getRowData(selRows[i]);
                            gridData.push(rowData);
                        }
                        
                        log(gridData);
                        
                        /*  var gridData2 = $('#gridList').jqGrid('getDataIDs');
                        for(var i = 0; i < gridData2.length; i++) {
                            $('#gridList').jqGrid('restoreRow', gridData2[i], true);
                        }
                         */
                        
                    }
                },
                
                event: {
                    init: function() {
                        // 저장
                        $('#btnSave').on('click', function() {
                            MyHubApp.data.save();
                        });
                        
                        // 닫기
                        $('#btnClose').on('click', function() {
                            window.close();
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
                
                <!-- grid area -->
                <div>
                    <table id="gridList"></table>
                    <div id="gridPager"></div>
                </div>
                <!-- /grid area -->
                <br>
                
                <!-- action area -->
                <div align="right">
                    <button class="btn btn-primary" id="btnSave">저장</button>
                    <button class="btn btn-default" id="btnClose">닫기</button>
                </div>
                <!-- /action area -->
        
    </body>
</html>