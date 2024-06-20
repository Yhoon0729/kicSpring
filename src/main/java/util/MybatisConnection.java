package util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisConnection {
	private static SqlSessionFactory sqlmap;
	private MybatisConnection() {}
	
	static {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("util/mybatis-config.xml");
			sqlmap = new SqlSessionFactoryBuilder().build(reader);
			System.out.println("config ok");
		} catch(IOException e) {
			e.printStackTrace();
		} // end of tryCatch
	} // end of static
	
	public static SqlSession getConnection() {
		return sqlmap.openSession();
	} // end of getConnecton()
	
	public static void main(String[] args) {
		SqlSession session = getConnection();
		System.out.println(session);
	} // end of main()
} // end of class MybatisConnection
