package com.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MyUtil
{
	// 문자열의 내용중 원하는 문자열을 다른 문자열로 변환
	// String str = replaceAll(str, "\r\n", "<br>"); // 엔터를 <br>로 변환
	public String replaceAll(String str, String oldStr, String newStr)
	{
		if (str == null || str.equals(""))
			return "";

		Pattern p = Pattern.compile(oldStr);
		Matcher m = p.matcher(str);

		StringBuffer sb = new StringBuffer();

		while (m.find())
		{
			m.appendReplacement(sb, newStr);
		}
		m.appendTail(sb);

		return sb.toString();
	}

	// *******************************************************************************
	// 전체페이지수 구하기
		/* param :
		 *     numPerPage : 한페이지에 표시할 데이터의 개수
		 *     dataCount : 전체데이터수
		 */
	public int getPageCount(int numPerPage, int dataCount)
	{
		int pageCount = 0;

		pageCount = dataCount / numPerPage;
		if (dataCount % numPerPage != 0)
			pageCount++;

		//System.out.println("MyUtil.getPageCount.pageCount : " + pageCount);
		
		return pageCount;
	}

	// 페이지 처리 메소드(GET 방식)
		/*
		 * param:
		 *     current_page : 현재 표시할 페이지
		 *     total_page : 전체페이지수
		 *     list_url : 링크를 설정할 url
		 */

	public String pageIndexList(int current_page, int total_page, String list_url)
	{
		if (current_page < 1 || total_page < 1)
			return "";

		StringBuffer sb = new StringBuffer();
		int numPerBlock = 10;
		int currentPageSetup;
		int n, page;

		if (list_url.indexOf("?") != -1)
			list_url = list_url + "&";
		else
			list_url = list_url + "?";

		// currentPageSetup : 표시할첫페이지-1
		currentPageSetup = (current_page / numPerBlock) * numPerBlock;
		if (current_page % numPerBlock == 0)
			currentPageSetup = currentPageSetup - numPerBlock;

		// 1 페이지, [Prev]:10 페이지를 이전페이지로 이동
		n = current_page - numPerBlock;
		if (total_page > numPerBlock && currentPageSetup > 0)
		{
			sb.append("<a href='" + list_url + "pageNum=1'>1</a>&nbsp;");
			sb.append("[<a href='" + list_url + "pageNum=" + n + "'>Prev</a>]&nbsp;");
		}

		// 바로가기 페이지
		page = currentPageSetup + 1;
		while (page <= total_page && (page <= currentPageSetup + numPerBlock))
		{
			if (page == current_page)
			{
				sb.append("<font color='Fuchsia'>" + page + "</font>&nbsp;");
			} else
			{
				sb.append("<a href='" + list_url + "pageNum=" + page + "'>" + page + "</a>&nbsp;");
			}
			page++;
		}

		// [Next]:10페이지를 다음페이지로 이동, 마지막 페이지
		n = current_page + numPerBlock;
		if (total_page - currentPageSetup > numPerBlock)
		{
			sb.append("[<a href='" + list_url + "pageNum=" + n + "'>Next</a>]&nbsp;");
			sb.append("<a href='" + list_url + "pageNum=" + total_page + "'>" + total_page + "</a>");
		}

		return sb.toString();
	}

	// *******************************************************************************
	// E-Mail 검사
	public boolean isEmail(String email)
	{
		if (email == null)
			return false;
		boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
		return b;
	}

	// NULL 인 경우 ""로 
	public String checkNull(String str)
	{
		String strTmp;
		if (str == null)
			strTmp = "";
		else
			strTmp = str;
		return strTmp;
	}

	// 8859_1 를 euc-kr로
	public String isoToEuc(String str)
	{
		String convStr = null;
		try
		{
			if (str == null)
				return "";

			convStr = new String(str.getBytes("8859_1"), "euc-kr");
		} catch (UnsupportedEncodingException e)
		{
		}
		return convStr;
	}

	// 8859_1 를 utf-8로
	public String isoToUtf(String str)
	{
		String convStr = null;
		try
		{
			if (str == null)
				return "";

			convStr = new String(str.getBytes("8859_1"), "utf-8");
		} catch (UnsupportedEncodingException e)
		{
		}
		return convStr;
	}

	// euc-kr 를 ISO-8859-1 로
	public String eucToIso(String str)
	{
		String convStr = null;
		try
		{
			if (str == null)
				return "";

			convStr = new String(str.getBytes("euc-kr"), "8859_1");
		} catch (UnsupportedEncodingException e)
		{
		}
		return convStr;
	}

	// KSC56O1 를 8859_1로
	public String fromKorean(String str)
	{
		String convStr = null;
		try
		{
			if (str == null)
				return "";

			convStr = new String(str.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e)
		{
		}
		return convStr;
	}

	// euc-kr 를 KSC5601 로
	public String eucToKsc(String str)
	{
		String convStr = null;
		try
		{
			if (str == null)
				return "";

			convStr = new String(str.getBytes("euc-kr"), "8859_1");
			convStr = new String(convStr.getBytes("8859_1"), "KSC5601");
		} catch (UnsupportedEncodingException e)
		{
		}
		return convStr;
	}
}
