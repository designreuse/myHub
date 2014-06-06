/**
 * 공통 Object 정보
 */
var commonObj = {
	config: {
		siteName: 'MyHub',
		version: '1.0',
		contextPath: '/myhub'
	},
	
	// 데이터 관련
	data : {
		
		// Cookie 
		cookie: {
			
			// Cookie 설정
			set: function(name, value, expiredays) {
				
				// javascript cookie
		        /* 
		        var today = new Date();
		        today.setDate( today.getDate() + expiredays );
		        document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + today.toGMTString() + ";";
		        */
				
				// jqeury cookie 사용
				$.cookie(name, value, { expires: expiredays, path: '/' });
			},
			
			// Cookie 얻기
			get: function(name) {
				
				// javascript cookie
				/*
		        var cook = document.cookie + ";";
		        var idx =  cook.indexOf(name, 0);
		        var val = "", begin, end;
		        if(idx != -1)  {
		            cook = cook.substring(idx, cook.length);
		            begin = cook.indexOf("=", 0) + 1;
		            end = cook.indexOf(";", begin);
		            val = unescape(cook.substring(begin, end));
		        }
		        */
		        
				// jqeury cookie 사용
		        var val = $.cookie(name);
		        return val;
			},
			
			// Cookie 삭제
			del: function(name, value, expiredays) {
				
				//commonObj.data.cookie.set(name, value, expiredays);
				
				// jqeury cookie 사용 
	            $.removeCookie(name);
			}
		},
		
		// ajax TODO: AJAX 모듈 정리
		ajax: function(url, _config) {
			
			try {
                if (!_config || !url) {
                	throw "commonObj.data.ajax configuration error!\ncheck your script code!";
                }

                if (!_config.method) _config.method = "post";
                if (_config.async == undefined) _config.async = true;
                if (_config.contentType == undefined) _config.contentType = "application/x-www-form-urlencoded";
                if (!_config.data) _config.data = "json";

                jQuery.ajax({
                    url: url,
                    data: _config.pars,
                    type: _config.method,
                    async: _config.async,
                    contentType: _config.contentType,
                    dataType: _config.data,
                    success: function (res) {
                    	var result = res;
                    	
                    	
	                    _config.onsucc( result );
                    },
                    error: function (res) {
                    	
                    	if ( _config.onerr != undefined ){
                    		_config.onerr ( res.responseText );	
                    	} else {
                    		alert("onError : " + res.responseText);
                    		
                    	}
                    },
                    timeout: function (res) {
                        alert("onTimeout : " + res.responseText);
                    }
                });
            } catch (e) { alert(e); }
		},
		
		// 데이터 검증
		vaild: {
			email: function(_val) {
				var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
				
				if (!_val.match(regExp)) {
					return false;
				}
				
				return true;
			},
			
			number: function(_val) {
				var regExp =  /^[0-9]+$/;
				
				if (!_val.match(regExp)) {
					return false;
				}
				
				return true;
			}
		}
	},
	
	// 날짜 처리 관련
	date: {
		
	},
	
	// 상수모음(클라이언트에서의 하드 코딩 방지)
	constants : {
		result : {
			SUCCESS: '0000',
			FAIL: '9999'
		}
	}
	
	
};

// String Object extendable


/**
 *로그 출력
 *
 *- parameter
 * str : 출력할 문자열
 */
function log(str) {
	if(typeof console === "undefined") {
        return false;
    };
    
    console.log(str);
}

/**
 * 부트스트랩 관련 공통
 */
var commBootObj = {
	alerts : function(_conf) {
		if (!_conf) return false;
		
		var defaultConf = {
			renderId: '',
			type: this.constants.alerts.WARNING,
			msg: ''
		};
		
		var prop = $.extend(true, {}, defaultConf, _conf); 
		
		var html = '';
		html = html.concat('<div class="alert alert-dismissable">');
		html = html.concat('<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>');
		html = html.concat('<strong id="typeNm"></strong>&nbsp;'.concat(prop.msg));
		html = html.concat('</div>');
		
		var renderObj = $('#'.concat(prop.renderId));
		
		renderObj.html(html);
		
		renderObj.find('#typeNm').text(prop.type.concat('!'));
		switch(prop.type) {
		case this.constants.alerts.SUCCESS:
			renderObj.find('.alert').addClass('alert-success');
			break;
		case this.constants.alerts.INFO:
			renderObj.find('.alert').addClass('alert-info');
			break;
		case this.constants.alerts.WARNING:
			renderObj.find('.alert').addClass('alert-warning');
			break;
		case this.constants.alerts.DANGER:
			renderObj.find('.alert').addClass('alert-danger');
			break;
		}
	},
	
	// 모달 팝업
	alertModalMsg: function(msg, buttonOpts) {
		// 메세지 세팅
	    $('#notiMsg').html(msg);
	    $("#notiModal").modal({
	        backdrop: 'static',
	        keyboard: true,
	        show: true
	    });
	    
	    if (!buttonOpts) {
	    	$('#btnThird').show();
	    	return false;
	    }
	    
	    $('#btnFirst').hide();
	    $('#btnSecond').hide();
	    $('#btnThird').hide();
	    
	    /* 버튼 옵션 설정*/
	    if(buttonOpts.first) {
	    	$('#btnFirst').show();
	    	
	    	if (buttonOpts.first.text) $('#btnFirst').text(buttonOpts.first.text);
	    	if (buttonOpts.first.fn) $('#btnFirst').on('click', buttonOpts.first.fn);
	    }
	    
	    if(buttonOpts.second) {
	    	$('#btnSecond').show();
	    	
	    	if (buttonOpts.second.text) $('#btnSecond').text(buttonOpts.second.text);
	    	if (buttonOpts.second.fn) $('#btnSecond').on('click', buttonOpts.second.fn);
	    }
	    
	    if(buttonOpts.third) {
	    	$('#btnThird').show();
	    	
	    	if (buttonOpts.third.text) $('#btnThird').text(buttonOpts.third.text);
	    	if (buttonOpts.third.fn) $('#btnThird').on('click', buttonOpts.third.fn);
	    }
	},
	
	constants: {
		alerts: {
			SUCCESS : 'Success',
			INFO : 'Info',
			WARNING : 'Warning',
			DANGER : 'Danger'
		}
	}
};
