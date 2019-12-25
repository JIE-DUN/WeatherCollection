package weathercollection.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import weathercollection.util.SysUtil;

public class testPool {

	static public Map<String, Integer> pool;
	// HDFS上未处理过的数据的目录
	static final String logHdfsPrefix = "/user/ubuntu/weather/raw";
	static final String logHivePrefix = "/user/hive/warehouse/weather.db";
	static FileSystem fs = null;
	
	@Test
	public void testPool() throws IOException {
		// 将hdfs的参数初始化
		pool=new HashMap<String,Integer>();
		String path = "D:/javatest/2019/12/24/a.txt";
		
		int data = 20191224;
		pool.put(path, data);
		
		int out0 = pool.get(path);
		System.out.println(out0);
	}
}
