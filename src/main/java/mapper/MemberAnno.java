package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.KicMember;

public interface MemberAnno {
	@Select("select * from kicmember where id = #{id}")
	KicMember getMember(String id);
	
	@Select("select * from kicmember")
	List<KicMember> memberList();
	
	@Insert("insert into kicmember values (#{id},#{pass},#{name},#{gender},#{tel},#{email},#{picture})")
	public int insertMember(KicMember kic);
	
	@Update("update kicmember set name=#{name}, gender=#{gender}, tel=#{tel}, email=#{email}, picture=#{picture} where id=#{id}")
	public int updateMember(KicMember kic);
	
	@Delete("delete from kicmember where id = #{id}")
	public int deleteMember(String id);
	
	@Update("update kicmember set pass = #{modPass} where id = #{id}")
	public int modifyPass(Map map);
}
