<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>


<style>
.uploadResult{
	width:100%;
	background-color: gray;
}

.uploadResult ul{
	display:flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li{
	list-style: none;
	padding: 10px;
	align-content: center;
	text-align: center;
}

.uploadResult ul li img{
	width: 100px;
}

.uploadResult ul li span{
	color: white;
}

.bigPictureWrapper{
	position: absolute;
	display: none;
	justify-content: center;
	align-items: center;
	top: 0%;
	width: 100%;
	height: 100%;
	background-color: gray;
	z-index: 100;
	background: rgba(255,255,255,0.5);
}

.bigPicture{
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img{
	width: 600px;
}

</style>

  <!-- Page Heading -->
  <h1 class="h3 mb-4 text-gray-800">Register</h1>

<div class="col-md-12">
    <div class="col-md-4">
        <form role="form" action="/board/register" method="post">
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" class="form-control" name="title" placeholder="제목을 입력하세요">
            </div>
            <div class="form-group">
                <label for="content"> 내용 </label>
                <textarea class="form-control" name="content" placeholder="내용을 입력하세요"></textarea>
            </div>
            <div class="form-group">
                <label for="author"> 작성자 </label>
                <input type="text" class="form-control" name="writer" placeholder="작성자를 입력하세요"
                value='<sec:authentication property="principal.username"/>' readonly="readonly">
            </div>
            <div class="form-group">
                <label for="author"> 파일 </label>
                <input type="file" name="uploadFile" multiple="multiple">
            </div>
            
            <div class="uploadResult">
            	<ul>
            	
            	</ul>
            </div>
            
        <button type="submit" class="btn btn-primary" id="regBtn">등록</button>
        <button type="reset" class="btn btn-primary" id="btn-save">리셋</button>
        </form>
    </div>
</div>


<script>
$(document).ready(function(e){
	
	var formObj = $("form[role='form']");
	
	$("#regBtn").on("click",function(e){
	
		e.preventDefault();
		
		console.log("submit clicked")
		
		var str = "";
		
		$(".uploadResult ul li").each(function(i,obj){
			
			var jobj = $(obj);
			
			console.dir(jobj);
			
			str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
			
		}); 
		
		formObj.append(str).submit();
	});
	
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
	
	$(".uploadResult").on("click", "button", function(e){
		
		console.log("Del")
		
		var targetFile = $(this).data("file");
		var type = $(this).data("type");
		
		var targetLi = $(this).closest("li");
		
		$.ajax({
			
			url: '/deleteFile',
			data: {fileName : targetFile, type:type},
			dataType : 'text',
			type : 'POST',
				success: function(result){
			
				alert(result);
				targetLi.remove();
				
			}
			
		});// end ajax
	});//button click


}); //end dom

</script>
                
<%@include file="../includes/footer.jsp"%>

  