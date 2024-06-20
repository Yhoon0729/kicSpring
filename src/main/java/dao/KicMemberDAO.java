package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.KicMember;

public class KicMemberDAO {
	// DAO(Data Access Object)
	
	public Connection getConnection() {
		// 1. driver
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "kic24", "1234");
			return conn;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// 2. connection
		catch (SQLException e) {
			e.printStackTrace();
		} // end of tryCatch
		
		return null;
		
	} // end of geConnection()
	
	public KicMember getMember(String id) {
		Connection conn = getConnection();
		// 3. PreparedStatement
		PreparedStatement pstmt = null;

		String sql = "select * from kicmember where id = ?";

		// 4. mapping
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				KicMember m = new KicMember();
				// id 있음
				m.setId(rs.getString("id"));
				m.setPass(rs.getString("pass"));
				m.setName(rs.getString("name"));
				m.setGender(rs.getInt("gender"));
				m.setTel(rs.getString("tel"));
				m.setEmail(rs.getString("email"));
				m.setPicture(rs.getString("picture"));
				
				return m;
			} else {
				return null;
			} // end of if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} // end of tryCatch
		
		return null;  
	} // end of getMemeber()
	
	public int insertMember(KicMember kic) {
		Connection conn = getConnection();
		// 3. PreparedStatement
		PreparedStatement pstmt = null;
		
		String sql = "insert into kicmember values (?,?,?,?,?,?,?)";
		
		// 4. mapping
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kic.getId());
			pstmt.setString(2, kic.getPass());
			pstmt.setString(3, kic.getName());
			pstmt.setInt(4, kic.getGender());
			pstmt.setString(5, kic.getTel());
			pstmt.setString(6, kic.getEmail());
			pstmt.setString(7, kic.getPicture());
			// sql 실행
			int num = pstmt.executeUpdate();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} // end of tryCatch
		
		return 0;
	} // end of insertMember()
	
	public int updateMember(KicMember kic) {
		Connection conn = getConnection();
		// 3. PreparedStatement
		PreparedStatement pstmt = null;
		
		String sql = "update kicmember set name=?, gender=?, tel=?, email=?, picture=? where id = ?";
		
		// 4. mapping
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kic.getName());
			pstmt.setInt(2, kic.getGender());
			pstmt.setString(3, kic.getTel());
			pstmt.setString(4, kic.getEmail());
			pstmt.setString(5, kic.getPicture());
			pstmt.setString(6, kic.getId());
			// sql 실행
			int num = pstmt.executeUpdate();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} // end of tryCatch
		
		return 0;
	} // end of updateMember()
	
	public int deleteMember(String id) {
		Connection conn = getConnection();
		// 3. PreparedStatement
		PreparedStatement pstmt = null;
		
		String sql = "delete from kicmember where id = ?";
		
		// 4. mapping
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// sql 실행
			int num = pstmt.executeUpdate();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} // end of tryCatch
		
		return 0;
	} // end of deleteteMember()
	
	
	public int modifyPass(String id, String modPass) {
		Connection conn = getConnection();
		// 3. PreparedStatement
		PreparedStatement pstmt = null;
		
		String sql = "update kicmember set pass = ? where id = ?";
		
		// 4. mapping
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, modPass);
			pstmt.setString(2, id);
			
			
			// sql 실행
			int num = pstmt.executeUpdate();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} // end of tryCatch
		
		return 0;
	} // end of deleteteMember()
	
	public List<KicMember> memberList() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "select * from kicmember";
		
		List<KicMember> li = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				KicMember m = new KicMember();
				m.setId(rs.getString("id"));
				m.setPass(rs.getString("pass"));
				m.setName(rs.getString("name"));
				m.setGender(rs.getInt("gender"));
				m.setTel(rs.getString("tel"));
				m.setEmail(rs.getString("email"));
				m.setPicture(rs.getString("picture"));
				li.add(m);
			} // end of while(rs.next())
			
			return li;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} // end of tryCatch
		
	} // end of memberList()
	
} // end of class
