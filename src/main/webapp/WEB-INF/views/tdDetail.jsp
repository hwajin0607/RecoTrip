<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
    <head>
	    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">
        <title>TDdetail</title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        
        <style>
			
            #title{
                background-color: rgb(246, 247, 248);
                width: 1000px;
                height: 80px;
                text-align: center;
                border-radius: 10px;
                margin: auto;
            }
            #title p{
                text-align: center;
                margin: 5px;   
            }
            .photo{
                width: 800px;
                height: 400px;
                background-color: white;
                border-radius: 10px;
                text-align: center;
                margin: auto;
                margin-top: 5px;
            }
            .text{
                width: 400px;
                height: 200px;
                background-color: white;
                border-radius: 10px;
                text-align: center;
                margin: auto;
            }
            #textBox{
                width: 400px;
                height: 400px;
                background-color: white;
                text-align: center;
                border-radius: 10px;
                margin: auto;
            }
            .prologue{
                width: 1000px;
                height: 500px;
                background-color: rgb(246, 247, 248);
                text-align: center;
                border-radius: 10px;
                margin: auto;
            }

            #plus{
                width: 400px;
                height: 50px;
                text-align: center;
                margin: auto;  
                display: block;
            }
            #plus img{
                width: 50px;
                height: 50px;
            }
            .app{
            	margin: auto;
            }
            .app button{
                text-align: center;
            }
            #delete,#update{
            	display: block;
            }
            #delete:hover{
            	background-color: #d8fae6;            	
            }
            
            #update:hover{
            	background-color: #d8fae6;
            }
            #my{
            	margin: 10px;
            }
            #my li{
            	float: left;
            }
            #update{
				position: relative;
				left: 67%;
				background-color: rgb(246, 247, 248);
            	height: 22px;
            	border-radius: 10px;
            	padding: 5px;
						
			}
            #update a{
				text-decoration: none;
				color: black;
						
			}
			#delete{
				position: relative;
				left: 68%;
            	background-color: rgb(246, 247, 248);
            	height: 22px;
            	border-radius: 10px;
            	padding: 5px;
			}
			#delete a{
				text-decoration: none;
				color: black;
						
			}

            .group{
            	display: none;
            }
			img{
				max-width: 400px;
				max-height: 400px;
				
			}
			body > h2{
				text-align: center;
			}
			.commentAll{
		        position: absolute;
			    width: 92%;
			    left: 7%;
			    padding-bottom: 3%;
			}
			.comment{
			    position: relative;
			    left: 18%;
			    height: 70px;
			    width: 90%;
			    top: 138%;
			    margin-bottom: 1%;
			}
			.con{
				width:60%;
				height: 30px;
			}
			.commentList{
			   	position: relative;
			    left: 18%;
			    width: 58%;
			    height: auto;
			}
			.btnt{
				border: 0;
			    width: 41px;
			    height: 43px;
			    position: absolute;
			    top: 18px;
			    right: 36%;
			}
			.btnt:hover{
				cursor: pointer;
			}
			#reply{
				position:absolute;
				top:0%;
			}
			[name=commentForm]{
				margin-top: 4px;
			}

			/* 좋아요------------------------------------ */
			#likeBox{
				position:relative;
				left:200;
				width: 200px;
				height: 100px;
				margin: auto;
				border-radius: 10px;
				border: 0;
			}
			#likeIcon{
				position: absolute;
				width: 60px;
				height: 60px;
				top: 26px;
				left:34px;
				background-image :url('resources/img/heartEmpty.png');
				background-size: 60px;
				background-repeat: no-repeat;
			}
			#likeIcon:hover{
				cursor: pointer;
			}
			#likeCnt{
				position: absolute;
				width: 80px;
				height: 60px;
				top: 29px;
    			left: 100px;
				font-weight: bold;
            	font-size: 33px;
            	text-align: center;
			}
			/* 좋아요------------------------------------ */
			 @charset "utf-8";
		
		/* 아이콘 이미지 최적화 작업 */
		.icon {background-image: url(resources/image/icon.png);}

		@media only screen and (-webkit-min-device-pixel-ratio: 1.5),
       only screen and (min-device-pixel-ratio: 1.5),
       only screen and (min-resolution: 1.5dppx){
       .icon {background-image: url(resources/image/icon@2.png); background-size: 500px;}
       }

       #footer {background: #211f1f;position: relative;
        top: 900px;}
       
       /* 가운데 영역 */
       .container {position: relative; width: 1280px; margin: 0 auto; /* background:rgba(0,0,0,0.1); */ }
       
       /* 로우 영역 */
       .row {padding: 0 16px;}
                                                   
       /* 푸터 */
       .footer_sns {position: absolute; right: 17px; top: 118px; z-index: 1;}
       .footer_sns li {display: inline-block; width: 36px; height: 36px; margin-left: 4px;}
       .footer_sns li.s1 {background-position: 0 -375px;}
       .footer_sns li.s2 {background-position: -36px -375px;}
       .footer_sns li.s3 {background-position: -72px -375px;}
       .footer_sns li.s4 {background-position: -108px -375px;}
       .footer_sns li.s5 {background-position: -144px -375px;}
       .footer_sns li.s1:hover {background-position: 0 -411px;}
       .footer_sns li.s2:hover {background-position: -36px -411px;}
       .footer_sns li.s3:hover {background-position: -72px -411px;}
       .footer_sns li.s4:hover {background-position: -108px -411px;}
       .footer_sns li.s5:hover {background-position: -144px -411px;}
       .footer_sns .tel {text-align: right; padding-top: 5px;}
       .footer_sns .tel a {color: #8f8f8f; font-size: 21px;}
       .footer_sns .tel a em {color: #fff;}
       
       .footer_infor {padding: 50px 0; width: 80%;}
       .footer_infor h2 img {width: 107px;}
       .footer_infor ul {padding: 15px 0;}
       .footer_infor li {position: relative; display: inline; padding-right: 16px; white-space: nowrap;}
       .footer_infor li:after {content: ''; position: absolute; right: 5px; top: 5px; width: 1px; height: 11px; background: #8f8f8f;}
       .footer_infor li:last-child:after {width: 0; height: 0;}
       .footer_infor li a {color: #8f8f8f;}
       .footer_infor address p {color: #8f8f8f; padding-bottom: 15px;}
       
       @media (max-width: 960px){
           .footer_sns {position: static; text-align: center; background: #352c2c; padding-bottom: 15px;}
           .footer_sns .tel {display: none;}
           .footer_infor {width: 100%; text-align: center;}
       }
       
       @media (max-width: 600px){
           .footer_infor {padding: 30px 0;}
           .footer_infor address p br {display: none;}
       }
        </style>
    </head>
    <body>
    <%@ include file="navi.jsp" %>
        <table id="main">
            <tr>
                <div class="group">
                	<select name="diary_public">
                		<option value="1">공개</option>
                		<option value="0">비공개</option>
                	</select>

                </div>
            </tr>
            <tr>
                <div id="title">
                    <p name="title">${list.diary_subject}</p></br>
                    <p name="date">${list.diary_reg_date}</p>
                </div>
           		<ul id="my">
		           <li id="update"><a href="tdUpdateForm?idx=${idx}">수정하기</a></li>
		           <li id="delete"><a href="tdDelete?idx=${idx}">삭제하기</a></li>
           		</ul>
           		<br/><br/>
            </tr>
            <tr>
            	<div class="prologue">
           		<h2>Profile</h2>
                <div class="photo" name="content0">
   					${list.diary_main}
                </div>
                </div>
            </tr>
        </table>
            <table class="app">
                <tr>
                    <div class="prologue">
                        <h2>Prologue</h2>
                        <div class="photo" name="content1">
   							${list.diary_content1}
                        </div>
                    </div>
                </tr>
            </table>
            <c:if test="${not empty list.diary_content2}">
	         	<table class="app">
	                <tr>
	                    <div class="prologue">
	                    	<br/>
	                        <div class="photo" name="content2">
	    							${list.diary_content2}
	                        </div>
	                    </div>
	                </tr>
	            </table>              
  	 	   </c:if>
  	 	   <c:if test="${not empty list.diary_content3}">
	         	<table class="app" >
	                <tr>
	                    <div class="prologue">
	                    	<br/>
	                        <div class="photo" name="content3">
    							${list.diary_content3}
	                        </div>
	                    </div>
	                </tr>
	            </table>              
  	 	   </c:if>
  	 	   <c:if test="${not empty list.diary_content4}">
	         	<table class="app">
	                <tr>
	                    <div class="prologue">
	                    	<br/>
	                        <div class="photo" name="content4">
    							${list.diary_content4}
	                        </div>
	                    </div>
	                </tr>
	            </table>              
  	 	   </c:if>
  	 	   <c:if test="${not empty list.diary_content5}">
	         	<table class="app">
	                <tr>
	                    <div class="prologue">
	                    	<br/>
	                        <div class="photo" name="content5">
    							${list.diary_content5}
	                        </div>
	                    </div>
	                </tr>
	            </table>              
  	 	   </c:if>
  	 	   

           
           <!-- 좋아요 -->
           <div id="likeBox">
           		<div id="likeIcon"></div>
           		<div id="likeCnt"></div>
           		<input type="hidden" name="likeIconCondition" id="likeIconCondition" value=""/>
           </div>
           
           <!-- 댓글 -->
           <div class="commentAll">
           <div class="comment">
              <label for="content">&nbsp;댓글 : [ <span name="count"></span> ]</label>
              <br/>
              <form name="commentForm">
                     <input type="hidden" name="tdIdx" value="${tdIdx}"/>
                     <input type="hidden" name="loginId" value="${sessionScope.loginId}"/>
                     <input type="text" class="con" id="content" name="comment" placeholder="댓글을 입력하세요.">
                          <button class="btnt" type="button" name="commentBtn">등록</button>
              </form>
          </div>
            
          <div class="commentList">
           
          </div>
          <%@ include file="diaryReply.jsp" %>
          </div>
	
	
            
    </body>
     <footer id="footer">
        <div id="footer_sns">
            <div class="container">
                <div class="footer_sns">
                    <ul>
                        <li class="icon s1"><a href="#"><span class="ir_pm"></span></a></li>
                        <li class="icon s2"><a href="#"><span class="ir_pm"></span></a></li>
                        <li class="icon s3"><a href="#"><span class="ir_pm"></span></a></li>
                        <li class="icon s4"><a href="#"><span class="ir_pm"></span></a></li>
                        <li class="icon s5"><a href="#"><span class="ir_pm"></span></a></li>
                    </ul>    
                    <div class="tel">
                        <a href="#">ARS <em>02-2108-5900</em></a>
                    </div>
                </div>
            </div>
        </div>
        <div id="footer_infor">
            <div class="container">
                <div class="row">
                    <div class="footer_infor">
                        <h2><img src="resources/image/logo2.png" alt=""></h2>
                        <ul>
                            <li><a href="#">회사소개</a></li>
                            <li><a href="#">채용정보</a></li>
                            <li><a href="#">제휴/광고/부대사업 문의</a></li>
                            <li><a href="#">이용약관</a></li>
                            <li><a href="#">개인정보처리방침</a></li>
                            <li><a href="#">고객센터</a></li>
                            <li><a href="#">윤리경영</a></li>
                        </ul>
                        <address>
                            <p>08505 서울시 금천구 가산디지털2로 115, 509호,811호(가산동, 대륭테크노타운3차),구디아카데미(주)<br><span class="bar2">대표자명 이승엽</span> 개인정보보호 책임자 HRD Team 주승재 차장<br><span class="bar2"> 사업자등록번호 : 457-85-00300</span> 통신판매업신고번호 : 제2014-서울금천-0684호</p>
                            <p>전화 : 02-2108-5900 , 팩스 : 02-2108-5909</p>
                        </address>
                    </div>
                </div>
            </div>
        </div>
    </footer>
    <script>
	
    var idx = "${idx}";
    btn();
    function btn() {
    	$.ajax({
			type:"get",
			url:"tdButton",
			data:{"idx":idx},
			dataType:"JSON",
			success:function(data){
				if(data!=1){
					$("#delete").css("display","none");
					$("#update").css("display","none");
				}
			},
			error:function(error){
				console.log(error);
			}
			});
		
	}
    /*--------------- 좋아요 --------------------*/
    likeCheck();
    getLikeCnt();
    var loginId = "${sessionScope.loginId}";
    $("#likeIcon").mouseenter(function () {
    	if($("#likeIconCondition").val()==0){
    		$("#likeIcon").css("background-image","url('resources/img/heartFilled.png')");
    	}else{
    		$("#likeIcon").css("background-image","url('resources/img/heartEmpty.png')");
    	}
	});
    $("#likeIcon").mouseleave(function () {
    	if($("#likeIconCondition").val()==0){
    		$("#likeIcon").css("background-image","url('resources/img/heartEmpty.png')");
    	}else{
    		$("#likeIcon").css("background-image","url('resources/img/heartFilled.png')");
    	}
	});
    function getLikeCnt() { //해당 게시글 좋아요 갯수 파악
    	$.ajax({
			type:"POST",
			url:"getLikeCnt",
			data:{"idx":idx},
			dataType:"JSON",
			success:function(data){
				$("#likeCnt").text(data);
			},
			error:function(error){
				console.log(error);
			}
			});
	}
    function likeCheck() { //로그인한 회원이 좋아요 눌렀는지 확인
    	$.ajax({
			type:"POST",
			url:"likeCheck",
			data:{"idx":idx},
			dataType:"JSON",
			success:function(data){
				console.log(data);
				if(data>0){
					$("#likeIconCondition").val(1);
					$("#likeIcon").css("background-image","url('resources/img/heartFilled.png')");
				}else{
					$("#likeIconCondition").val(0);
					$("#likeIcon").css("background-image","url('resources/img/heartEmpty.png')");
				}
			},
			error:function(error){
				console.log(error);
			}
			});
	}
    $("#likeIcon").click(function () {
    	if(loginId!="" && $("#likeIconCondition").val()==0){
    		$.ajax({
    			type:"post",
    			url:"like",
    			data:{"idx":idx,
    				"loginId":loginId},
    			dataType:"JSON",
    			success:function(data){
					if(data>0){
						$("#likeIcon").css("background-image","url('resources/img/heartFilled.png')");
	    				$("#likeIconCondition").val(1);
	    				getLikeCnt();
    				}
    			},
    			error:function(error){
    				console.log(error);
    			}
    			});
    	}else if(loginId!="" && $("#likeIconCondition").val()==1){
    		$.ajax({
    			type:"post",
    			url:"disLike",
    			data:{"idx":idx,
    				"loginId":loginId},
    			dataType:"JSON",
    			success:function(data){
    				if(data>0){
						$("#likeIcon").css("background-image","url('resources/img/heartEmpty.png')");
	    				$("#likeIconCondition").val(0);
	    				getLikeCnt();
    				}
    			},
    			error:function(error){
    				console.log(error);
    			}
    			});
    	}else{
    		alert("로그인이 필요합니다.")
    	}
	});
    </script>
</html>