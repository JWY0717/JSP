package model.board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;

public class BoardDAO extends JDBConnect {

	public BoardDAO(ServletContext application) {
		super(application);
	}

	public int insertWrite(BoardDTO dto) {
		int result = 0;

		try {
			// INSERT 쿼리문 작성
			String query = "INSERT INTO board (" 
					+ " title,content,id,visitcount) " 
					+ " VALUES ( "
					+ " ?, ?, ?, 0)";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());

			result = psmt.executeUpdate();
			
		} 
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;
	
	}
	


	// 검색 조건에 맞는 게시물의 개수를 반환
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0; // 결과(게시물 수)를 담을 변수

		String query = "SELECT COUNT(*) FROM board";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " " + " Like '%" + map.get("searchWord") + "%'";
		}
		System.out.println("쿼리:" + query);
		try {
			stmt = con.createStatement();		//쿼리문 생성
			rs = stmt.executeQuery(query);		//쿼리 실행
			rs.next();							//커서를 첫 번째 행으로 이동
			totalCount = rs.getInt(1);			//첫 번쪠 컬럼 값을 가져옴 
		} catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}

		return totalCount;
	}
	//게시물 목록 가져오기!
	public List<BoardDTO> selectList( Map<String,Object>map ) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		String query = "SELECT * FROM board";
		if (map != null && map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
				   + " Like '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY num DESC ";
		
		try {
			//쿼리문 완성.
			stmt = con.createStatement();		//쿼리문 생성
			rs = stmt.executeQuery(query);		//쿼리 실행
			
			while (rs.next()) {					//결과를 순화하며...
				
				// 한 행(게시물 하나)의 데이터를 DTO에 저장
				BoardDTO dto = new BoardDTO();

				dto.setNum(rs.getString("num"));				// 일련번호
				dto.setTitle(rs.getString("title"));			// 제목
				dto.setContent(rs.getString("content"));		// 내용
				dto.setPostdate(rs.getDate("postdate"));		// 작성일
				dto.setId(rs.getString("id"));					// 작성자 아이디 
				dto.setVisitcount(rs.getString("visitcount"));	// 조회수
				//반환할 결과 목록에 게시물 추가
				bbs.add(dto);

			}

		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		//목록 반환
		return bbs;

	}
		
	
	// 검색 조건에 맞는 게시물 목록을 반환합니다(페이징 기능 지원).
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();	//결과(게시물 목록)를 담을 변수
		
		//쿼리문 템플릿 MySQL Start
		String query = "SELECT * FROM board";
		
		//검색 조건 추가
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
				   + " Like '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY num DESC LIMIT ?,?";
		
		//쿼리문 템플릿 MySQL End
		//**************************************************************************
		
		try {
			//쿼리문 완성
			psmt = con.prepareStatement(query);
			//psmt.setString(1, map.get("start").toString());
			//psmt.setString(2, map.get("end").toString());
			psmt.setInt(1, (int)map.get("start")-1);
			psmt.setInt(2, (int)map.get("end"));
			
			//쿼리문 실행
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				// 한 행(게시물 하나)의 데이터를 DTO에 저장
				BoardDTO dto = new BoardDTO();

				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("Content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				//반환할 결과 목록에 게시물 추가
				bbs.add(dto);

			}

		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		//목록 반환
		return bbs;

	}
	//지정한 게시물을 찾아 내용을 반환합니다
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		
		//쿼리문 준비
		String query = "SELECT B.*, M.name"
					 + " FROM member M INNER JOIN board B"
					 + " ON M.id=B.id "
					 + " WHERE num=?";
		try{
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
			
			//결과 처리
			if (rs.next()) {
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(1));
				dto.setContent(rs.getString("Content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString(6));
				dto.setName(rs.getString("name"));
			}
		}
		catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		return dto;
		
	}
	//지정한 게시물의 조회수를 1 증가시킵니다
	public void updateVisitCount(String num) {
		//쿼리문 준비
		String query = "UPDATE board SET"
					 + " visitcount=visitcount+1 "
					 + " WHERE num=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,num);
			psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	public int updateEdit(BoardDTO dto) {
		int result = 0;
		
		try {
			//쿼리문 템플릿
			String query = "UPDATE board SET"
						 + " title=?, content=?"
						 + " WHERE num=?";
			//쿼리문 완성
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());
			//쿼리문 실행
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		return result;  //결과 반환
	}
	//지정된 게시물을 삭제합니다.
	public int deletePost(BoardDTO dto) {
		int result = 0;
		
		try {
			//쿼리문 템플릿
			String query = "DELETE FROM board WHERE num=?";
						
			//쿼리문 완성
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getNum());
			
			//쿼리문 실행
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result;  //결과 반환
	}
}
