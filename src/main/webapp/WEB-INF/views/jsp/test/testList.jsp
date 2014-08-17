<%@ include file="/WEB-INF/views/jsp/common/include/taglibs.jsp" %>

<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>

<!doctype html>
<html>
    <head>
        <title>Test</title>
        
        <script type="text/javascript">
        
            var MyHubApp = {
                pageInit: function() {
                    'use strict';
                    
                    // data init
                    this.data.init();
                    
                    // event init
                    this.event.init();
                },
                
                data: {
                    init: function() {
                    
                    },
                    
                    setScheduleStart: function() {
                        var url = commonObj.config.contextPath.concat('/test/setScheduleStart');
                        var pars = '';
                            
                        commonObj.data.ajax(url, {pars: pars, async: false, 
                            onsucc: function(res) {
                                if (res.resultCd === commonObj.constants.result.FAIL) {
                                    commBootObj.alertModalMsg(res.resultMsg);
                                    return false;   
                                }
                                $('#result').html(JSON.stringify(res))
                            },
                            onerr: function(res) {
                                commBootObj.alertModalMsg('<spring:message code="myhub.error.common.fail"/>');
                            }
                        });
                    }
                },
                
                event: {
                    init: function() {
                        // 스케쥴 시작
                        $('#btnScheduleStart').on('click', function() {
                        	MyHubApp.data.setScheduleStart();
                        });
                        
                        // 스케쥴 종료
                        $('#btnScheduleEnd').on('click', function() {
                            
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
                <!-- label -->
                <blockquote>
                    <p>Test Application</p>
                </blockquote>
                <!-- /label -->
                
                <!-- form -->
                <form class="form-horizontal" id="frmInfo" name="frmInfo" onsubmit="return false;">
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-primary" id="btnScheduleStart">스케쥴 시작</button>
                            <button class="btn btn-primary" id="btnScheduleEnd">스케쥴 종료</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="birthday" class="col-sm-2 control-label">Result</label>
                        <div class="col-sm-3">
                            <p class="form-control-static" id="result"></p>
                        </div>
                    </div>
                </form>
                <!-- /form -->
    </body>
</html>