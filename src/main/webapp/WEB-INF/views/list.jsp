<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container" align="center">
		<h2 class="text-primary">게시글 목록</h2>
		<table class="table table-striped table-bordered">
			<tr>
				<td>번호</td>
				<td>제목</td>
				<td>작성자</td>
				<td>작성일</td>
				<td>조회수</td>
			</tr>
			<c:if test="${empty list }">
				<tr>
					<td colspan="5">데이터가 없습니다</td>
				</tr>
			</c:if>
			<c:if test="${not empty list }">
				<c:forEach var="board" items="${list}">
					<tr>
						<td>${board.num }</td>
						<c:if test="${board.del == 'y' }">
							<td colspan="4">삭제된 글 입니다</td>
						</c:if>
						<c:if test="${board.del != 'y' }">
							<td title="${board.content }">
								<!-- 	답변글이면  --> <c:if test="${board.re_level > 0 }">
									<!-- 레벨만큼 들여쓰기 -->
									<img alt="" src="images/level.gif" height="5"
										width="${board.re_level*10 }">
									<img alt="" src="images/re.gif">
								</c:if> <c:if test="${board.readcount > 30}">
									<img alt="" src="images/hot.gif">
								</c:if> <a href="view.do?num=${board.num}&pageNum=${pb.currentPage}"
								class="btn btn-sm btn-success">${board.subject }</a>
							</td>
							<td>${board.writer }</td>
							<td>${board.reg_date}</td>
							<td>${board.readcount }</td>
						</c:if>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<div align="center">
			<ul class="pagination">
				<c:if test="${pb.startPage > pb.pagePerBlock}">
					<li><a
						href="list.do?pageNum=1&search=${board.search}&keyword=${board.keyword}">
							<span class="glyphicon glyphicon-backward"></span>
					</a></li>
					<li><a
						href="list.do?pageNum=${pb.startPage-1}&search=${board.search}&keyword=${board.keyword}">
							<span class="glyphicon glyphicon-triangle-left"></span>
					</a></li>
				</c:if>
				<c:forEach var="i" begin="${pb.startPage}" end="${pb.endPage}">
					<c:if test="${i==pb.currentPage}">
						<li class="active"><a
							href="list.do?pageNum=${i}&search=${board.search}&keyword=${board.keyword}">${i}</a></li>
					</c:if>
					<c:if test="${i!=pb.currentPage}">
						<li><a
							href="list.do?pageNum=${i}&search=${board.search}&keyword=${board.keyword}">${i}</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${pb.endPage < pb.totalPage }">
					<li><a
						href="list.do?pageNum=${pb.endPage+1}&search=${board.search}&keyword=${board.keyword}">
							<span class="glyphicon glyphicon-triangle-right"></span>
					</a></li>
					<li><a
						href="list.do?pageNum=${pb.totalPage}&search=${board.search}&keyword=${board.keyword}">
							<span class="glyphicon glyphicon-forward"></span>
					</a></li>
				</c:if>
			</ul>
		</div>
		<form action="list.do">
			<input type="hidden" name="pageNum" value="1"> <select
				name="search">
				<c:forTokens var="item" items="writer,subject,content,subcon" delims=","
					varStatus="i">
					<c:if test="${board.search==item }">
						<option value="${item }" selected="selected">${tit[i.index]}</option>
					</c:if>

					<c:if test="${board.search!=item }">
						<option value="${item }">${tit[i.index]}</option>
					</c:if>

				</c:forTokens>

				<!-- 	<option value="writer">이름</option>
				<option value="subject">제목</option>
				<option value="content">내용</option> -->
			</select> <input type="text" name="keyword" value="${board.keyword }">
			<input type="submit" value="확인">
		</form>
		<a href="insertForm.do" class="btn btn-info">게시글 입력</a>
	</div>
</body>
</html>