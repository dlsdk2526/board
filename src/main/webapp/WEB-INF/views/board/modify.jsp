<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Page Heading -->
<h1 class="h3 mb-4 text-gray-800">Board</h1>

<div class="col-md-12">
	<div class="col-md-4">
		<form id='modForm' action="/board/modify" method='post'>
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
					value='<c:out value="${board.writer }"/>'>
			</div>
			<input type="hidden" name="page" value="${pageDTO.page }"> 
			<input type="hidden" name="perSheet" value="${pageDTO.perSheet }">
			<input type="hidden" name="bno" value="${board.bno }">
			<button type="button" class="btn btn-primary" id="okBtn">확인</button>
		</form>
	</div>

</div>


<script>
	$(document).ready(function() {

		$("#okBtn").on("click", function(e) {
			$("#modForm").submit()
		})

	})
</script>

<%@include file="../includes/footer.jsp"%>

