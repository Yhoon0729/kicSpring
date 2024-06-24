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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dao.KicBoardMybatis;
import model.Comment;
import model.KicBoard;

@Controller
@RequestMapping("/board/")
public class BoardController {

	HttpSession session;
	HttpServletRequest request;

	@Autowired
	KicBoardMybatis mybatisdao = new KicBoardMybatis();

	@ModelAttribute
	protected void service(HttpServletRequest request) throws ServletException, IOException {
		this.request = request;
		session = request.getSession();
		String nav = (String) session.getAttribute("boardid");
		String boardName = (String) session.getAttribute("boardName");
		request.setAttribute("nav", nav);
		request.setAttribute("boardName", boardName);
	}

	@RequestMapping("index")
	public String index() throws ServletException, IOException {

		return "index";
	}

	@RequestMapping("boardForm")
	public String boardForm() throws ServletException, IOException {

		return "board/boardForm";
	}

	@RequestMapping("boardInfo")
	public String boardInfo(int num) throws ServletException, IOException {

		// int num = Integer.parseInt(request.getParameter("num"));
		System.out.println(num);

		mybatisdao.addReadCount(num); // readcnt++

		int count = mybatisdao.getCommentCount(num);
		KicBoard board = mybatisdao.getBoard(num);
		List<Comment> li = mybatisdao.commentList(num);

		request.setAttribute("board", board);
		request.setAttribute("li", li);
		request.setAttribute("count", count);
		return "board/boardInfo";
	}

	@RequestMapping("boardCommentPro")
	public String boardCommentPro(String comment, int boardnum) throws ServletException, IOException {
		// String comment = request.getParameter("comment");
		// int boardnum = Integer.parseInt(request.getParameter("boardnum"));
		request.setAttribute("boardnum", boardnum);
		request.setAttribute("comment", comment);

		mybatisdao.insertComment(boardnum, comment);
		int count = mybatisdao.getCommentCount(boardnum);

		request.setAttribute("comment", comment);
		request.setAttribute("count", count);

		return "/single/boardCommentPro";
	} // end of boardCommentPro

	@RequestMapping("boardUpdateForm")
	public String boardUpdateForm(int num) throws ServletException, IOException {
		// http://localhost:8080/kicmodel2/board/boardInfo?num=10

		// int num = Integer.parseInt(request.getParameter("num"));
		System.out.println(num);
		KicBoard board = mybatisdao.getBoard(num);

		request.setAttribute("board", board);
		return "board/boardUpdateForm";
	}

	
	@RequestMapping("boardUpdatePro")
	public String boardUpdatePro(@RequestParam("file2") MultipartFile multipartFile, KicBoard kicboard)
			throws ServletException, IOException {
		String path = request.getServletContext().getRealPath("/") + "img/board/";
		KicBoard boarddb = mybatisdao.getBoard(kicboard.getNum());
		String filename = "";
		if (!multipartFile.isEmpty()) {
			File file = new File(path, multipartFile.getOriginalFilename());
			filename = multipartFile.getOriginalFilename();
			multipartFile.transferTo(file);
			kicboard.setFile1(filename);
		} else {
			kicboard.setFile1(boarddb.getFile1());
		}

		String msg = "수정 되지 않았습니다";
		String url = "boardUpdateForm?num=" + kicboard.getNum();
		System.out.println(boarddb);
		if (boarddb != null) {
			if (kicboard.getPass().equals(boarddb.getPass())) {
				int count = mybatisdao.boardUpdate(kicboard);
				if (count == 1) {
					msg = "수정 되었습니다";
					url = "boardInfo?num=" + kicboard.getNum();
				}
			} else {
				msg = "비밀번호 확인 하세요";
			}
		} else {
			msg = "게시물이 없습니다";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	} // end of boardUpdatePro
	

	@RequestMapping("boardPro")
	public String boardPro(@RequestParam("file2") MultipartFile multipartFile, KicBoard kicboard)
			throws ServletException, IOException {
		String path = request.getServletContext().getRealPath("/") + "img/board/";

		String filename = "";
		if (!multipartFile.isEmpty()) {
			File file = new File(path, multipartFile.getOriginalFilename());
			filename = multipartFile.getOriginalFilename();
			multipartFile.transferTo(file);
		}

		String boardid = (String) session.getAttribute("boardid");
		kicboard.setBoardid(boardid);
		kicboard.setFile1(filename);
		int num = mybatisdao.insertBoard(kicboard);
		String msg = "게시물 등록 성공";
		String url = "boardList?boardid=" + boardid;
		if (num == 0) {
			msg = "게시물 등록 실패";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}

	@RequestMapping("boardList")
	public String boardList(String boardid, String pageNum) throws ServletException, IOException {

		// String boardid = request.getParameter("boardid");
		session.setAttribute("boardid", boardid);
		if (session.getAttribute("boardid") == null) {
			boardid = "1";
		}

		// String pageNum = request.getParameter("pageNum");
		session.setAttribute("pageNum", pageNum);
		if (session.getAttribute("pageNum") == null) {
			pageNum = "1";
		}

		String boardName = "";
		switch (boardid) {
		case "1":
			boardName = "공지사항";
			break;
		case "2":
			boardName = "자유게시판";
			break;
		case "3":
			boardName = "QnA";
			break;
		default:
			boardName = "공지사항";
		}

		session.setAttribute("boardName", boardName);
		int count = mybatisdao.boardCount(boardid);
		int limit = 3;
		int pageInt = Integer.parseInt(pageNum); // 페이지 목록 번호
		int boardNum = count - ((pageInt - 1) * limit); // 각 페이지의 첫번째 게시글

		int bottomLine = 3; // 하단 페이지 목록 갯수
		int start = (pageInt - 1) / bottomLine * bottomLine + 1; // 하단 페이지 목록에서 start 페이지 번호
		int end = start + limit - 1; // 하단 페이지 목록 마지막 페이지 번호
		int maxPage = (int) Math.ceil((double) count / (double) limit); // 페이지 목록 갯수
		if (end > maxPage) {
			end = maxPage;
		}

		List<KicBoard> li = mybatisdao.boardList(boardid, pageInt, limit);

		request.setAttribute("bottomLine", bottomLine);
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("pageInt", pageInt);
		request.setAttribute("boardNum", boardNum);

		request.setAttribute("boardName", boardName);
		request.setAttribute("li", li);
		request.setAttribute("boardid", boardid);
		request.setAttribute("nav", boardid);
		request.setAttribute("count", count);

		return "board/boardList";
	} // end of boardList

	@RequestMapping("boardDeleteForm")
	public String boardDeleteForm(int num) throws ServletException, IOException {
		request.setAttribute("num", num);
		return "board/boardDeleteForm";
	}

	@RequestMapping("boardDeletePro")
	public String boardDeletePro(int num, String pass) throws ServletException, IOException {
		// int num = Integer.parseInt(request.getParameter("num"));
		// String pass = request.getParameter("pass");
		String boardid = (String) session.getAttribute("boardid"); // 1
		KicBoard boarddb = mybatisdao.getBoard(num);
		String msg = "삭제 되지 않았습니다";
		String url = "boardDeleteForm?num=" + num;
		if (boarddb != null) {
			if (pass.equals(boarddb.getPass())) {
				int count = mybatisdao.boardDelete(num);
				if (count == 1) {
					msg = "삭제 되었습니다";
					url = "boardList?boardid=" + boardid;// 2
				}
			} else {
				msg = "비밀번호 확인 하세요";
			}
		} else {
			msg = "게시물이 없습니다";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}

}