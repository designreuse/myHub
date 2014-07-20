package kr.co.myhub.appframework.vo;

/**
 * 
 * file   : CommonListVo.java
 * date   : 2014. 7. 20.
 * author : jmpark
 * content: jqgrid 공통 VO
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 20.   kbtapjm     최초생성
 */
public class CommonListVo {

    /* 총페이지 */
    private int total;
    
    /* 총카운트 */
    private int records;
    
    /* 페이지 */
    private int page;

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
}
