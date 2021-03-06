package uti.sample.controller;
 
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import uti.common.common.CommandMap;
import uti.sample.service.SampleService;
 
@Controller
public class SampleController {
    Logger log = Logger.getLogger(this.getClass());
     
    @Resource(name="sampleService")
    private SampleService sampleService;
     
    @RequestMapping(value="/sample/openMainPage.do")
    public ModelAndView openSampleMainPage(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/mainPage"); //화면에 보여줄 jsp파일
         
//        List<Map<String,Object>> list = sampleService.selectMainPage(commandMap.getMap());
//        mv.addObject("list", list);
         
        return mv;
    }
//    
//    @RequestMapping(value="/sample/testMapArgumentResolver.do")
//    public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("");
//         
//        if(commandMap.isEmpty() == false){
//            Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
//            Entry<String,Object> entry = null;
//            while(iterator.hasNext()){
//                entry = iterator.next();
//                log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
//            }
//        }
//        return mv;
//    }
//
//    @RequestMapping(value="/sample/openBoardWrite.do")
//    public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("/sample/boardWrite");
//         
//        return mv;
//    }
//    
//    @RequestMapping(value="/sample/insertBoard.do")
//    public ModelAndView insertBoard(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("redirect:/sample/openMainPage.do");
//         
//        sampleService.insertBoard(commandMap.getMap());
//         
//        return mv;
//    }
//
//    @RequestMapping(value="/sample/openBoardDetail.do")
//    public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("/sample/boardDetail");
//         
//        Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
//        mv.addObject("map", map);
//         
//        return mv;
//    }
//
//    @RequestMapping(value="/sample/openBoardUpdate.do")
//    public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("/sample/boardUpdate");
//         
//        Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
//        mv.addObject("map", map);
//         
//        return mv;
//    }
//     
//    @RequestMapping(value="/sample/updateBoard.do")
//    public ModelAndView updateBoard(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
//         
//        sampleService.updateBoard(commandMap.getMap());
//         
//        mv.addObject("IDX", commandMap.get("IDX"));
//        return mv;
//    }
//    
//    @RequestMapping(value="/sample/deleteBoard.do")
//    public ModelAndView deleteBoard(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("redirect:/sample/openMainPage.do");
//         
//        sampleService.deleteBoard(commandMap.getMap());
//         
//        return mv;
//    }
    
    @RequestMapping(value="/dataAccess.do",produces = "application/text; charset=utf8")
    @ResponseBody
     public  String dataAccess(@RequestParam("requestUrl") String url,@RequestParam("firstName") String firstName, CommandMap commandMap) throws Exception{
    	String returnData = sampleService.dataAccess(commandMap.getMap(),url,firstName); 
    	return returnData;
    }
    
    @RequestMapping(value="/selectComment.do",produces = "application/text; charset=utf8")
    @ResponseBody
     public String selectComment(@RequestParam("TITLE") String TITLE,@RequestParam("XY") String XY, CommandMap commandMap) throws Exception{
    	List<Map<String, Object>> data = sampleService.selectComment(commandMap.getMap(),TITLE,XY);
		
		JSONArray json_arr=new JSONArray();
        for (Map<String, Object> map : data) {
            JSONObject json_obj=new JSONObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                try {
                    json_obj.put(key,value);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }                           
            }
            json_arr.put(json_obj);
        }
        
    	return json_arr.toString();
    }
    

    @RequestMapping(value="/insertComment.do",produces = "application/text; charset=utf8")
    @ResponseBody
     public String insertComment(@RequestParam("TITLE") String TITLE,@RequestParam("XY") String XY, @RequestParam("CREA_NAME")String CREA_NAME, @RequestParam("COMMENT")String COMMENT, @RequestParam("STAR_POINT")Integer STAR_POINT, CommandMap commandMap) throws Exception{
    	List<Map<String, Object>> data = sampleService.insertComment(commandMap.getMap(),TITLE,XY,CREA_NAME,COMMENT,STAR_POINT);
		
		JSONArray json_arr=new JSONArray();
        for (Map<String, Object> map : data) {
            JSONObject json_obj=new JSONObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                try {
                    json_obj.put(key,value);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }                           
            }
            json_arr.put(json_obj);
        }
        
    	return json_arr.toString();
    }
    
}