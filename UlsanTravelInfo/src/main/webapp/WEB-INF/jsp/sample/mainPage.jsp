<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<%@ include file="/WEB-INF/include/include-body.jspf"%>
</head>
<body onload= "screenReSize()" onresize="screenReSize()">
<!-- 	<div id="mainBG"></div> -->
	<div id="line"></div>
	<div id="contents">
		<div id="title">
			<h1>울산 관광 정보 시스템</h1>
		</div>
		<div class="mapDiv">
			<div id="googleMap"></div>
		</div>
		<div class="buttonDiv">
			<br>
			<p>울산 12경</p>
			<label class="switch"> <input type="checkbox" id="ulsan12">
				<span class="slider round"></span>
			</label>
			<p>무료와이파이존</p>
			<label class="switch"> <input type="checkbox"
				id="ulsanPubwifi"> <span class="slider round"></span>
			</label>
			<p>도시공원</p>
			<label class="switch"> <input type="checkbox" id="ulsanPark">
				<span class="slider round"></span>
			</label>
			<p>전시/박물관</p>
			<label class="switch"> <input type="checkbox"
				id="ulsanMuseum"> <span class="slider round"></span>
			</label>
			<p>농어촌체험마을</p>
			<label class="switch"> <input type="checkbox"
				id="ruralVillage"> <span class="slider round"></span>
			</label>
			<p>전통시장</p>
			<label class="switch"> <input type="checkbox"
				id="ulsanMarket"> <span class="slider round"></span>
			</label>
			<p>공중화장실</p>
			<label class="switch"> <input type="checkbox"
				id="ulsanToilet"> <span class="slider round"></span>
			</label>
		</div>
		<div class="info">
			<h2 id="infoTitle">장소</h2>
			<p id="infoAddr">주소</p>
			<p id="infoTel">전화</p>
			<p id="infoXY" class="invisible"></p>
			<div id="comments"></div>
			<input type="text" id="CREA_NAME" name="CREA_NAME"/>
			<input type="text" id="COMMENT" name="COMMENT"/>
			<p class="star_rating">
			    <a href="#" class="on">★</a>
			    <a href="#" class="on">★</a>
			    <a href="#" class="on">★</a>
			    <a href="#">★</a>
			    <a href="#">★</a>
			</p>
			<input id="commentSubmit"type="submit" value="댓글달기" />
		</div>
	</div>

	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCV20csFPiq82GMIeWgAN_fYSWc02xDNQM&callback=initMap">
		//my key =AIzaSyCV20csFPiq82GMIeWgAN_fYSWc02xDNQM
	</script>
	<script type="text/javascript">
		function initMap() {
			map = new google.maps.Map(document.getElementById('googleMap'), {
				zoom : 11,
				center : new google.maps.LatLng(35.53889, 129.31667)//울산좌표
			});
		}
		var settingButton = function(buttonID, url, serviceID, icon) {
			var markers = this.markers = [];
			var onOff = this.onOff = false;

			$(buttonID).on("click", function(e) {
				if (onOff) {
					onOff = false;
					setMapOnAll(null);
					markers = [];

				} else {
					onOff = true;
					addMarkers(url, serviceID);
				}
			});

			
			function addMarkers(requestUrl, firstName) {
				var result = (function(temp) {
					$.ajax({
						url : "dataAccess.do",
						dataType : "text",
						async : false,
						data : {
							"requestUrl" : requestUrl,
							"firstName" : firstName
						},
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						success : function(responseData, data,
								returnData) {
							var afterStr = returnData.responseText
									.split('^');
							temp = afterStr;
						},
						error : function(responseData, request, status,
								error) {
// 							console.log("에러");
						}
					});
					return temp;
				})(result);

				for (var i = 0; i < result.length; i++) {
					var xy = JSON.parse(result[i]);
					var marker = new google.maps.Marker({
						position : new google.maps.LatLng(xy.ypos, xy.xpos),
						ypos:xy.ypos,
						xpos:xy.xpos,
						map : map,
						title : xy.title,
						icon : icons[icon].icon,
						addr: xy.addr,
						tel:xy.tel
					});
					
					marker.addListener('click', function(event){
							$("#infoTitle").text(this.title);
							$("#infoAddr").text('주소 : '+this.addr);
							$("#infoTel").text('전화 : '+this.tel);
							$("#infoXY").text(this.ypos+','+this.xpos);
// 							$("#XY").val(this.ypos+','+this.xpos);
							var infowindow = new google.maps.InfoWindow({
	 					          content: '<p>'+this.title+'</p>'
	 					        });
	 						infowindow.open(map, this);
	 						$.ajax({
	 							url : "selectComment.do",
	 							dataType : "json",
	 							async : false,
	 							data : {
	 								"TITLE" : this.title,
	 								"XY" : this.ypos+','+this.xpos
	 							},
	 							contentType : "application/x-www-form-urlencoded; charset=UTF-8",
	 							success : function(response) {
									var temp='';
									if(response.length>4){
										var commentLength=4;
									}else{
										var commentLength=response.length;
									}
									for(var i=0; i<commentLength;i++){
										temp+='<p id="commentDetail">'+response[i].CREA_NAME +' '+response[i].COMMENTS +' '+ response[i].CREA_DTM;
										temp+="<p class='star_pointing'>";
										for(var j=0; j<response[i].STAR_POINT;j++){
											temp+='<a class="on">★</a>';
										}
										for(var j=0; j<5-response[i].STAR_POINT;j++){
											temp+='<a >★</a>';
										}
										temp+='</p>'+'</p>';
									}
									$("#comments").empty();
									$("#comments").append(temp);
									$("#commentSubmit").css("display","inline");
									$("#CREA_NAME").css("display","inline");
									$("#COMMENT").css("display","inline");
									$(".star_rating").css("display","inline");
	 								},
	 								error : function(responseData, request, status, error) {
	 		 							console.log("에러");
	 							}
	 						});
						});
					markers.push(marker);
				}
			}
			function setMapOnAll(map) {
				for (var i = 0; i < markers.length; i++) {
					markers[i].setMap(map);
				}
			}
		}

		$(document).ready(
			function() {
				//-------------------------버튼세팅--------------------------------
				new settingButton(
						"#ulsan12",
						"http://data.ulsan.go.kr/rest/ulsanscenes/getUlsanscenesList",
						"ulsanscenes", "TD");
				new settingButton(
						"#ulsanPubwifi",
						"http://data.ulsan.go.kr/rest/ulsanpubwifi/getUlsanpubwifiList",
						"ulsanpubwifi", "wifi");
				new settingButton(
						"#ulsanPark",
						"http://data.ulsan.go.kr/rest/ulsanpark/getUlsanParkList",
						"ulsanpark", "park");
				new settingButton(
						"#ulsanMuseum",
						"http://data.ulsan.go.kr/rest/ulsanexhandmus/getUlsanexhandmusList",
						"ulsanexhandmus", "museum");
				new settingButton(
						"#ruralVillage",
						"http://data.ulsan.go.kr/rest/ulsanruralvillage/getUlsanruralvillageList",
						"ulsanruralvillage", "village");
				new settingButton(
						"#ulsanMarket",
						"http://data.ulsan.go.kr/rest/ulsanmarket/getUlsanmarketList",
						"ulsanmarket", "market");
				new settingButton(
						"#ulsanToilet",
						"http://data.ulsan.go.kr/rest/ulsantoilet/getUlsantoiletList",
						"toilet", "toilet");
				$("#commentSubmit").on("click", function(e){ //댓글작성하기 버튼
		            e.preventDefault();
		            fn_insertComment();
		        });
				
				//------------------------------별점----------------------------------
				$( ".star_rating a" ).click(function() {
				     $(this).parent().children("a").removeClass("on");
				     $(this).addClass("on").prevAll("a").addClass("on");
				     return false;
				});

				//------------------------------랜덤 배경----------------------------------
// 				var numberOfImages = 4;
// 				var imageNum = Math.round(Math.random()
// 						* (numberOfImages - 1)) + 1;
// 				var imgPath = (imageNum + '.png');
// 				$('#mainBG').css('background-image',
// 						('url("images/' + imgPath + '")'));	
				//------------------------------------------------------------------------			
		});
		function screenReSize(){
			var w = window.outerWidth;
			var padding_left=0;
			if(w>1250){		
				if(w-1310>290){
					padding_left=290;
				}else{					
					padding_left=w-1250;
				}
			}
			else{
				padding_left=0;
			}
			$("#contents").css("padding-left",padding_left);
			
		}
		function fn_insertComment(){
	        var comSubmit = new ComSubmit("infomation");//form아이디
			if($("#CREA_NAME").val()==""||$("#CREA_NAME").val()==null){
				alert("작성자 이름을 입력해주세요");
				return false;
			}else if($("#COMMENT").val()==""||$("#COMMENT").val()==null){
				alert("코멘트 내용을 입력해주세요");
				return false;
			}
	        
	        $.ajax({
					url : "insertComment.do",
					dataType : "json",
					async : false,
					data : {
						"TITLE" : $("#infoTitle").text(),
						"XY" : $("#infoXY").text(),
						"CREA_NAME" : $("#CREA_NAME").val(),
						"COMMENT" : $("#COMMENT").val(),
						"STAR_POINT" : $(".star_rating>a.on").size()
					},
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success : function(response) {
						var temp='';
						if(response.length>4){
							var commentLength=4;
						}else{
							var commentLength=response.length;
						}
						for(var i=0; i<commentLength;i++){
							temp+='<p id="commentDetail">'+response[i].CREA_NAME +' '+response[i].COMMENTS +' '+ response[i].CREA_DTM;
							temp+="<p class='star_pointing'>";
							for(var j=0; j<response[i].STAR_POINT;j++){
								temp+='<a class="on">★</a>';
							}
							for(var j=0; j<5-response[i].STAR_POINT;j++){
								temp+='<a >★</a>';
							}
							temp+='</p>'+'</p>';
						}
						$("#comments").empty();
						$("#comments").append(temp);
						$("#CREA_NAME").val('');
						$("#COMMENT").val('');
//						console.dir(response);
						},
						error : function(responseData, request, status, error) {
							console.log("에러");
					}
				});
	    }
		var icons = {
			TD : {
				icon : "http://maps.google.com/mapfiles/kml/pal4/icon38.png"
			},
			wifi : {
				icon : "http://maps.google.com/mapfiles/kml/pal5/icon30.png"
			},
			park : {
				icon : "http://maps.google.com/mapfiles/kml/pal2/icon4.png"
			},
			museum : {
				icon : "http://maps.google.com/mapfiles/kml/pal3/icon19.png"
			},
			village : {
				icon : "http://maps.google.com/mapfiles/kml/pal3/icon48.png"
			},
			market : {
				icon : "http://maps.google.com/mapfiles/kml/pal5/icon36.png"
			},
			toilet : {
				icon : "http://maps.google.com/mapfiles/kml/pal5/icon19.png"
			}
		};
	</script>
</body>
</html>
