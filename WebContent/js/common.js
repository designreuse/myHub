/**
 * 공통 Object 정보
 */
var commonObj = {
	config: {
		siteName: "MyHub",
		version: "1.0",	
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
		
		// ajax
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
		}
	},
	
	// 날짜 처리 관련
	date: {
		
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
