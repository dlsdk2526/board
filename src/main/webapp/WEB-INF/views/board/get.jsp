<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<style>

.uploadResult {
  width:100%;
  background-color: gray;
}

.uploadResult ul{
  display:flex;
  flex-flow: row;
  justify-content: center;
  align-items: center;
}

.uploadResult ul li {
  list-style: none;
  padding: 10px;
  align-content: center;
  text-align: center;
}

.uploadResult ul li img{
  width: 100px;
}

.uploadResult ul li span {
  color:white;
}

.bigPictureWrapper {
  position: absolute;
  display: none;
  justify-content: center;
  align-items: center;
  top:0%;
  width:100%;
  height:100%;
  background-color: gray; 
  z-index: 100;
  background:rgba(255,255,255,0.5);
}

.bigPicture {
  position: relative;
  display:flex;
  justify-content: center;
  align-items: center;
}

.bigPicture img {
  width:600px;
}

</style>


<!-- Page Heading -->
<h1 class="h3 mb-4 text-gray-800">Board</h1>



<!-- 게시글  -->
<div class="col-md-12">
	<div class="col-md-4">
		<div class="form-group">
			<label for="title">글 번호</label> <input type="text"
				class="form-control" name="bno"
				value='<c:out value="${board.bno }"/>' readonly="readonly">
		</div>
		<div class="form-group">
			<label for="title">제목</label> <input type="text" class="form-control"
				name="title" value='<c:out value="${board.title }"/>'
				readonly="readonly">
		</div>
		<div class="form-group">
			<label for="content"> 내용 </label>
			<textarea class="form-control" name="content" readonly="readonly"><c:out
					value="${board.content }" /></textarea>
		</div>
		<div class="form-group">
			<label for="author"> 작성자 </label> <input type="text"
				class="form-control" name="writer"
				value='<c:out value="${board.writer }"/>' readonly="readonly">
		</div>
		<div class="form-group">
			<label for="author"> 수정일자 </label> <input type="text"
				class="form-control" name="writer"
				value='<c:out value="${board.updatedate }"/>' readonly="readonly">
		</div>
		<div class="form-group">
			<label for="author"> 작성일자 </label> <input type="text"
				class="form-control" name="writer"
				value='<c:out value="${board.regdate }"/>' readonly="readonly">
		</div>
		
		<sec:authentication property="principal" var="pinfo"/>
		<sec:authorize access="isAuthenticated()">
		<c:if test="${pinfo.username eq board.writer }">
			<button type="button" class="btn btn-primary" id="modBtn" data-oper='modify'>수정</button>
			<button type="button" class="btn btn-danger" id="delBtn" data-oper='remove'>삭제</button>
		</c:if>
		</sec:authorize>
	
		
		
		<button type="button" class="btn btn-success" id="listBtn"
			data-oper='list'>목록</button>
	</div>
</div>

<!-- 파일 -->
<div class="bigPictureWrapper">
	<div class="bigPicture"></div>
</div>

<div class='row'>
	<div class="col-lg-12">
		<!-- /.panel -->
		<div class="panel panel-default">
			
			<div class="panel-heading">
			<i class="fa fa-paperclip fa-fw"></i>
			files</div>
			
			<div class="panel-body">
				<div class="uploadResult">
					<ul>
					</ul>
				</div>
			</div><!-- panel-body -->
			<div class="panel-footer"></div>
		</div>
	</div><!-- col-lg-12 -->
</div>
<!-- ./ end row -->



<!-- 댓글 -->
<div class='row'>
  <div class="col-lg-12">
  
    <!-- /.panel -->
    <div class="panel panel-default">
      <div class="panel-heading">
        <i class="fa fa-comments fa-fw"></i> 댓글
        <sec:authorize access="isAuthenticated()">
        <button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>댓글 쓰기</button>
        </sec:authorize>
      </div>      
      
      <!-- /.panel-heading -->
      <div class="panel-body">        
      
        <ul class="chat">

        </ul>
        <!-- ./ end ul -->
      </div>
      <!-- /.panel .chat-panel -->

	<div class="panel-footer"></div>

		</div>
  </div>
  <!-- ./ end row -->
</div>

<!-- 댓글 모달 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			
			<div class="modal-body">
				<div class="form-group">
					<label>내용</label> 
					<input class="form-control" name='reply' value='content'>
				</div>
				<div class="form-group">
					<label>작성자</label> <input class="form-control" name='replyer' value='replyer' readonly="readonly" >
				</div>
				<div class="form-group">
					<label>등록시간</label> <input class="form-control" name='replyDate' value='regDate'>
				</div>

			</div>
			
			<div class="modal-footer">
				<button id='modalModBtn' type="button" class="btn btn-warning">수정</button>
				<button id='modalRemoveBtn' type="button" class="btn btn-danger">삭제</button>
				<button id='modalRegisterBtn' type="button" class="btn btn-primary">등록</button>
				<button id='modalCloseBtn' type="button" class="btn btn-default">닫기</button>
			</div>
		</div>
	</div>
