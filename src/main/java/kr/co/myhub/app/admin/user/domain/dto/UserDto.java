package kr.co.myhub.app.admin.user.domain.dto;

import kr.co.myhub.appframework.dto.CommonDto;

/**
 * 
 * file   : UserDto.java
 * date   : 2014. 7. 3.
 * author : jmpark
 * content: 유저 DTO
 * ref:
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 3.   kbtapjm     최초생성
 */
public class UserDto extends CommonDto {
    
    /* 성별  */
    private String gender;
    
    /* 검색타입  */
    private String searchType;
    
    /* 검색어  */
    private String searchWord;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
