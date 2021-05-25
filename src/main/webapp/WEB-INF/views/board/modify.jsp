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


<!-- 게시글 수정부분  -->
<div class="col-md-12">
	<div class="col-md-4">
		<form id='modForm' action="/board/modify" method='post'>
		 <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
			<div class="form-group">
				<label for="title">글 번호</label> <input type="text"
					class="form-control" name="bno"
					value='<c:out value="${board.bno }"/>' readonly="readonly">
			</div>
			<div class="form-group">
				<label for="title">제목</label> <input type="text"
					class="form-control" name="title"
					value='<c:out value="${board.title }"/>'>
			</div>
			<div class="form-group">
				<label for="content"> 내용 </label>
				<textarea class="form-control" name="content"><c:out
						value="${board.content }" /></textarea>
			</div>
			<div class="form-group">
				<label for="author"> 작성자 </label> <input type="text"
					class="form-control" name="writer"
					value='<c:out value="${board.writer }"/>'readonly="readonly">
			</div>
			<input type="hidden" name="page" value="${pageDTO.page }"> 
			<input type="hidden" name="perSheet" value="${pageDTO.perSheet }">
			<input type="hidden" name="bno" value="${board.bno }">
			
			<sec:authentication property="principal" var="pinfo"/>
			<sec:authorize access="isAuthenticated()">
			<c:if test="${pinfo.username eq board.writer }">
			<button type="button" class="btn btn-primary" id="okBtn" data-oper='modify'>수정</button>
			</c:if>
			</sec:authorize>
		</form>
	</div>
</div>


<!-- 파일 수정부분 -->
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
			  <div class="form-group uploadDiv">
			  	<input type="file" name="uploadFile" multiple="multiple">
			  </div>	
		
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


<script>

$(document).ready(function() {
	
	//첨부파일 보여주는 작업 
	(function(){
		
		var bno = '<c:out value = "${board.bno}"/>';
		
		$.getJSON("/board/getAttachList", {bno:bno}, function(arr){
			console.log(arr);
			
			var str = "";
			
			$(arr).each(function(i, attach){
				
				//image type
				if(attach.fileType){
					
					var fileCallPath = encodeURIComponent( attach.uploadPath+ "/"+attach.uuid +"_"+attach.fileName);
					
					str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'><div>";
					str += "<span>"+ attach.fileName + "</span>";
					str += "<button type='button' data-file\'"+fileCallPath+"'\ data-type ='image'"
					str += "class = 'btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
					str += "<img src='/display?fileName="+fileCallPath+"'>";
					str += "</div>";
					str + "</li>";
					
				}else{
					
					str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'><div>";
					str += "<span>"+ attach.fileName + "</span>";
					str += "<button type='button' data-file\'"+fileCallPath+"'\ data-type ='image'"
					str += "class = 'btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
					str += "<img src='/resources/img/attach.png'>";
					str += "</div>";
					str + "</li>";
					
				}
			});
			
			$(".uploadResult ul").html(str);
			
		});//end JSON
	})();//end function

	//화면에서만 이미지 삭제
	$(".uploadResult").on("click", "button", function(e){
		
		console.log("delete file");
		
		if(confirm("Remove this file?")){
			var targetLi = $(this).closest("li");
			targetLi.remove();
		}
		
	});//end uploadResult
	
	//첨부파일 추가 
	
	//파일검사
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880;
	
	function checkExtension(fileName, fileSize){
		
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과");
			return false;
			
		}
		
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드 할 수 없습니다.")
			return false;
		}
		return true;
	}// end check

	//업로드
	$("input[type='file']").change(function(e){
		
		var formData = new FormData();
		
		var inputFile = $("input[name='uploadFile']");
		
		var files = inputFile[0].files;
		
		console.log(files);
		
		for(var i = 0; i < files.length; i++){
			
			if(!checkExtension(files[i].name,files[i].size)){
				return false;	
			}
			
			formData.append("uploadFile", files[i]);
		}//end for
		
		$.ajax({
			url: '/uploadAjaxAction',
			processData : false,
			contentType : false,
			data: formData,
			type : 'POST',
			dataType : 'json',
				success: function(result){
			
				console.log(result);
				
				showUploadResult(result);
			}
			
		});//end ajax
	});//end change
	
	//업로드결과 섬네일 처리
	function showUploadResult(uploadResultArr){
		
		if(!uploadResultArr || uploadResultArr.length == 0){return;}
		
		var uploadUL = $(".uploadResult ul");
		
		var str = "";
		
		$(uploadResultArr).each(function(i, obj){
			
			if (obj.image) {
				
				var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/"+obj.uuid +"_"+obj.fileName);
				
				str += "<li data-path='"+ obj.uploadPath+"'";
				str += "data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'"
				str += "><div>";
				str += "<span>" + obj.fileName+ "</span>";
				str += "<button type = 'button' data-file=\'"+fileCallPath+"\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
				str += "<img src='/display?fileName="+fileCallPath+"'>";
				str += "</div>";
				str += "</li>";
				
			}else{
				
				var fileCallPath = encodeURIComponent( obj.uploadPath+ "/"+obj.uuid +"_"+obj.fileName);
		        
				var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
				
				str += "<li"
				str += "data-path='"+obj.uploadPath+"' data-uuid'"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'><div>";
				str += "<span>" + obj.fileName+ "</span>";
				str += "<button type = 'button' data-file=\'"+fileCallPath+"\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
				str += "<img src = '/resources/img/attach.png'></a>";
				str += "</div>";
				str += "</li>";
				
			}
		}); //uploadResultArr

		uploadUL.append(str);

	}//showuploadResult
	
	
	var formObj = $("form")
	
	//modify submit
	$("button").on("click", function(e) {
		
		e.preventDefault();
		
		var operation = $(this).data("oper");
		
		console.log(operation);
		
		if(operation ==='modify'){
			
			console.log("submit clicked....")
			
			var str = "";
			
			$(".uploadResult ul li").each(function(i,obj){
				
				var jobj = $(obj);
				
				str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
				
			}); 
			
			formObj.append(str).submit();
			
		} //end if
		
		console.log(formObj)
		formObj.submit();
	}); //end okBtn

}); //end dom 
</script>

<%@include file="../includes/footer.jsp"%>