</div>


<form id="actionForm" action="/board/modify" method="get">
	<input type="hidden" name="page" value="${pageDTO.page }"> 
	<input type="hidden" name="perSheet" value="${pageDTO.perSheet }">
	<input type="hidden" name="writer" value="${board.writer}">
</form>

<!-- 댓글 script -->
<script type="text/javascript" src="/resources/js/reply.js"></script>

<script>

//게시물 조회화면 처리 

$(document).ready(function(){
	
	(function(){
		
		var bno = '<c:out value = "${board.bno}"/>';
		
		$.getJSON("/board/getAttachList", {bno: bno}, function(arr){
			
			console.log(arr);
			
			var str = "";
			
			$(arr).each(function(i,attach){
				
				//image type
				if(attach.fileType){
					
					var fileCallPath = encodeURIComponent( attach.uploadPath+ "/"+attach.uuid +"_"+attach.fileName);
					
					str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'><div>";
					str += "<img src='/display?fileName="+fileCallPath+"'>";
					str += "</div>";
					str + "</li>";
					
				}else{
					
					str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'><div>";
					str += "<span>" + attach.fileName+"</span><br/>"
					str += "<img src='/resources/img/attach.png'>";
					str += "</div>";
					str + "</li>";
					
				}
					
			});//end arr
			
			$(".uploadResult ul").html(str);
			
		});//end getjson
		
	})();// end function 즉시실행함수
	
	$(".uploadResult").on("click","li",function(e){
		
		console.log("view image");
		
		var liObj = $(this);
		
		var path =  encodeURIComponent(liObj.data("path")+"/" + liObj.data("uuid")+ "_" + liObj.data("filename"));
		
		if(liObj.data("type")){
			showImage(path.replace(new RegExp(/\\/g),"/"));
		}else{
			self.location = "/download?fileName="+path
		}
	}); 
	
	function showImage(fileCallPath){
		
		$(".bigPictureWrapper").css("display","flex").show();
		
		$(".bigPicture")
		.html("<img src='/display?fileName="+fileCallPath+"'>")
		.animate({width:'100%', height: '100%'}, 1000);
	
	}
		
	//사진 클릭시 닫기
		$(".bigPictureWrapper").on("click",function(e){
			$(".bicPicture").animate({width:'0%', height:'0%'}, 1000);
			setTimeout(function(){
				$(".bigPictureWrapper").hide();
			},50);
		});
	
	

}); //end dom

</script>

<script>

