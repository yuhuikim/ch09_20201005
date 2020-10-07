package com.ch.ch09.dao;

import java.util.List;
import com.ch.ch09.model.Board;

public interface BoardDao {
	// List<Board> list(int startRow, int endRow);
	List<Board> list(Board board);

	int insert(Board board);

	void updateReadCount(int num);

	Board select(int num);

	int getTotal(Board board);

	int update(Board board);

	int delete(int num);

	int maxNum();

	void updateRe_step(Board board);

}