package kr.co.myhub.app.admin.user.domain.vo;

import java.util.List;

import kr.co.myhub.appframework.vo.CommonListVo;

/**
 * 
 * file   : UserVoList.java
 * date   : 2014. 7. 11.
 * author : jmpark
 * content: 유저 목록 결과 Vo
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 11.   kbtapjm     최초생성
 */
public class UserVoList extends CommonListVo {
   
    /* 유저목록 */
    private List<UserVo> list;

    public void setList(List<UserVo> list) {
        this.list = list;
    }

    public List<UserVo> getList() {
        return list;
    }
}
