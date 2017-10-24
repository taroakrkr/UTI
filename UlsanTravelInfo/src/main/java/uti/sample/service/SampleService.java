package uti.sample.service;
 
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;
 
public interface SampleService {
 
    List<Map<String, Object>> selectMainPage(Map<String, Object> map) throws Exception;
 
    void insertBoard(Map<String, Object> map) throws Exception;

    Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception;

    void updateBoard(Map<String, Object> map) throws Exception;

    void deleteBoard(Map<String, Object> map) throws Exception;

    List<Map<String, Object>> updateDB(Map<String, Object> map) throws Exception;

	String dataAccess(Map<String, Object> map,String requestUrl,String firstName) throws XmlPullParserException, IOException;
}


