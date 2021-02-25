/*====================
   FileManager.java
====================*/

package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class FileManager
{
	public static boolean doFileDownload(String saveFileName
		, String originalFileName, String path, HttpServletResponse response)
	{
		String filePath = path + File.separator + saveFileName;
		
		try
		{
			if(originalFileName == null || originalFileName.length() == 0)
			{
				originalFileName = saveFileName;
			}
			
			originalFileName = new String(originalFileName.getBytes("EUC-KR"), "8859_1");
			
			File f = new File(filePath);
			
			if(!f.exists())	// 파일이 존재하지 않을 경우
			{
				return false;
			}
			
			byte[] b = new byte[1024];
			
			// 클라이언트에게 전송할 대상이 스트림
			response.setContentType("application/octet-stream");
			
			// 클라이언트의 파일 저장 대화상자에 표시할 파일 이름 지정
			response.setHeader("Content-disposition"
					, "attachment;filename=" + originalFileName);
			
			// 파일의 내용을 전송(파일은 반드시 byte 스트림을 이용해야 함)
			BufferedInputStream bis 
					= new BufferedInputStream(new FileInputStream(f));
			
			OutputStream os = response.getOutputStream();
			
			int n;
			while ((n = bis.read(b, 0, 1024)) != -1)
			{
				os.write(b, 0, n);
			}
			os.flush();	// 버퍼가 채워지지 않았을 경우에도 전송해야 하기 때문
			os.close();
			bis.close();
			
			return true;
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
				
		return false;
	
	}//end doFileDownload()
	
	
	public static void doFileDelete(String fileName, String path)
	{
		try
		{
			File file = null;
			String fullFileName = path + File.separator + fileName;
			file = new File(fullFileName);
			if(file.exists())
				file.delete();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

}







