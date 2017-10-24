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
     
    @Override
    public List<Map<String, Object>> selectMainPage(Map<String, Object> map) throws Exception {
        return sampleDAO.selectMainPage(map);
    }
    
    @Override
    public void insertBoard(Map<String, Object> map) throws Exception {
        sampleDAO.insertBoard(map);
    }

    @Override
    public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
        sampleDAO.updateHitCnt(map);
        Map<String, Object> resultMap = sampleDAO.selectBoardDetail(map);
        return resultMap;
    }

    @Override
    public void updateBoard(Map<String, Object> map) throws Exception{
        sampleDAO.updateBoard(map);
    }

    @Override
    public void deleteBoard(Map<String, Object> map) throws Exception {
        sampleDAO.deleteBoard(map);
    }

	@Override
	public List<Map<String, Object>> updateDB(Map<String, Object> map) throws Exception {
		//DB업데이트 호출하고 메인페이지 리턴
		sampleDAO.updateDB(map);//이거 제외하고 selectMainPage와 동일
		return sampleDAO.selectMainPage(map);
	}

	@Override
	public String dataAccess(Map<String, Object> map,String requestUrl,String firstName) throws XmlPullParserException, IOException {
		return sampleDAO.dataAccess(map,requestUrl,firstName);
	}

}

