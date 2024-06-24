package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dao.KicMemberMybatis;
import model.KicMember;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	Model m;
	HttpSession session;
	HttpServletRequest request;
	
	@Autowired
	KicMemberMybatis mybatisdao = new KicMemberMybatis();
	
	private boolean isLogin(HttpServletRequest request) {
		return session.getAttribute("id") != null;
	}
	
	@ModelAttribute
	protected void service(HttpServletRequest request, Model m)
			throws ServletException, IOException {
		session = request.getSession();
		this.request = request;
		this.m = m;
	}

	@RequestMapping("index")
	public String index() throws ServletException, IOException {
		request.setAttribute("nav", "index");
		return "index";
	} // end of index()

	@RequestMapping("join")
	public String join() throws ServletException, IOException {
		request.setAttribute("nav", "join");
		return "member/join";
	} // end of join()

	@RequestMapping("joinPro")
	public String joinPro(KicMember kic) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		int num = mybatisdao.insertMember(kic);

		String msg = "";
		String url = "join";

		if (num > 0) {
			msg = kic.getName() + "님의 회원가입이 완료되었습니다.";
			url = "login";
		} else {
			msg = "회원가입이 실패 하였습니다.";
		}

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);

		return "alert";
	} // end of joinPro()

	@RequestMapping("joinInfo")
	public String joinInfo() throws ServletException, IOException {
		String id = (String) session.getAttribute("id");
		// KicMemberDAO dao = new KicMemberDAO();
		KicMember mem = mybatisdao.getMember(id);

		request.setAttribute("mem", mem);
		request.setAttribute("nav", "joinInfo");
		return "member/joinInfo";
	} // end of joinInfo()

	@RequestMapping("login")
	public String login() throws ServletException, IOException {
		request.setAttribute("nav", "login");
		return "member/login";
	} // end of login()

	@RequestMapping("logout")
	public String logout() throws ServletException, IOException {
		session.invalidate();
		request.setAttribute("msg", "로그아웃 되었습니다.");
		request.setAttribute("url", "index");
		return "alert";
	} // end of logout()

	@RequestMapping("loginPro")
	public String loginPro(String id, String pass) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		// String id = request.getParameter("id");
		// String pass = request.getParameter("pass");


		// KicMemberDAO dao = new KicMemberDAO();

		String msg = id + "님으로 로그인 하셨습니다.";
		String url = "index";

		KicMember mem = mybatisdao.getMember(id);
		if (mem != null) {
			if (pass.equals(mem.getPass())) {
				session.setAttribute("id", id);
			} else {
				msg = "비밀번호가 틀렸습니다";
				url = "login";
			} // end of if (pass.equals(mem.getPass())))
		} else {
			msg = "존재하지 않는 아이디입니다.";
			url = "login";
		} // end of if (mem != null)

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	} // end of loginPro()
	
	@RequestMapping("memberUpdateForm")
	public String memberUpdateForm() throws ServletException, IOException {

		String id = (String) session.getAttribute("id");
		KicMember mem = mybatisdao.getMember(id);
		request.setAttribute("mem", mem);

		return "member/memberUpdateForm";
	} // end of memberUpdateForm()

	@RequestMapping("memberUpdatePro")
	public String memberUpdatePro(KicMember kic) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String id = (String) session.getAttribute("id");

		KicMember memdb = mybatisdao.getMember(id);

		kic.setId(id);

		String msg = "";
		String url = "memberUpdateForm";

		if (memdb != null) {
			if (memdb.getPass().equals(kic.getPass())) {
				msg = "수정 완료";
				mybatisdao.updateMember(kic);
				url = "joinInfo";
			} else {
				msg = "비밀번호가 틀렸습니다.";
				url = "memberUpdateForm";
			} // end of if (memdb.getPass().equals(pass))
		} else {
			msg = "수정할 수 없습니다.";
		} // end of if (memdb != null)

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);

		return "alert";
	} // end of memberUpdatePro()

	@RequestMapping("memberDeleteForm")
	public String memberDeleteForm() throws ServletException, IOException {
		return "member/memberDeleteForm";
	} // end of memberDeleteForm()

	@RequestMapping("memberDeletePro")
	public String memberDeletePro(String pass) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String id = (String) session.getAttribute("id");

		KicMember memdb = mybatisdao.getMember(id);

		String msg = "";
		String url = "memberDeleteForm";

		if (memdb != null) {
			if (memdb.getPass().equals(pass)) {
				msg = "탈퇴 완료";
				session.invalidate();
				mybatisdao.deleteMember(id);
				url = "index";
			} else {
				msg = "비밀번호가 틀렸습니다.";
			} // end of if (memdb.getPass().equals(pass))
		} else {
			msg = "탈퇴할 수 없습니다.";
		} // end of if (memdb != null)

		request.setAttribute("msg", msg);
		request.setAttribute("url", url);

		return "alert";
	} // end of memberDeletePro()

	@RequestMapping("memberPassForm")
	public String memberPassForm() throws ServletException, IOException {

		return "member/memberPassForm";
	} // end of memberPassForm()

	@RequestMapping("memberPassPro")
	public String memberPassPro(String pass, String modPass) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String id = (String) session.getAttribute("id");

		KicMember memdb = mybatisdao.getMember(id);

		String msg = "";
		String url = "memberPassForm";

		if (memdb != null) {
			if (memdb.getPass().equals(pass)) {
				msg = "수정 완료";
				session.invalidate();
				mybatisdao.modifyPass(id, modPass);
				url = "login";
			} else {
				msg = "비밀번호가 틀렸습니다.";
				url = "memberPassForm";
			} // end of if (memdb.getPass().equals(pass))
		} else {
			msg = "수정할 수 없습니다.";
		} // end of if (memdb != null)

		m.addAttribute("msg", msg);
		m.addAttribute("url", url);

		return "alert";
	} // end of join()

	@RequestMapping("memberList")
	public String memberList() throws ServletException, IOException {
		List<KicMember> li = mybatisdao.memberList();

		request.setAttribute("li", li);
		return "member/memberList";
	} // end of memberList()

	@RequestMapping("pictureimgForm")
	public String pictureimgForm() throws ServletException, IOException {
		return "../single/pictureimgForm";
	} // end of pictureimgForm()

	
	@RequestMapping("picturePro")
	public String picturePro(@RequestParam("picture") MultipartFile multipartFile)
			throws ServletException, IOException {
		String path = request.getServletContext().getRealPath("/") + "/img/member/picture/";
		System.out.println(path);
		String filename = "";
		if (!multipartFile.isEmpty()) {
			File file = new File(path, multipartFile.getOriginalFilename());
			filename = multipartFile.getOriginalFilename();
			multipartFile.transferTo(file);
		}

		System.out.println(filename);
		request.setAttribute("filename", filename);

		return "../single/picturePro";
	} // end of picturePro()
	

	@RequestMapping("fortunePro")
	public String fortunePro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isLogin(request)) {
			// 로그인되어 있지 않으면 로그인 페이지로 이동
			request.setAttribute("msg", "로그인 후에 운세를 확인할 수 있습니다.");
			request.setAttribute("url", "login");
			return "alert";
		}

		// 로그인되어 있으면 운세를 확인할 수 있도록 처리
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		request.setAttribute("name", request.getParameter("name"));

		// 운세를 확인하는 코드

		return "content/fortuneResult";
	} // end of fortunePro()
}
