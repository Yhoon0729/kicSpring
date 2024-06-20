package controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import model.KicMember;
import util.MybatisConnection;

public class Test {
	public static void main(String[] args) {
		SqlSession session = MybatisConnection.getConnection();
		String namespace = "xml.member.";
		
		System.out.println("count====");
		int num = session.selectOne(namespace+"count");
		System.out.println(num);
		
		System.out.println("list====");
		List<KicMember> li = session.selectList(namespace+"memberList");
		System.out.println(li);
	}
}
