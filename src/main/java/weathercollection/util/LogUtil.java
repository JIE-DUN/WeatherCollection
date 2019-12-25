package weathercollection.util;


import java.util.StringTokenizer;

/**
 * Log工具类
 */
public class LogUtil {
	/**
	 * 格式化整理一行日志，使其被逗号(,)分隔
	 * 删除了日期
	 */
	public static String formatTem(String line){
		String delim = ",";
		StringTokenizer st = new StringTokenizer(line);
		//2019-12-23|北京|晴|无持续风向|-4|-18
		String time = st.nextToken("|");			//2019-12-23
		String city = st.nextToken("|");			//北京
		String weather = st.nextToken("|");			//晴
		String wind = st.nextToken("|");			//无持续风向
		String mintem = st.nextToken("|");			//-4
		String maxtem = st.nextToken("|");			//-18

		StringBuilder builder = new StringBuilder();
		builder.append(city).append(delim)
				.append(weather).append(delim)
				.append(wind).append(delim)
				.append(mintem).append(delim)
				.append(maxtem);
		return builder.toString();
	}
	
	
	/**
	 * 格式化整理一行日志，使其被逗号(,)分隔
	 * 删除了日期,加入了温差
	 */
	public static String formatAddDifftem(String line){
		String delim = ",";
		StringTokenizer st = new StringTokenizer(line);
		// 2019-12-23|北京|晴|无持续风向|-4|3
		String time = st.nextToken("|"); 		// 2019-12-23
		String city = st.nextToken("|"); 		// 北京
		String weather = st.nextToken("|");		// 晴
		String wind = st.nextToken("|");		// 持续
		String mintem = st.nextToken("|");		// -4
		String maxtem = st.nextToken("|");		// -18
		String difftem = (Integer.valueOf(maxtem) - Integer.valueOf(mintem)) + "";
		

		StringBuilder builder = new StringBuilder();
		builder.append(city).append(delim)
				.append(weather).append(delim)
				.append(mintem).append(delim)
				.append(maxtem).append(delim)
				.append(difftem);

		return builder.toString();
	}
}
