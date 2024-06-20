package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mapper.BoardAnno;
import model.Comment;
import model.KicBoard;

@Component
public class KicBoardMybatis {
	
	@Autowired
	SqlSessionTemplate session;
	
	public int insertBoard(KicBoard kicboard) {
		int num = session.getMapper(BoardAnno.class).insertBoard(kicboard);
		session.commit();
		return num;
	}
	
	public List<KicBoard> boardList(String boardid, int pageInt, int limit) {
		Map map = new HashMap();
		map.put("boardid", boardid);
		map.put("num1", ((pageInt - 1) * limit + 1));
		map.put("num2", pageInt * limit);
		
		List<KicBoard> li = session.getMapper(BoardAnno.class).boardList(map);
		return li;
	}
	
	public int boardCount(String boardid) {
		int num = session.getMapper(BoardAnno.class).boardCount(boardid);
		return num;
	}
	
	public int addReadCount(int num) {
		int count = session.getMapper(BoardAnno.class).addReadCount(num);
		session.commit();
		return count;
	}
	
	public int insertComment(int boardnum, String comment) {
		Map map = new HashMap();
		map.put("boardnum", boardnum);
		map.put("content", comment);
		
		int num = session.getMapper(BoardAnno.class).insertComment(map);
		session.commit();
		return num;
	}
	
	public int getCommentCount(int boardnum) {
		int num = session.getMapper(BoardAnno.class).getCommentCount(boardnum);
		return num;
	}
	
	public List<Comment> commentList(int boardnum) {
		List<Comment> li = session.getMapper(BoardAnno.class).commentList(boardnum);
		return li;
	}
	
	public KicBoard getBoard(int num) {
		KicBoard board = session.getMapper(BoardAnno.class).getBoard(num);
		return board;
	}
	
	public int boardUpdate(KicBoard board) {
		int num = session.getMapper(BoardAnno.class).boardUpdate(board);
		session.commit();
		return num;
	}
	
	public int boardDelete(int num) {
		int count = session.getMapper(BoardAnno.class).boardDelete(num);
		session.commit();
		return count;
	}
	
}
