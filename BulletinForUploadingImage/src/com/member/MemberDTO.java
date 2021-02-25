/*========================
 	MemberDTO.java
==========================*/
package com.member;

public class MemberDTO
{
	// 주요 속성 구성
	private String userId, userPwd, userName;
	private String tel, tel1, tel2, tel3;
	private String email, email1, email2;
	private String created;
	
	// getter / setter 구성 
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getUserPwd()
	{
		return userPwd;
	}
	public void setUserPwd(String userPwd)
	{
		this.userPwd = userPwd;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getTel1()
	{
		return tel1;
	}
	public void setTel1(String tel1)
	{
		this.tel1 = tel1;
	}
	public String getTel2()
	{
		return tel2;
	}
	public void setTel2(String tel2)
	{
		this.tel2 = tel2;
	}
	public String getTel3()
	{
		return tel3;
	}
	public void setTel3(String tel3)
	{
		this.tel3 = tel3;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getEmail1()
	{
		return email1;
	}
	public void setEmail1(String email1)
	{
		this.email1 = email1;
	}
	public String getEmail2()
	{
		return email2;
	}
	public void setEmail2(String email2)
	{
		this.email2 = email2;
	}
	public String getCreated()
	{
		return created;
	}
	public void setCreated(String created)
	{
		this.created = created;
	}

	
	
}
