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
	if(typeof console == "undefined") {
        return false;
    };
    
    console.log(str);
}
