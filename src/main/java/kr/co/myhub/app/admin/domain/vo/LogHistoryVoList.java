package kr.co.myhub.app.admin.domain.vo;

import java.util.List;

import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.appframework.vo.CommonListVo;

/**
 * 
 * file   : LogHistoryVoList.java
 * date   : 2014. 8. 30.
 * author : jmpark
 * content: 로그이력 결과 VO
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 8. 30.   jmpark     최초생성
 */
public class LogHistoryVoList extends CommonListVo {

    /* 로그 목록 */
    private List<LogHistory> list;

    public List<LogHistory> getList() {
        return list;
    }

    public void setList(List<LogHistory> list) {
        this.list = list;
    }
    
}
