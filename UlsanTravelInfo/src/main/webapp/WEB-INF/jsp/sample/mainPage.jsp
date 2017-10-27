<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</head>
<body>
	<div id="mainBG"></div>
	<div id="contents">
		<h1>울산 관광 정보 시스템</h1>
		<div class="mapDiv">
			<div id="googleMap"></div>
			<br>
			<p>API 콘솔 주소 =  <a href="https://console.developers.google.com/projectselector/apis/dashboard?hl=ko">https://console.developers.google.com/projectselector/apis/dashboard?hl=ko</a></p>
		</div>
		<div class="buttonDiv">
			<div class="buttonName">
				<p>울산 12경</p>
				<p>무료와이파이존</p>
				<p>도시공원</p>
				<p>전시/박물관</p>
				<p>농어촌체험마을</p>
				<p>전통시장</p>
				<p>공중화장실</p>
			</div>
			<div class="buttonSwitch">
				<label class="switch">
				  <input type="checkbox" id="ulsan12">
				  <span class="slider round"></span>
				</label>
				<label class="switch">
				  <input type="checkbox" id="ulsanPubwifi">
				  <span class="slider round"></span>
				</label>
				<label class="switch">
				  <input type="checkbox" id="ulsanPark">
				  <span class="slider round"></span>
				</label>
				<label class="switch">
				  <input type="checkbox" id="ulsanMuseum">
				  <span class="slider round"></span>
				</label>
				<label class="switch">
				  <input type="checkbox" id="ruralVillage">
				  <span class="slider round"></span>
				</label>		
				<label class="switch">
				  <input type="checkbox" id="ulsanMarket">
				  <span class="slider round"></span>
				</label>
				<label class="switch">
				  <input type="checkbox" id="ulsanToilet">
				  <span class="slider round"></span>
				</label>
			</div>
		</div>
	</div>
    
	<script async defer
	   src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCV20csFPiq82GMIeWgAN_fYSWc02xDNQM&callback=initMap">//my key =AIzaSyCV20csFPiq82GMIeWgAN_fYSWc02xDNQM
	</script>
    <script type="text/javascript">
    function initMap() {
		map = new google.maps.Map(document.getElementById('googleMap'), {
        	zoom: 11,
        	center:new google.maps.LatLng(35.53889, 129.31667)//울산좌표
      	});
    }
 	var settingButton = function(buttonID,url,serviceID,icon){
    	var markers=this.markers=[];
    	var onOff=this.onOff=false;

    	 $(buttonID).on("click", function(e){
         	if(onOff){
         		onOff=false;
         		setMapOnAll(null);
         		markers=[];
         		
       		}else{
       			onOff=true;
       			addMarkers(url,serviceID);
   			}
         });

    	 function addMarkers(requestUrl,firstName){
	            var result=
	            (function(temp){
				    $.ajax({
				        url : "dataAccess.do",
				        dataType: "text",
				        async: false,
				        data: {"requestUrl" : requestUrl, "firstName":firstName},
				        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				        success : function(responseData,data,returnData) {
				        	var afterStr = returnData.responseText.split('^');
				        	temp= afterStr;
				        },
				        error : function(responseData,request,status,error){
				            console.log("에러");
				        }
	           		});
				    return temp;
	            })(result);
	            
				for(var i=0;i<result.length;i++){
					var xy=JSON.parse(result[i]);
					var marker = new google.maps.Marker({
				          position: new google.maps.LatLng(xy.ypos,xy.xpos),
				          map: map,
				          title: xy.title,
				         icon: icons[icon].icon,
				         text:'안녕'
				        });
// 					var marker = new google.maps.Marker(
// 							new MarkerOptions()
// 			                    .position(new google.maps.LatLng(xy.ypos,xy.xpos))
// 			                    .title(xy.title).snippet("안녕").icon(icons[icon].icon)
//                     );
					 markers.push(marker);
				}	
     	}
    	function setMapOnAll(map) {
			for (var i = 0; i < markers.length; i++) {
				markers[i].setMap(map);
			}
		}
    }

	$(document).ready(function(){
       	new settingButton("#ulsan12","http://data.ulsan.go.kr/rest/ulsanscenes/getUlsanscenesList","ulsanscenes","TD");
       	new settingButton("#ulsanPubwifi","http://data.ulsan.go.kr/rest/ulsanpubwifi/getUlsanpubwifiList","ulsanpubwifi","wifi");
       	new settingButton("#ulsanPark","http://data.ulsan.go.kr/rest/ulsanpark/getUlsanParkList","ulsanpark","park");
       	new settingButton("#ulsanMuseum","http://data.ulsan.go.kr/rest/ulsanexhandmus/getUlsanexhandmusList","ulsanexhandmus","museum");
       	new settingButton("#ruralVillage","http://data.ulsan.go.kr/rest/ulsanruralvillage/getUlsanruralvillageList","ulsanruralvillage","village");
       	new settingButton("#ulsanMarket","http://data.ulsan.go.kr/rest/ulsanmarket/getUlsanmarketList","ulsanmarket","market");
       	new settingButton("#ulsanToilet","http://data.ulsan.go.kr/rest/ulsantoilet/getUlsantoiletList","toilet","toilet"); 
       	
        var numberOfImages=4;
        var imageNum = Math.round(Math.random()*(numberOfImages-1))+1; 
        var imgPath=(imageNum+'.png'); 
        $('#mainBG').css('background-image', ('url("images/'+imgPath+'")')); 
    });
    var icons = {
	    TD: {
	  	  icon: "http://maps.google.com/mapfiles/kml/pal4/icon38.png"
	    },
	    wifi: {
	  	  icon: "http://maps.google.com/mapfiles/kml/pal5/icon30.png"
	    },
	    park: {
	  	  icon: "http://maps.google.com/mapfiles/kml/pal2/icon4.png"
	    },
	    museum: {
	  	  icon: "http://maps.google.com/mapfiles/kml/pal3/icon19.png"
	    },
	    village: {
	  	  icon: "http://maps.google.com/mapfiles/kml/pal3/icon48.png"
	    },
	    market: {
	  	  icon: "http://maps.google.com/mapfiles/kml/pal5/icon36.png"
	    },
	    toilet: {
	  	  icon: "http://maps.google.com/mapfiles/kml/pal5/icon19.png"
	    }
	};
    </script>
</body>
</html>
