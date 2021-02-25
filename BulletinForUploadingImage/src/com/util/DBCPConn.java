/*=============================
 		DBCPConn.java
===============================*/
package com.util;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DBCPConn
{
	private static Connection conn = null;
	
	public static Connection getConnection()
	{
		if(conn==null)
		{
			try
			{
				Context context  = new InitialContext();
				
				// JNDI 를 이용하여 객체를 탐색할 수 있도록 처리
				// → 코드 수정 불가
				Context con = (Context)context.lookup("java:/comp/env");
				
				DataSource ds = (DataSource)con.lookup("jdbc/myoracle");
				
				conn = ds.getConnection();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		return conn;
	}
	
	public static void close()
	{
		if(conn == null)
		{
			return;
		}
		
		try
		{
			if(!conn.isClosed())
			{
				conn.close();
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		conn = null;
	}
}
