package weathercollection.util;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 工具类
 */
public class SysUtil {
	
	/**  
	 * 返回昨天的系统时间的格式串
	 * 这个方法主要用于weathercollection.task.CloseOutputStreamTask
	 * 关闭之前的线程，释放空间
	 */
	public static String getYesterdaySysDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		//返回前一天的系统时间
		c.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(c.getTime());
	}
	
	public static String getSysDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Calendar c = Calendar.getInstance();
		//返回当天的系统时间
		c.add(Calendar.DAY_OF_MONTH, 0);
		return "day=" + sdf.format(c.getTime());
	}
	
	/**
	 * 和上面的相比，是为了区分HDFS和HIVE的Consumer
	 */
	public static String getSysDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		//返回当天的系统时间
		c.add(Calendar.DAY_OF_MONTH, 0);
		return sdf.format(c.getTime());
	}
	
	/**
	 * 从日志中解析日期信息
	 */
	public static String getYear(String tem_data) {
		return format(tem_data,"yyyy");
	}
	
	/**
	 * 从日志中解析日期信息
	 */
	public static String getMonth(String tem_data) {
		return format(tem_data,"MM");
	}
	
	/**
	 * 从日志中解析日期信息
	 */
	public static String getDay(String tem_data) {
		return format(tem_data,"dd");
	}
	
	/**
	 * 从日志中解析日期信息
	 */
	public static String getDate(String tem_data) {
		return format(tem_data,"yyyy/MM/dd");
	}
	
	/**
	 * 从日志中解析日期信息
	 */
	public static String getTime(String tem_data) {
		return format(tem_data,"HH:mm:ss");
	}
	
	/**
	 * 从日志中解析日期信息
	 */
	private static String format(String tem_data,String pattern) {
		try {
			//2019-12-23|北京|晴|无持续风向|-4|-18
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
			return sdf_cn.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到当前主机的IP地址
	 */
	public static String getIP(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到当前主机的名字
	 */
	public static String getHost(){
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
