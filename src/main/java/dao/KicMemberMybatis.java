package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mapper.MemberAnno;
import model.KicMember;


@Component
public class KicMemberMybatis {
	
	@Autowired
	SqlSessionTemplate session;
	
	public KicMember getMember(String id) {
		KicMember mem = session.getMapper(MemberAnno.class).getMember(id);
		return mem;
	} // end of getMemeber()
	
	public List<KicMember> memberList() {
		List<KicMember> li = session.getMapper(MemberAnno.class).memberList();
		return li;
	} // end of memberList()

	public int insertMember(KicMember kic) {
		int num = session.getMapper(MemberAnno.class).insertMember(kic);
		session.commit();
		return num;
	} // end of insertMember()

	public int updateMember(KicMember kic) {
		int num = session.getMapper(MemberAnno.class).updateMember(kic);
		session.commit();
		return num;
	}
	
	public int deleteMember(String id) {
		int num = session.getMapper(MemberAnno.class).deleteMember(id);
		session.commit();
		return num;
	}
	
	public int modifyPass(String id, String modPass) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("modPass", modPass);
		
		int num = session.getMapper(MemberAnno.class).modifyPass(map);
		session.commit();
		return num;
	}
}
