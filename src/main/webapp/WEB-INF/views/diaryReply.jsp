<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="resources/js/jquery.twbsPagination.js" type="text/javascript"></script> 
        <style>
   			body{
				overflow-x:hidden;
				overflow-y:auto;  
			}
			a{
				color:blue;
				cursor:pointer;
			}
			a:hover{
				text-decoration: underline;
			}
			.form-control{
        	    width: 80%;
			    height: 23px;
			    position: relative;
			    left: 5%;
			    top: 34%;
			    font-size: 15px;
			}
			.updatebtn{
			    width: 50px;
			    height: 27px;
			    position: relative;
			    left: 6%;
			    top: 34%;
			}
			.conSub{
				position: absolute;
				height: 36px;
			}
			.oriCon{
				height: 36px;
			}
			.input-group{
				height: 36px;
				margin-bottom: 15px;
				
			}
			.cut{
			 	width: 50px;
			    height: 27px;
			    position: relative;
			    left: 7%;
			    top: 34%;
			}
			.pagination li{
				list-style: none;
				float:left;
				margin:10px;
			}
			.pagination{
				position:relative;
				left:26%;

			}
			
			.pagination a{
				color: black;
			}		
			.pagination a:hover{
				color: blue;
			}
			.container{
				position:relative;
				left:4%;
			}
        </style>
    </head>
    <body>		
			<div class="container">					
				<nav aria-label="Page navigation" style="text-align:center">
					<ul class="pagination" id="pagination"></ul>
				</nav>					
			</div>
				
    
    
    </body>
    <script>
    var tdIdx = '${tdIdx}';
    console.log("게시물 번호 : "+tdIdx);

    
    $('[name=commentBtn]').click(function(){
    	console.log("버튼 눌렀따.");

		if($('[name=comment]').val()==''){
			alert("댓글을 입력해주세요.");
		}else{
	        var content = $('[name=commentForm]').serialize();
	        commentWrite(content);
	        console.log(content);
		}
		
    });

    
    //댓글 갯수
    commentCount();
    
    
    //페이징
    listCall(1);
    
    //댓글 리스트
	 function listCall(page){
		var ppn=5;
		var tdIdx = '${tdIdx}';
		
		 $.ajax({
				url:"replylist",
				type:"post",
				data:{
					"ppn":ppn,
					"page":page,
					"tdIdx":tdIdx
				},
				dataType:"json",
				success:function(data){	
					console.log("페이징 데이터 : "+data);
					var loginId = "${sessionScope.loginId}";
	                var a =''; 
	                console.log(data.list);
	                $.each(data.list, function(key,value){ 
	                    var userId = value.id;
	                    if(loginId==userId || loginId=="admin"){
	                    	a += '<div class="commentArea" style="border-bottom:1px solid darkgray; margin-bottom: 15px;">';
	                        a += '<div class="commentInfo'+value.reply_num+'">'+'작성자 : '+value.id;
	                        a += '<a class="up'+value.reply_num+'" onclick="commentUpdateForm('+value.reply_num+',\''+value.reply_content+'\');">&nbsp;수정</a>';
	                        a += '<a onclick="commentDelete('+value.reply_num+');"> 삭제 </a></div>';
	                        a += '<div class="commentContent'+value.reply_num+'"> <div class="oriCon"><p>내용 : '+value.reply_content +'</p></div>';
	                        a += '</div></div>';
	                    }else{
	                    	a += '<div class="commentArea" style="border-bottom:1px solid darkgray; margin-bottom: 15px;">';
	                        a += '<div class="commentInfo'+value.reply_num+'">'+'작성자 : '+value.id+'</div>';
	                        a += '<div class="commentContent'+value.reply_num+'"> <div class="oriCon"><p>내용 : '+value.reply_content +'</p></div>';
	                        a += '</div></div>';
	                    }
	                });
	                $(".commentList").html(a);

					$('#pagination').twbsPagination({
						startPage: data.currPage,	//시작 페이지
						totalPages: data.range,	//총 페이지 갯수
						visiblePages: 5,	//보여줄 페이지 수
						onPageClick: function (event, page) {
							console.log(event);
							console.log(page);
							listCall(page);
						}
					});
					
				},
				error:function(e){
					console.log(e);
				}
			});
		
	}
    
	 //댓글 수
    function commentCount(){
    	$.ajax({
        	type:"post",
			url:"commentCount",
            data : {tdIdx},
            success : function(data){
                console.log("댓글 갯수 : "+data);
                $('[name=count]').html(data);
	    	},
			error:function(error){
				console.log(error);
			}
        });
    }
    
    //댓글 등록
    function commentWrite(content){

        $.ajax({
        	type:"post",
			url:"commentWrite",
            data : content,
            success : function(data){
                if(data == 1) {

                    $('[name=comment]').val('');
                    commentCount();
                    location.reload();

                }
	    	},
			error:function(error){
				console.log(error);
			}
        });
    }

    
    function commentUpdateForm(reNum, content){
        var a ='';
        console.log("reNum : "+reNum);
        a += '<div class="input-group">';
        a += '<div class="conSub"><p>내용 : </p></div>';
        a += '<input type="text" class="form-control" name="content_'+reNum+'" value="'+content+'"/>';
        a += '<span class="updatespan"><button class="updatebtn" type="button" onclick="commentUpdate('+reNum+');">확인</button>';
        a += '<button class="cut" type="button" onclick="listCall(1)">취소</button></span>';
        a += '</div>';
        $('.commentContent'+reNum).html(a);
    }
    
    
    //수정
	function commentUpdate(reNum){
	    var upContent = $('[name=content_'+reNum+']').val();
	    console.log("바꿀 내용: "+upContent);
	    if(upContent == ""){
	    	alert("댓글을 입력해주세요.");
	    }else{
		    $.ajax({
		    	type:"post",
				url:"commentUpdate",
		        data : {'upContent' : upContent, 'reNum' : reNum},
		        success : function(data){
	           		 if(data == 1){
	                     location.reload();
		           	}
		        },
				error:function(error){
					console.log(error);
				}
		    });
	    }
	}
    
	//댓글 삭제 
	function commentDelete(reNum){
	    $.ajax({
	    	type:"post",
			url:"commentDelete",
			data : {reNum},
	        success : function(data){
	            if(data == 1){
	            	location.reload();
	            	commentCount();
	            }
	        },
			error:function(error){
				console.log(error);
			}
	    });
	}
	
	$('[name=comment]').click(function(){
		var loginId = "${sessionScope.loginId}";
		if(loginId == ""){
			alert("로그인 후 입력 가능합니다.");
		}
	});
	
	

	


	$(document).ready(function(){
		//commentList();
	});

    </script>
</html>