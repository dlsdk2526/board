<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!-- 모달 -->
<div class="modal" tabindex="-1" role="dialog" id="myModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Modal title</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<p>처리가 완료되었습니다.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>


<!-- 리스트 테이블 -->
<h1 class="h3 mb-4 text-gray-800">Tables</h1>

<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">Board List Page</h6>
	</div>
	<div class="card-body">
		<div class="table-responsive">
			<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
				<thead>
					<tr>
						<th>#번호</th>
						<th>제목</th>
						<th>내용</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>수정일</th>
					</tr>
				</thead>

				<c:forEach items="${list }" var="board">
					<tbody class="blist">
						<tr>
							<td><c:out value="${board.bno }" /></td>
							<td><a href='<c:out value="${board.bno }"/>'><c:out value="${board.title }" />
							<b>...[<c:out value="${board.replyCnt }"/>]</b>
							</a></td>
							<td><a href='<c:out value="${board.bno }"/>'><c:out value="${board.content }" /></a></td>
							<td><c:out value="${board.writer }" /></td>
							<td>${board.regdate }</td>
							<td>${board.updatedate }</td>
					</tbody>
				</c:forEach>
			</table>
		</div>
	</div>
</div>

<div>
<form id="searchForm" action="/board/list" method="get">
 <select name="type">
	 <option value="">---</option>
	 <option value="T" ${pageDTO.type == "T"?"selected":""}>제목</option>
	 <option value="C" ${pageDTO.type == "C"?"selected":""}>내용</option>
	 <option value="W" ${pageDTO.type == "W"?"selected":""}>작성자</option>
	 <option value="TC" ${pageDTO.type == "TC"?"selected":""}>제목 or 내용</option>
	 <option value="TW" ${pageDTO.type == "TW"?"selected":""}>제목 or 작성자</option>
	 <option value="TWC" ${pageDTO.type == "TWC"?"selected":""}>제목 or 내용 or 작성자</option> 
 </select>
	<input type="text" name="keyword" value="${pageDTO.keyword }"/>
 	<input type="hidden" name="page" value="${pageDTO.page }">
	<input type="hidden" name="perSheet" value="${pageDTO.perSheet }">
	<input type="hidden" name="type" value="${pageDTO.type }"/>
	<button class="btn btn-default">search</button>
</form>
</div>


<div>
<ul class="pagination">
	<c:if test="${pageMaker.prev }">
		<li class="page-item"><a class="page-link"
			href="${pageMaker.start-1 }" tabindex="-1">Previous</a></li>
	</c:if>

	<c:forEach begin="${pageMaker.start }" end="${pageMaker.end}" var="num">
		<li class="page-item ${num == pageMaker.pageInfo.page?"active":"" }"><a
			class="page-link" href="${num }">${num }</a></li>
	</c:forEach>

	<c:if test="${pageMaker.next}">
		<li class="page-item"><a class="page-link"
			href="${pageMaker.end +1}">Next</a></li>
	</c:if>
</ul>
</div>

<form id="actionForm" action="/board/list" method="get">
	<input type="hidden" name="page" value="${pageDTO.page }">
	<input type="hidden" name="perSheet" value="${pageDTO.perSheet }">
	<input type="hidden" name="type" value="${pageDTO.type }"/>
	<input type="hidden" name="keyword" value="${pageDTO.keyword }"/>
</form>
	
	
<script>
 	
 	//등록
 	$(document).ready(function(){
 		
 		var result = '<c:out value="${result}"/>'
 		
 		console.log(result)
 		
 		checkModal(result);
 		
 		history.replaceState({},null,null)
 		
 		function checkModal(result){
 			if(result === '' || history.state){
 				return
 			}
 			
 			if(parseInt(result) > 0){
 				$(".modal-body").html("게시글" + parseInt(result)+ "번이 등록되었습니다.")
	 		}
 			
 			$("#myModal").modal("show")
 		}
 		
 	})
 	
//페이징
 $(document).ready(function(){
	 
 	$(".pagination").on("click",function(e){
 		
 		e.preventDefault()
 		const target = e.target
 		const pageNum = target.getAttribute("href")
 		console.log(pageNum)
 		
 	    $("#actionForm").find("input[name='page']").val(pageNum)
 		$("#actionForm").submit()
 		
 	})
 })	
 
 //list
 $(".blist").on("click",function(e){
	 e.preventDefault()
	 const target = e.target
	 console.log(target)

	 const bno = target.getAttribute("href")
	 console.log(bno)
	 
	 if(bno === null){
			return
		}
	 
	 $("#actionForm").append("<input type='hidden' name='bno' value='"+bno+"'>")
	 $("#actionForm").attr("action","/board/get")
	 $("#actionForm").submit()
	 
 })
 
 
 //검색
 const searchForm = $("#searchForm")
 
 $("#searchForm button").on("click", function(e){

	 if(!searchForm.find("option:selected").val()){
		 alert("검색종류를 선택하세요")
		 return false
	 }

	 if(!searchForm.find("input[name='keyword']").val()){
		 alert("키워드를 입력하세요")
		 return false
	 }
	 
	 searchForm.find("input[name='page']").val("1")
	 e.preventDefault()
	 
	 searchForm.submit()
	 
 })
</script>




<%@include file="../includes/footer.jsp"%>















