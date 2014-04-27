package kr.co.myhub.app.common;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 
 * file   : TestJson.java
 * date   : 2014. 4. 14.
 * author : jmpark
 * content: JSON 테스트
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 4. 14.   kbtapjm     최초생성
 */
public class TestJson {

    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JSONArray jsonArray = new JSONArray();
       
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("tr-no", "1");
        jsonArray.add(jsonObj);
        
        jsonObj = new JSONObject();
        jsonObj.put("tr-no", "2");
        jsonArray.add(jsonObj);
        
        JSONObject resultMap = new JSONObject();
        resultMap.put("rCode", "0000");
        resultMap.put("rMsg", "Success");
        resultMap.put("rData", jsonArray);
        
        // Array
        JSONArray resultArray = (JSONArray) resultMap.get("rData");
        System.out.println(resultArray);
        
        Iterator<JSONObject> iterator = resultArray.iterator();
        while (iterator.hasNext()) {
            JSONObject data = iterator.next();
            
            System.out.println(data.get("tr-no"));
        }
    }
}
