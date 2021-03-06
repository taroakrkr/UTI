package uti.sample.service;
 
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import uti.sample.dao.SampleDAO;

@Service("sampleService")
public class SampleServiceImpl implements SampleService{
    Logger log = Logger.getLogger(this.getClass());
     
    @Resource(name="sampleDAO")
    private SampleDAO sampleDAO;
     
//    @Override
//    public List<Map<String, Object>> selectMainPage(Map<String, Object> map) throws Exception {
//        return sampleDAO.selectMainPage(map);
//    }
//    
//    @Override
//    public void insertBoard(Map<String, Object> map) throws Exception {
//        sampleDAO.insertBoard(map);
//    }
//
//    @Override
//    public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
//        sampleDAO.updateHitCnt(map);
//        Map<String, Object> resultMap = sampleDAO.selectBoardDetail(map);
//        return resultMap;
//    }
//
//    @Override
//    public void updateBoard(Map<String, Object> map) throws Exception{
//        sampleDAO.updateBoard(map);
//    }
//
//    @Override
//    public void deleteBoard(Map<String, Object> map) throws Exception {
//        sampleDAO.deleteBoard(map);
//    }

	@Override
	public String dataAccess(Map<String, Object> map,String requestUrl,String firstName) throws XmlPullParserException, IOException {
		return sampleDAO.dataAccess(map,requestUrl,firstName);
	}
	
	@Override
	public List<Map<String, Object>> selectComment(Map<String, Object> map,String TITLE,String XY) throws Exception {

		return sampleDAO.selectComment(map,TITLE,XY);
	}

	@Override
	public List<Map<String, Object>> insertComment(Map<String, Object> map, String TITLE, String XY, String CREA_NAME, String COMMENT, Integer STAR_POINT) throws Exception {
		sampleDAO.insertComment(map,TITLE,XY,CREA_NAME,COMMENT,STAR_POINT);
		return sampleDAO.selectComment(map,TITLE,XY);
	}

}

