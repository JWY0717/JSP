package model2_mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.JDBConnect;

public class MVCBoardDAO extends JDBConnect { // 커넥션 풀 상속
	
	public MVCBoardDAO() {
		super();
	}


	// 검색 조선에 맞는 게시물의 개수를 반환합니다.
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
		// 쿼리문 준비 (게시물 수를 얻어오는 쿼리문)
		String query = "SELECT COUNT(*) FROM mvcboard";
		// 검색 조건이 있다면 WHERE로 추가
		if (map.get("searchword") != null) {
			query += " WHERE " + map.get("searchword") + " " 
						+ " LIKE '%" + map.get("searchword") + "%'";
		}
		try {
			stmt = con.createStatement(); // 쿼리문 생성
			rs = stmt.executeQuery(query); // 쿼리문 실행
			rs.next();
			totalCount = rs.getInt(1); // 검색된 게시물 개수 저장
		} catch (Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount; // 게시물 개수를 서블릿으로 반환
	}

	// 검색 조건에 맞는 게시물 목록을 반환합니다(페이징 기능 지원)!!!!
	public List<MVCBoardDTO> selectListPage(Map<String, Object> map) {
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		// 쿼리문 준비
//		String query = " "
//					 + "SELECT * FROM ( "
//					 + "	SELECT Tb.*, ROWNUM rNum FROM ( "
//					 + "		SELECT * FROM mvcboard ";		
//		// 검색 조건이 있다면 WHERE절로 추가
//		if (map.get("searchword" ) != null)
//		{
//			query += " WHERE " + map.get("searchField")
//				   + " LIKE '%" + map.get("searchWord") + "%' ";
//		}
//		
//		query += "		ORDER BY idx DESC "
//			   + "  ) Tb "
//			   + " ) "
//			   + " WHERE rNum BETWEEN ? AND ?";			//게시물 구간은 인파라미터로..
//	
		// 쿼리문 템플릿 MySQL Start
		String query = "SELECT * FROM mvcboard";

		// 검색 조건 추가
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " Like '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY idx DESC LIMIT ?,?";
		
		try {
			psmt = con.prepareStatement(query); // 동적 쿼리문 생성
			psmt.setInt(1, Integer.valueOf( map.get("start").toString())); // 인파라미터 설정
			psmt.setInt(2, Integer.valueOf(map.get("pageSize").toString()));
			rs = psmt.executeQuery(); // 쿼리문 실행

			// 반환된 게시물 목록을 List 컬렉션에 추가
			while (rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();

				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));

				board.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return board; // 목록 반환
	}
	
	public int insertWrite(MVCBoardDTO dto) {
		int result = 0;
		try {
	//		String query = "INSERT INTO mvcboard ( "
	//				 	 + " name, title, content, ofile, sfile, pass) "
	//				 	 + " VALIES ( "
	//				 	 + " ?,?,?,?,?,?)";
			String query = "INSERT INTO mvcboard (name, title, content, ofile, sfile, pass) VALUES (?, ?, ?, ?, ?, ?)";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			result = psmt.executeUpdate();	
			
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
		
	}
	
	// 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환합니다.
	public MVCBoardDTO selectView(String idx) {
			
			MVCBoardDTO dto = new MVCBoardDTO();	//DTO 객체 생성
			
			
			String query = "SELECT * FROM mvcboard WHERE idx=?";	//쿼리문 템플릿 준비
			
			try {
				
				psmt = con.prepareStatement(query);	//쿼리문 준비
				psmt.setString(1, idx);				//인파라미터 설정
				rs = psmt.executeQuery();			//쿼리문 실행
				
				//결과처리
				if (rs.next()) {					//결과를 DTO 객체에 저장 
					dto.setIdx(rs.getString(1));
					dto.setName(rs.getString(2));
					dto.setTitle(rs.getString(3));
					dto.setContent(rs.getString(4));
					dto.setPostdate(rs.getDate(5));
					dto.setOfile(rs.getString(6));
					dto.setSfile(rs.getString(7));
					dto.setDowncount(rs.getInt(8));
					dto.setPass(rs.getString(9));
					dto.setVisitcount(rs.getInt(10));
				}
				
			} catch(Exception e) {
				System.out.println("게시물 상세보기 중 예외 발생");
				e.printStackTrace();
			}
			return dto;								//결과 반환
		}
		//주어진 일련번호에 해당하는 개시물의 조회수를 1 증가시킵니다.!
	public void updateVisitCount(String idx) {
			
			String query = "UPDATE mvcboard SET "
						 + " visitcount=visitcount+1 " 
						 + " WHERE idx=?";
			
			try {
				
				psmt = con.prepareStatement(query);
				psmt.setString(1, idx);
				psmt.executeUpdate();
				
			} catch(Exception e) {
				System.out.println("게시물 조회수 증가 중 예외 발생");
				e.printStackTrace();
			}
		}
		
		public void downCountPlus(String idx) {
			String sql = "UPDATE mvcboard SET "
					  + " downcount=downcount+1 "
					  + " WHERE idx=? ";
			try {
				psmt = con.prepareStatement(sql);
				psmt.setString(1, idx);
				psmt.executeUpdate();
			}
			catch (Exception e) {}
		}
		
		//입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인합니다.
		public boolean confirmPassword(String pass, String idx) {
			boolean isCorr = true;
			
			try {
				String sql = "SELECT COUNT(*) FROM mvcboard WHERE pass=? AND idx=?";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, pass);
				psmt.setString(2, idx);
				rs = psmt.executeQuery();
				rs.next();
				// 해당 게시물이 없으면 지울 것이 없음
				if (rs.getInt(1) == 0) {
						isCorr = false;
				}
			}
			catch (Exception e) {
				isCorr = false;
				e.printStackTrace();
			}
			return isCorr;
		}
		
		// 지정한 일련번호의 게시물을 삭제합니다.
		public int deletePost(String idx) {
			int result = 0;
			
			try {
				String query = "DELETE FROM mvcboard WHERE idx=?";
				psmt = con.prepareStatement(query);
				psmt.setString(1, idx);
				result = psmt.executeUpdate();
			}
			catch (Exception e) {
				System.out.println("게시물 삭제 중 예외 발생");
				e.printStackTrace();
			}
			return result;
		}
		
		// 게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신합니다(파일 업로드 지원 ).
		public int updatePost(MVCBoardDTO dto) {
			int result = 0;
			
			try {
				//쿼리문 템플릿 준비
				String query = "UPDATE mvcboard "
							+ " SET title=?, name=?, content=?, ofile=?, sfile=? "
							+ " WHERE idx=? and pass=?";
				//쿼리문 준비
				psmt = con.prepareStatement(query);
				psmt.setString(1, dto.getTitle());
				psmt.setString(2, dto.getName());
				psmt.setString(3, dto.getContent());
				psmt.setString(4, dto.getOfile());
				psmt.setString(5, dto.getSfile());
				psmt.setInt(6, Integer.parseInt(dto.getIdx()));
				psmt.setString(7, dto.getPass());
			
				//쿼리문 실행
				result = psmt.executeUpdate();
			}
			catch (Exception e) {
				System.out.println("게시물 수정 중 예외 발생");
				e.printStackTrace();
			}
			return result;
		}
}