$(document).ready(function() {
	
		const bnoValue = '<c:out value = "${board.bno}"/>';
		const replyUL = $(".chat");

		showList(1);

		function showList(page) {

			replyService.getList(
			{ bno : bnoValue, page : page || 1},
	function(replyCnt, list) {
		
		console.log("replyCnt : "+ replyCnt);
		console.log("list: "+ list);
		console.log(list);
		
		if(page == -1){
			pageNum = Math.ceil(replyCnt/10.0);
			showList(pageNum);
			return;
		}
				
		var str = "";

		if (list == null|| list.length == 0) {

			replyUL.html("");
			return;
		}

		for (var i = 0, len = list.length || 0; i < len; i++) {
			str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
			str += "<div><div class='header'><strong class='primary-font'>["+ list[i].rno+ "] "+ list[i].replyer+ "</strong>";
			str += "<small class='pull-right text-muted'>"+ replyService.displayTime(list[i].replyDate)+ "</small></div>";
			str += "<p>"+ list[i].reply+ "</p></div></li>";
		}//for

			replyUL.html(str);
		
			showReplyPage(replyCnt);

	});//end function

}//end showList 
						
		var modal = $(".modal")
		var modalInputReply = modal.find("input[name='reply']");
		var modalInputReplyer = modal.find("input[name='replyer']");
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		var modalModBtn = $("#modalModBtn")
		var modalRemoveBtn = $("#modalRemoveBtn")
		var modalRegisterBtn = $("#modalRegisterBtn")
		
		var replyer= null;
		
 		<sec:authorize access="isAuthenticated()">
		replyer = '<sec:authentication property="principal.username"/>';
		</sec:authorize> 
		
		var csrfHeaderName = "${_csrf.headerName}";
		var csrfTokenValue = "${_csrf.token}";
		
		
		//ajax spring security header
/*  		$(document).ajaxSend(function(e, xhr, options){
			xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		});   */
		
						    
		$("#addReplyBtn").on("click", function(e){
			
			e.preventDefault();
			e.stopPropagation();	
			
			modal.find("input").val("");
			modal.find("input[name='replyer']").val(replyer);
			modalInputReplyDate.closest("div").hide();
			modal.find("button[id != 'modalCloseBtn']").hide();
							    	
			modalRegisterBtn.show();
							    	
			$(".modal").modal("show");
		});
		
		modalRegisterBtn.on("click",function(e){
			
			var reply = {
					reply: modalInputReply.val(),
					replyer : modalInputReplyer.val(),
					bno: bnoValue
			};
			replyService.add(reply, function(result){
				alert(result);
				
				modal.find("input").val("");
				modal.modal("hide");
				
				//showList(1);
				showList(1);
			});
		});
		
		
		//댓글 조회 클릭 이벤트 처리 
	   $(".chat").on("click", "li", function (e) {

        var rno = $(this).data("rno");

        replyService.get(rno, function (reply) {
            modalInputReply.val(reply.reply);
            modalInputReplyer.val(reply.replyer);
            modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly","readOnly");
            modal.data("rno",reply.rno);

            modal.find("button[id !='modalClosenBtn']").hide();
            modalModBtn.show();
            modalRemoveBtn.show();

            $(".modal").modal("show");
        })
    });
		
		//댓글 페이징
	    var pageNum = 1;
	    var replyPageFooter = $(".panel-footer");
	    
	    function showReplyPage(replyCnt){
	      
	      var endNum = Math.ceil(pageNum / 10.0) * 10;  
	      var startNum = endNum - 9; 
	      
	      var prev = startNum != 1;
	      var next = false;
	      
	      if(endNum * 10 >= replyCnt){
	        endNum = Math.ceil(replyCnt/10.0);
	      }
	      
	      if(endNum * 10 < replyCnt){
	        next = true;
	      }
	      
	      var str = "<ul class='pagination pull-right'>";
	      
	      if(prev){
	        str+= "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
	      }
	      
	       
	      
	      for(var i = startNum ; i <= endNum; i++){
	        
	        var active = pageNum == i? "active":"";
	        
	        str+= "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
	      }
	      
	      if(next){
	        str+= "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li>";
	      }
	      
	      str += "</ul></div>";
	      
	      console.log(str);
	      
	      replyPageFooter.html(str);
	    }
	    
	    replyPageFooter.on("click","li a", function(e){
	    	e.preventDefault();
	    	console.log("page click");
	    	
	    	var targetPageNum = $(this).attr("href");
	    	
	    	console.log("targetPageNum: "+ targetPageNum);
	    	
	    	pageNum = targetPageNum;
	    	
	    	showList(pageNum);
	    });
	    
	   
	    modalModBtn.on("click", function(e){
	    	
	    	var originalReplyer = modalInputReplyer.val();
	    	
	    	console.log("reply:"+reply)
	    	
	    	var reply = {rno:modal.data("rno"), reply: modalInputReply.val(), replyer: originalReplyer};
	    	

	    	if(!replyer){
	    		alert("로그인 후 수정이 가능합니다.");
	    		modal.modal("hide");
	    		return;
	    	}
	    	
	    	console.log("Original Replyer:" + originalReplyer); //댓글의 원래 작성자
	    	
	    	if(replyer != originalReplyer){
	    		
	    		alert("자신이 작성한 댓글만 수정이 가능합니다.");
	    		modal.modal("hide");
	    		return;
	    	}
	    	
	    	replyService.update(reply, function(result){
	    		
	    		alert(result);
	    		modal.modal("hide");
	    		showList(pageNum);
	    	});
	    	
	    });// end modmodal
	    
	    modalRemoveBtn.on("click",function(e){
	    	var rno = modal.data("rno");
	    	
	    	if(!replyer){
	    		alert("로그인 후 삭제가 가능합니다.");
	    		modal.modal("hide");
	    		return;
	    	}
	    	
	    	var originalReplyer = modalInputReplyer.val();
	    	
	    	console.log("Original Replyer:" + originalReplyer); //댓글의 원래 작성자
	    	
	    	if(replyer != originalReplyer){
	    		
	    		alert("자신이 작성한 댓글만 삭제가 가능합니다.");
	    		modal.modal("hide");
	    		return;
	    	}
	    	
	    	replyService.remove(rno,originalReplyer, function(result){
	    	
	    		alert(result)
	    		modal.modal("hide")
	    		showList(pageNum);
	    		
	    	});
	    });
		
		
});// end ready
	
</script>


<!-- 게시글 script -->
<script>
	//리스트버튼
	$("#listBtn").on("click", function(e) {

		e.preventDefault()
		$("#actionForm").attr("action", "/board/list")
		$("#actionForm").attr("method", "get")
		$("#actionForm").submit()
	})

	//delete 처리
	$(document).ready(function() {

		$("#delBtn").on("click",function(e) {

			e.preventDefault()

			const bno = $('input[name="bno"]').val()
				console.log(bno)

			$("#actionForm").append("<input type='hidden' name='bno' value='"+bno+"'>")
			$("#actionForm").attr("action","/board/remove")
			$("#actionForm").attr("method","post")
			$("#actionForm").submit()

			})//del end
	})//doc end

	//수정버튼
	$("#modBtn").on("click",function(e) {

			e.preventDefault()

			const bno = $('input[name="bno"]').val()
			console.log(bno)

			$("#actionForm").append("<input type='hidden' name='bno' value='"+bno+"'>")
			$("#actionForm").submit()
	})
</script>

<%@include file="../includes/footer.jsp"%>

