package weathercollection.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import weathercollection.util.SysUtil;

public class testData {
	/**
	 * 在文本中提取时间
	 */
	@Test
	public void getDate() throws Exception{
		InputStream is = this.getClass().getClassLoader().getSystemResourceAsStream("test.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		while(true){
			String line = reader.readLine();
			if(line == null){
				return;
			}
			else{
				System.out.println(SysUtil.getDate(line) + " " + SysUtil.getTime(line));
			}
		}
	}
	
	/**
	 * 从日志中解析日期信息
	 * @throws Exception 
	 */
	@Test
	public void format() throws Exception {
			String tem_data = "2019-12-23|北京|晴|无持续风向|-4|-18";
			String pattern = "yyyy/MM/dd";
			// 按照空格分隔
			String[] arr = tem_data.split("\\|");
			// 提取第一个元素,得出类似这个"2019-12-23"
			String dataPart = arr[0];
			// 规定解析dataPart的工具
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 按照sdf的格式，格式化dataPart
			Date d = sdf.parse(dataPart);
			// 按照中国的表示方法，格式化并输出
			SimpleDateFormat sdf_cn = new SimpleDateFormat(pattern);
			System.out.println(sdf_cn.format(d));
	}
	
	@Test
	public void getIP(){
		System.out.println(SysUtil.getHost());
	}
}
