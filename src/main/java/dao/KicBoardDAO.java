package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.KicBoard;
import model.KicMember;

public class KicBoardDAO {
	public Connection getConnection() {
		// 1. driver
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "kic24", "1234");
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 2 connection
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public int insertBoard(KicBoard board) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into kicboard values (kicboardseq.nextval,?,?,?,?,?,sysdate,0,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getPass());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getFile1());
			pstmt.setString(6, board.getBoardid()); // boardid

			int num = pstmt.executeUpdate();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	} // end of insertBoard()

	public int insertComment(String comment, int boardnum) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into boardcomment values (boardcomseq.nextval,?,?,sysdate)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardnum);
			pstmt.setString(2, comment);

			int num = pstmt.executeUpdate();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	} // end of insertComment()

	public int getCommentCount(int boardnum) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "select nvl(count(*),0) from boardcomment where num = ?"; // 1
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardnum);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	} // end of getCommentCount()
	
	public List<Comment> commentList(int boardnum) {
		Connection conn = getConnection();

		PreparedStatement pstmt = null;
		String sql = "	select * from boardcomment where num = ? order by regdate desc";
		List<Comment> li = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardnum);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Comment c = new Comment();
				c.setNum(rs.getInt("num"));
				c.setSer(rs.getInt("ser"));
				c.setContent(rs.getString("content"));
				c.setRegdate(rs.getTimestamp("regdate"));
				
				li.add(c);
			}
			return li;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} // end of commentList

	public List<KicBoard> boardList(String boardid, int pageInt, int limit) {
		Connection conn = getConnection();
		System.out.println(boardid);
		PreparedStatement pstmt = null;
		String sql = "	select *	"
				+ "	from (select rownum rn, a.* from kicboard a where boardid=? order by num desc)	"
				+ "	where rn between ? and ?	";
		List<KicBoard> li = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardid);
			pstmt.setInt(2, (pageInt - 1) * limit + 1);
			pstmt.setInt(3, pageInt * limit);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				KicBoard m = new KicBoard();
				m.setNum(rs.getInt("num"));
				m.setPass(rs.getString("pass"));
				m.setName(rs.getString("name"));
				m.setSubject(rs.getString("subject"));
				m.setContent(rs.getString("content"));
				m.setFile1(rs.getString("file1"));
				m.setRegdate(rs.getTimestamp("regdate"));
				m.setReadcnt(rs.getInt("readcnt"));
				m.setBoardid(rs.getString("boardid"));
				li.add(m);
			}
			return li;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} // end of boardList

	public int boardCount(String boardid) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "select nvl(count(*),0) from kicBoard where boardid = ?"; // 1
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public KicBoard getBoard(int num) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "select * from kicBoard where num = ?";
		// 4. mapping
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				KicBoard board = new KicBoard();
				// id 있음
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setFile1(rs.getString("file1"));
				board.setReadcnt(rs.getInt("readcnt"));

				return board;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int addReadCount(int num) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update kicboard set readcnt = readcnt+1 " + " where num = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			int count = pstmt.executeUpdate();
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public int boardUpdate(KicBoard board) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update kicboard set name=?, subject=?, content=?, file1=? " + " where num = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFile1());
			pstmt.setInt(5, board.getNum());
			int num = pstmt.executeUpdate();
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}

	public int boardDelete(int num) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "delete from kicboard where num=? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			int count = pstmt.executeUpdate();
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	} // end of boardDelete()

} // end class