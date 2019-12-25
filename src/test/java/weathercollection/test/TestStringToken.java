package weathercollection.test;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.junit.Test;

public class TestStringToken {
	/**
	 * 这个也是根据空格将字符串切割的方法，但是比split灵活一点
	 */
	@Test
	public void test1() throws Exception {
		String delim = ",";
		InputStream is = this.getClass().getClassLoader().getSystemResourceAsStream("test.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = br.readLine();
		StringTokenizer st = new StringTokenizer(line);
		// 2019-12-23|北京|晴|无持续风向|-4|-18
		String time = st.nextToken("|"); 		// 2019-12-23
		String city = st.nextToken("|"); 		// 北京
		String weather = st.nextToken("|");		// 晴
		String wind = st.nextToken("|");		// 持续
		String mintem = st.nextToken("|");		// -4
		String maxtem = st.nextToken("|");		// -18
		String Difftem = (Integer.valueOf(maxtem) - Integer.valueOf(mintem)) + "";
				

		StringBuilder builder = new StringBuilder();
		builder.append(city).append(delim)
				.append(weather).append(delim)
				.append(mintem).append(delim)
				.append(maxtem).append(delim)
				.append(Difftem);
		System.out.println(builder.toString());
	}
}
