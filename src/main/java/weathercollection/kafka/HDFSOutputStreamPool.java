package weathercollection.kafka;


import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * hdfs输出流池，包含hive和hdfs输出处理
 */
public class HDFSOutputStreamPool {
	static public Map<String, FSDataOutputStream> pool;
	static FileSystem fs = null;
	// 将hdfs的参数初始化
	static {
		try {
			pool = new HashMap<String, FSDataOutputStream>();
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://s100:8020");
			fs = FileSystem.get(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * 从pool中返回流 大概意思是pool中没有path = logPrefix + date + "/" + ip + ".txt"，就追加一个
//	 * 主要是为了应对停机之后，前一天的文件没放上去HDFS，从而需要在pool中创建对应日期的流并放上去
//	 */
//	public static FSDataOutputStream getHdfsOutputStream(String date, String ip) {
//		try {
//			String path = logHdfsPrefix + date + "/" + ip + ".txt";
//			return getHdfsOutputStream(path);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 从pool中返回流
	 */
	public static FSDataOutputStream getHdfsOutputStream(String path) {
		try {
			FSDataOutputStream out = pool.get(path);
			if (out == null) {
				Path p = new Path(path);
				// 判断是否存在
				if (!fs.exists(p)) {
					// 如果不存在，就创建文件，并追加
					// 为什么不直接用fs.create(p),是因为会将HDFS上的旧文件覆盖
					fs.createNewFile(p);
				}
				// 把数据追加进去
				out = fs.append(p);
				pool.put(path, out);
			}
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 写入数据到hdfs路径
	 */
	public static void writeHdfsLog(String path,String line){
		try {
			FSDataOutputStream out = getHdfsOutputStream(path);
			out.write((line + "\r\n").getBytes());
			out.hsync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

