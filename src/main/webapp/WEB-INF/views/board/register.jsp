<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
                <input type="text" class="form-control" name="writer" placeholder="작성자를 입력하세요">
            </div>
            <div class="form-group">
                <label for="author"> 파일 </label>
                <input type="file" name="uploadFile" multiple="multiple">
            </div>
            
            <div class="uploadResult">
            	<ul>
            	</ul>
            </div>
        <button type="submit" class="btn btn-primary" id="btn-save">등록</button>
        <button type="reset" class="btn btn-primary" id="btn-save">리셋</button>
        </form>
    </div>
</div>

                
<%@include file="../includes/footer.jsp"%>

  