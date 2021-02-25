/*==================================
 	ImageDAO.java
====================================*/

package com.image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBCPConn;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO
{
	private Connection conn = DBCPConn.getConnection();
	
	// 게시물 번호의 최대값 얻어내기
	public int getNumMax()
	{
		int result = 0;
		String sql = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try
		{
			sql = "SELECT NVL(MAX(NUM),0) AS COUNT"
					+ " FROM IMAGEBOARD";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
				result = rs.getInt(1);
			rs.close();
			stmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
		
	}// end getNumMax();
	
	
	// 게시물 등록
	public int insertData(ImageDTO dto)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try
		{
			String fields = "NUM, USERID, SUBJECT, SAVEFILENAME";
			
			sql = "INSERT INTO IMAGEBOARD("+ fields +")"
					+ " VALUES(?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getSaveFileName());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
		
	}//end insertData(ImageDTO dto)
	
	
	// 게시물 전체 데이터 갯수 확인 -- ????
	public int getTotalDataCount(String searchKey, String searchValue)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try
		{
			/*
			 * sql = "SELECT I.NUM AS NUM" + " , M.USERID AS USERID" +
			 * " , I.SUBJECT AS SUBJECT" + " , M.USERNAME AS USERNAME" +
			 * " FROM IMAGEBOARD I JOIN MEMBER M" + " ON I.USERID = M.USERID";
			 */
	         sql = "SELECT COUNT(*) AS COUNT"
	                 + " FROM"
	                 + " (SELECT I.NUM AS NUM, M.USERID AS USERID"
	                 + ", I.SUBJECT AS SUBJECT, M.USERNAME AS USERNAME"
	                 + " FROM IMAGEBOARD I JOIN MEMBER M"
	                 + " ON I.USERID = M.USERID)";
			
			if(searchValue != null && searchValue.length() != 0)
			{
				sql += " WHERE " + searchKey + "LIKE '%' || ? || '%'";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchValue);
				
			}else
			{
				pstmt = conn.prepareStatement(sql);
			}
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}// end getTotalDataCount(String searchKey, String searchValue)
	
	
	// 특정 구간의 리스트 얻어내기
	public List<ImageDTO> getListData(int start, int end, String searchKey, String searchValue)
	{
		System.out.println("ImageDAO.getListData().start : "  + start);
		System.out.println("ImageDAO.getListData().end : " + end);
		
		List<ImageDTO> lists = new ArrayList<ImageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try
		{
	         sb.append("SELECT *");
	         sb.append(" FROM");
	         sb.append("(");
	         sb.append("    SELECT ROWNUM AS RNUM");
	         sb.append("         , DATA.*");
	         sb.append("    FROM");
	         sb.append("    (");
	         sb.append("        SELECT I.NUM AS NUM");
	         sb.append("             , M.USERNAME AS USERNAME");
	         sb.append("             , M.USERID AS USERID");
	         sb.append("             , I.SUBJECT AS SUBJECT");
	         sb.append("             , I.SAVEFILENAME AS SAVEFILENAME");
	         sb.append("             , TO_CHAR(I.CREATED, 'YYYY-MM-DD') AS CREATED");
	         sb.append("        FROM IMAGEBOARD I JOIN MEMBER M");
	         sb.append("        ON I.USERID = M.USERID");
	         
	         if(searchValue !=null && searchValue.length()!=0)
	         {
	        	 sb.append("        WHERE "+ searchKey +" LIKE '%' || ? || '%'");
	 	         sb.append("        ORDER BY NUM DESC");
	 	         sb.append("    ) DATA");
	 	         sb.append(")");
	 	         sb.append(" WHERE RNUM >= ? AND RNUM <= ? ");
	 	         
	 	        pstmt = conn.prepareStatement(sb.toString());
	 	        pstmt.setString(1, searchValue);
	 	        pstmt.setInt(2, start);
	 	        pstmt.setInt(3, end);
	 	        
	         }
	         else
	         {
	        	 sb.append("        ORDER BY NUM DESC");
	 	         sb.append("    ) DATA");
	 	         sb.append(")");
	 	         sb.append(" WHERE RNUM >= ? AND RNUM <= ? ");
	 	         
	 	        pstmt = conn.prepareStatement(sb.toString());
	 	        pstmt.setInt(1, start);
	 	        pstmt.setInt(2, end);
	         
	         }
	        
	         rs = pstmt.executeQuery();
	         while(rs.next())
	         {
	        	 // NUM, USERNAME, USERID, SUBJECT, SAVEFILENAME, CREATED
	        	 ImageDTO dto = new ImageDTO();
	        	 dto.setNum(rs.getInt("NUM"));
	        	 dto.setUserName(rs.getString("USERNAME"));
	        	 dto.setUserId(rs.getString("USERID"));
	        	 dto.setSubject(rs.getString("SUBJECT"));
	        	 dto.setSaveFileName(rs.getString("SAVEFILENAME"));
	        	 dto.setCreated(rs.getString("CREATED"));
	        	 
	        	 lists.add(dto);
	         }
	         
	         rs.close();
	         pstmt.close();
	         
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return lists;
	
	}// end getListData(int start, int end, String searchKey, String searchValue)

	
	// 해당 게시물의 상세 내용 얻어내기
	public ImageDTO getReadData(int num)
	
	{
		ImageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try
		{
			sb.append(" SELECT I.NUM AS NUM");
			sb.append(" 	 , M.USERNAME AS USERNAME");
			sb.append("		 , M.USERID AS USERID");
			sb.append("		 , I.SUBJECT AS SUBJECT");
			sb.append("		 , I.SAVEFILENAME AS SAVEFILENAME");
			sb.append("		 , TO_CHAR(I.CREATED, 'YYYY-MM-DD') AS CREATED");
			sb.append(" FROM IMAGEBOARD I JOIN MEMBER M");
			sb.append(" ON I.USERID = M.USERID");
			sb.append(" WHERE I.NUM = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if(rs.next())
			{
				dto = new ImageDTO();
				dto.setNum(rs.getInt("NUM"));
				dto.setUserName(rs.getString("USERNAME"));
				dto.setUserId(rs.getString("USERID"));
				dto.setSubject(rs.getString("SUBJECT"));
				dto.setSaveFileName(rs.getString("SAVEFILENAME"));
				dto.setCreated(rs.getString("CREATED"));
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return dto;
	}// end getReadData(int num)
	
	
	// 이미지 게시물 삭제
	public int deleteData(int num, String userId)
	{
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = null;
		
		try
		{
			sql =  "DELETE"
					+" FROM IMAGEBOARD"
					+" WHERE NUM=? AND USERID =?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
			
			pstmt.close();
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
}
