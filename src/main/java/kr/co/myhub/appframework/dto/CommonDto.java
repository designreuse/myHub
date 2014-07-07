package kr.co.myhub.appframework.dto;

/**
 * 
 * file   : CommonDto.java
 * date   : 2014. 7. 6.
 * author : jmpark
 * content: 웹요청 공통 DTO
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 6.   kbtapjm     최초생성
 */
public class CommonDto {

    /* ================================ */
    /* jqgrid */
    /* ================================ */
    
    /* 요청 페이지 */
    private int page;
    
    /* 요청 카운트 */
    private int rows;
    
    /* 정렬 컬럼 */
    private String sidx;
    
    /* 정렬 타입(asc, desc) */
    private String sord;
    
    /* ================================ */
    /* cmm */
    /* ================================ */
    
    /* 페이지 시작번호 */
    private int startNo;
    
    /* 페이지 종료번호 */
    private int endNo;
    
    /* 정렬컬럼 이름 */
    private String sortName;
    
    /* 정렬컬럼 타입 */
    private String sortType;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public int getStartNo() {
        return startNo;
    }

    public void setStartNo(int startNo) {
        this.startNo = startNo;
    }

    public int getEndNo() {
        return endNo;
    }

    public void setEndNo(int endNo) {
        this.endNo = endNo;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
    
    /**
     * 페이지 및  데이터 정렬 설정 초기화
     */
    public void setPageInit() {
        if (this.getPage() == 0) this.setPage(1);
        if (this.getRows() == 0) this.setRows(1);
        
        this.setStartNo(((this.getPage() -1 ) * this.getRows()) + 1);
        this.setEndNo(this.getPage() * this.getRows());
        
        this.sortType = this.getSord();
        this.sortName = this.getSidx();
    }
}
