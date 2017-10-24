package uti.sample.controller;
 
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
         
        List<Map<String,Object>> list = sampleService.selectMainPage(commandMap.getMap());
        mv.addObject("list", list);
         
        return mv;
    }
    
    @RequestMapping(value="/sample/testMapArgumentResolver.do")
    public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("");
         
        if(commandMap.isEmpty() == false){
            Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
            Entry<String,Object> entry = null;
            while(iterator.hasNext()){
                entry = iterator.next();
                log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
            }
        }
        return mv;
    }

    @RequestMapping(value="/sample/openBoardWrite.do")
    public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardWrite");
         
        return mv;
    }
    
    @RequestMapping(value="/sample/insertBoard.do")
    public ModelAndView insertBoard(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:/sample/openMainPage.do");
         
        sampleService.insertBoard(commandMap.getMap());
         
        return mv;
    }

    @RequestMapping(value="/sample/openBoardDetail.do")
    public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardDetail");
         
        Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
        mv.addObject("map", map);
         
        return mv;
    }

    @RequestMapping(value="/sample/openBoardUpdate.do")
    public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardUpdate");
         
        Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
        mv.addObject("map", map);
         
        return mv;
    }
     
    @RequestMapping(value="/sample/updateBoard.do")
    public ModelAndView updateBoard(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
         
        sampleService.updateBoard(commandMap.getMap());
         
        mv.addObject("IDX", commandMap.get("IDX"));
        return mv;
    }
    
    @RequestMapping(value="/sample/deleteBoard.do")
    public ModelAndView deleteBoard(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:/sample/openMainPage.do");
         
        sampleService.deleteBoard(commandMap.getMap());
         
        return mv;
    }

    
    //DB업데이트
    @RequestMapping(value="/sample/updateDB.do")
    public ModelAndView updateDB(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:/sample/mainPage"); 
         
        List<Map<String,Object>> list = sampleService.updateDB(commandMap.getMap());
        mv.addObject("list", list);
        
        //파싱&업데이트를 하고 메인페이지로 돌아가기
         
        return mv;
    }
    
//    @RequestMapping(value="/sample/utiParser.do", method = RequestMethod.GET)
//    @ResponseBody
//    public String updateInfo(CommandMap commandMap) throws Exception{
//    	return sampleService.dataAccess(commandMap.getMap());
//    }
    
//    @RequestMapping(value="/dataAccess.do")
//    public ModelAndView dataAccess(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("/sample/mainPage"); 
//         
//        String data = sampleService.dataAccess(commandMap.getMap());
//        mv.addObject("data", data);
//
//        
//         
//        return mv;
//    }
    
    @RequestMapping(value="/dataAccess.do",produces = "application/text; charset=utf8")
    @ResponseBody
     public  String dataAccess(@RequestParam("requestUrl") String url,@RequestParam("firstName") String firstName, CommandMap commandMap) throws Exception{
    	String returnData = sampleService.dataAccess(commandMap.getMap(),url,firstName); 
    	return returnData;
    }
    
//    @RequestMapping(value="/dataAccess.do")
//    public String apiParserSearch(String obj) throws Exception {
//    	String PHARM_URL = "http://data.ulsan.go.kr/rest/ulsanscenes/getUlsanscenesList";
//        String KEY = "uXF7aS58LcHItF4VsvqpoHLJ5e7VrJdl%2FeKSmIVu3qai6qZfKR9GOYB4YrH1j7gLrt9UDq7iqaeeRoFBsP%2BR6w%3D%3D";
//        
//        URL url = new URL(PHARM_URL+"?ServiceKey=" + KEY);
// 
//        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        factory.setNamespaceAware(true);
//        XmlPullParser xpp = factory.newPullParser();
//        BufferedInputStream bis = new BufferedInputStream(url.openStream());
//        xpp.setInput(bis, "utf-8");
//        
//        String tag = null;
//        int event_type = xpp.getEventType();
//        
//        ArrayList<UlsanData> list = new ArrayList<UlsanData>();
//        UlsanData temp = new UlsanData("","","","","","","","");
//
//        while (event_type != XmlPullParser.END_DOCUMENT) {
//            if (event_type == XmlPullParser.START_TAG) {
//                tag = xpp.getName();
////                System.out.println(tag.toString());
//            } else if (event_type == XmlPullParser.TEXT) {
//                /**
//                 * 태그이름
//                 */
//            	switch(tag.toString()){
//            	case "ulsanscenesTitle" :
//            		temp.title = xpp.getText();
////            		System.out.println(temp.title);
//            		break;
//            	case "ulsanscenesAddr" :
//            		temp.addr = xpp.getText();
//            		break;
//            	case "ulsanscenesXpos" :
//            		temp.xpos = xpp.getText();
//            		break;
//            	case "ulsanscenesYpos" :
//            		temp.ypos = xpp.getText();
//            		break;
//            	case "ulsanscenesInfo" :
//            		temp.info = xpp.getText();
//            		break;
//            	case "ulsanscenesTraffic" :
//            		temp.traffic = xpp.getText();
//            		break;
//            	case "ulsaanscenesTel" :
//            		temp.tel = xpp.getText();
//            		break;
//            	case "ulsaanscenesRegDt" :
//            		temp.regdt = xpp.getText();
//            		break;
//            	default :
//            		break;
//            	}
//            } else if (event_type == XmlPullParser.END_TAG) {
//                tag = xpp.getName();
////                System.out.println("/"+tag.toString());
//                if (tag.equals("list")) {
//                    list.add(new UlsanData(temp.title,temp.addr,temp.xpos,temp.ypos,temp.info,temp.traffic,temp.tel,temp.regdt));
//                }
//            }
//            
//            event_type = xpp.next();
//        }
////        printList(list);
//        String data = "myObj = { 'data':[";
//        for(UlsanData entity : list){
//            data+=entity.returnJson()+",";
//        }
//        data = data.substring(0, data.length()-1); 
//        data += " ]};";
//        System.out.println(data);
//        return data;
//    }
// 
//    /**
//     * 결과 값을 출력해본다.
//     * @param list
//     */
//    private void printList(ArrayList<UlsanData> list){
//        for(UlsanData entity : list){
//            entity.showInfo();
//        }
//    }
    
 
}

/*
class UlsanData{
	String title;
	String addr;
	String xpos;
	String ypos;
	String info;
	String traffic;
	String tel;
	String regdt;
	
	public UlsanData(String title, String addr,	String xpos, String ypos, String info, String traffic, String tel, String regdt){
		this.title=title;
		this.addr=addr;
		this.xpos=xpos;
		this.ypos=ypos;
		this.info=info;
		this.traffic=traffic;
		this.tel=tel;
		this.regdt=regdt;
	}
	
	public void showInfo(){
		System.out.println("------------------------------------");
		System.out.println(title);
		System.out.println(addr);
		System.out.println(xpos);
		System.out.println(ypos);
		System.out.println(info);
		System.out.println(traffic);
		System.out.println(tel);
		System.out.println(regdt);
		System.out.println("------------------------------------");
	}
	
	public String returnJson(){
		return "{'title':"+ title + ", 'xpos' : "+xpos + ", 'ypos' : "+ypos +"}";
	}
}
*/