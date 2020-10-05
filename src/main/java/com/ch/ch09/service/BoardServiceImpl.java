package com.ch.ch09.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ch.ch09.dao.BoardDao;
import com.ch.ch09.model.Board;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao bd;

	public List<Board> list() {
		return bd.list();
	}

	public int insert(Board board) {
		return bd.insert(board);
	}

	public void updateReadCount(int num) {
		bd.updateReadCount(num);
	}

	public Board select(int num) {
		return bd.select(num);
	}
}