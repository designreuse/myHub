package kr.co.myhub.app.admin.user.domain.vo;

import java.util.List;

import kr.co.myhub.app.user.domain.User;

/**
 * 
 * file   : UserVo.java
 * date   : 2014. 7. 7.
 * author : jmpark
 * content: 유저정보 VO 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 7.   kbtapjm     최초생성
 */
public class UserVo {
    
    /* 총페이지 */
    private int total;
    
    /* 총카운트 */
    private int records;
    
    /* 페이지 */
    private int page;
    
    /* 유저목록 */
    private List<User> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
