package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Comment;
import model.KicBoard;

public interface BoardAnno {
	
	@Insert("insert into kicboard values (kicboardseq.nextval,#{name},#{pass},#{subject},#{content},#{file1},sysdate,0,#{boardid})")
	public int insertBoard(KicBoard kicboard);
	
	@Select("select * from (select rownum rn, a.* from kicboard a where boardid=#{boardid} order by num desc)"
			+ " where rn between #{num1} and #{num2}")
	public List<KicBoard> boardList(Map map);
	
	@Select("select nvl(count(*),0) from kicBoard where boardid = #{boardid}")
	public int boardCount(String boardid);
	
	@Update("update kicboard set readcnt = readcnt+1 " + " where num = #{num}")
	public int addReadCount(int num);
	
	@Insert("insert into boardcomment values (boardcomseq.nextval,#{boardnum},#{content},sysdate)")
	public int insertComment(Map map);
	
	@Select("select nvl(count(*),0) from boardcomment where num = #{boardnum}")
	public int getCommentCount(int boardnum);
	
	@Select("select * from boardcomment where num = #{boardnum} order by regdate desc")
	public List<Comment> commentList(int boardnum);
	
	@Select("select * from kicBoard where num = #{num}")
	public KicBoard getBoard(int num);
	
	@Update("update kicboard set name=?, subject=?, content=?, file1=?")
	public int boardUpdate(KicBoard board);
	
	@Delete("delete from kicboard where num=#{num}")
	public int boardDelete(int num);
}
