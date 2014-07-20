package kr.co.myhub.app.admin.domain.dto;

import kr.co.myhub.appframework.dto.CommonDto;

/**
 * 
 * file   : LogHistoryDto.java
 * date   : 2014. 7. 19.
 * author : jmpark
 * content: 로그이력 DTO
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 19.   kbtapjm     최초생성
 */
public class LogHistoryDto extends CommonDto {

    /* 로그타입  */
    private String logType;
    
    /* 검색타입  */
    private String searchType;
    
    /* 검색어  */
    private String searchWord;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

}
