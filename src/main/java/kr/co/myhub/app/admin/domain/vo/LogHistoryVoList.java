package kr.co.myhub.app.admin.domain.vo;

import java.util.List;

import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.appframework.vo.CommonListVo;

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
