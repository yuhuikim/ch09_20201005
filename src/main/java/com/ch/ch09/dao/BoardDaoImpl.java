package com.ch.ch09.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ch.ch09.model.Board;

@Repository
public class BoardDaoImpl implements BoardDao {
	@Autowired
	private SqlSessionTemplate sst;

//	@Override
//	public List<Board> list(int startRow, int endRow) {
//
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		map.put("startRow", startRow);
//		map.put("endRow", endRow);
//		// selectList는 값을 한 개만 보낼 수 있다. 그러므로 Map을 이용해준다.
//		// return sst.selectList("boardns.list", startRow, endRow);
//		return sst.selectList("boardns.list", map);
//
//	}

	@Override
	public List<Board> list(Board board) {
		return sst.selectList("boardns.list", board);
	}

	@Override
	public int insert(Board board) {
		return sst.insert("boardns.insert", board);
	}

	@Override
	public void updateReadCount(int num) {
		sst.update("boardns.updateReadCount", num);
	}

	@Override
	public Board select(int num) {
		return sst.selectOne("boardns.select", num);
	}

	@Override
	public int getTotal(Board board) {
		return sst.selectOne("boardns.getTotal",board);
	}

	@Override
	public int update(Board board) {
		return sst.update("boardns.update", board);
	}

	@Override
	public int delete(int num) {
		// return sst.delete("boardns.delete", num);
		return sst.update("boardns.delete", num); // 삭제하지 않고 숨겨놓기만 하기 하려고 update로 한다.
	}

	@Override
	public int maxNum() {
		return sst.selectOne("boardns.maxNum");
	}

	@Override
	public void updateRe_step(Board board) {
		sst.update("boardns.updateRe_step", board);
	}

}