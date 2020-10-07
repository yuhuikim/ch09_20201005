package com.ch.ch09.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.ch09.model.Board;
import com.ch.ch09.service.BoardService;
import com.ch.ch09.service.PagingBean;

@Controller
public class BoardController {
	@Autowired
	private BoardService bs;

	@RequestMapping("list")
	// 게시글을 읽어서 보내려는 목적
	public String list(Board board, String pageNum, Model model) { // ++

		int rowPerPage = 10;

		// 페이지가 지정되지 않으면 1페이지를 기본으로 보여주기 위해서 만듦
		if (pageNum == null || pageNum.equals("")) { // 숫자는 null하고 비교가 안되기 때문에 String pageNum으로 선언을 해줌 // ++
			pageNum = "1"; //
		}
		int currentPage = Integer.parseInt(pageNum); // String이었던 pageNum이 int 형으로 바뀐다. // ++

		// 게시글이 총 몇 개인지 알아야 페이징 처리를 할 수 있기 때문에 total을 생성해준다.
		// int total = bs.getTotal();
		int total = bs.getTotal(board);

		// ﻿시작번호 = (페이지 번호 -1) * 페이지당 개수 +1
		int startRow = (currentPage - 1) * rowPerPage + 1;

		// ﻿끝 번호 = 시작 번호 + 페이지당 개수 - 1
		int endRow = startRow + rowPerPage - 1;

		board.setStartRow(startRow);
		board.setEndRow(endRow);

		// List<Board> list = bs.list(startRow, endRow);
		// board에는 search, keyword, startRow, endRow가 들어있다.
		List<Board> list = bs.list(board);

		PagingBean pb = new PagingBean(currentPage, rowPerPage, total);

		// writer, subject, content라고 되어있는 것을 한글로 바꾸기 위해서 생성
		String[] tit = {"작성자", "제목","내용","제목+내용"};
		model.addAttribute("tit",tit);
		
		model.addAttribute("list", list);
		model.addAttribute("board", board); // board값을 보내줘야 페이지가 바뀌어도 유지된다.
		model.addAttribute("pb", pb);
		return "list";
	}

	@RequestMapping("insertForm")
	public String insertForm(String nm, String pageNum, Model model) { // ++
		// 답변용 ref, re_level, re_step
		int ref = 0, re_level = 0, re_step = 0, num = 0; // 답변글이 아님

		if (nm != null && !nm.equals("")) { // 널도 아니고 뭐도 없다는 뜻 --> 답변글이라는 뜻임!
			num = Integer.parseInt(nm); // 답변
			Board board = bs.select(num);

			// 원본글의 ref, re_level, re_step가 답변글이 아니면
			// 모두 0,0,0으로 채워질텐데 답변글이면 아래와 같이 값이 채워진다.
			ref = board.getRef();
			re_level = board.getRe_level();
			re_step = board.getRe_step();
		}
		model.addAttribute("ref", ref);
		model.addAttribute("re_level", re_level);
		model.addAttribute("re_step", re_step);
		model.addAttribute("num", num);
		model.addAttribute("pageNum", pageNum); // ++
		return "insertForm";
	}

	@RequestMapping("insert")
	public String insert(Board board, Model model, HttpServletRequest request, String pageNum) { // ++
		board.setIp(request.getLocalAddr()); // ip setting
		int number = bs.maxNum();
		if (board.getNum() != 0) { // 답변글
			// re_step을 정리
			bs.updateRe_step(board);
			board.setRe_level(board.getRe_level() + 1);
			board.setRe_step(board.getRe_step() + 1);
		} else {
			board.setRef(number); // 답변이 아닐 때는 ref와 num의 number는 같다. //board.setNum(number); == //
									// board.setRef(number);
		}
		board.setNum(number);
		int result = bs.insert(board);
		model.addAttribute("result", result);
		model.addAttribute("pageNum", pageNum); // ++
		return "insert";
	}

	@RequestMapping("view")
	public String view(int num, Model model, String pageNum) { // ++
		bs.updateReadCount(num);
		Board board = bs.select(num);
		model.addAttribute("board", board);

		// 보던 페이지 그대로 유지해서 돌아가게 하기 위해서
		model.addAttribute("pageNum", pageNum); // ++

		return "view";
	}

	@RequestMapping("updateForm")
	public String updateForm(int num, Model model, String pageNum) { // ++
		Board board = bs.select(num);
		model.addAttribute("board", board);
		model.addAttribute("pageNum", pageNum); // ++
		return "updateForm";
	}

	@RequestMapping("update")
	public String update(Board board, String pageNum, Model model) { // ++
		int result = bs.update(board);
		model.addAttribute("result", result);
		model.addAttribute("board", board);
		model.addAttribute("pageNum", pageNum); // ++
		return "update";
	}

	@RequestMapping("insertBoard")
	public String updateForm(HttpServletRequest request) {

		String ip = request.getLocalAddr();

		for (int i = 1; i <= 230; i++) { // 자동으로 230개를 추가해준다.
			Board board = new Board();
			board.setSubject("자동 제목 생성" + i);
			board.setWriter("관리자" + i);
			board.setEmail("admin" + i + "@naver.com");
			board.setPassword("0000");
			board.setContent("자동 내용 생성" + i);
			board.setIp(ip);
			bs.insert(board);
		}
		return "insertBoard";
	}

	@RequestMapping("deleteForm")
	public String deleteForm(int num, String pageNum, Model model) { // ++
		Board board = bs.select(num);
		model.addAttribute("board", board);
		model.addAttribute("pageNum", pageNum); // ++
		return "deleteForm";
	}

	@RequestMapping("delete")
	public String delete(int num, String pageNum, Model model) { // ++
		int result = bs.delete(num);
		model.addAttribute("result", result);
		model.addAttribute("pageNum", pageNum); // ++
		return "delete";
	}
}
