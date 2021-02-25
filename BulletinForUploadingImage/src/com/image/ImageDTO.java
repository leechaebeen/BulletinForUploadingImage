/*===========================
 	ImageDTO.java
=============================*/

package com.image;

public class ImageDTO
{
	// 주요 속성 구성
	private int num;
	private String userId, userName;
	private String subject, created;
	private String saveFileName;
		
	// getter / setter 구성
	public int getNum()
	{
		return num;
	}
	public void setNum(int num)
	{
		this.num = num;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public String getCreated()
	{
		return created;
	}
	public void setCreated(String created)
	{
		this.created = created;
	}
	public String getSaveFileName()
	{
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName)
	{
		this.saveFileName = saveFileName;
	}
	
	
	
}
