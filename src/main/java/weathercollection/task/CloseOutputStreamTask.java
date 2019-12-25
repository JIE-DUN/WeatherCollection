package weathercollection.task;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hadoop.fs.FSDataOutputStream;

import weathercollection.kafka.HDFSOutputStreamPool;
import weathercollection.util.SysUtil;

public class CloseOutputStreamTask {
	public void startTask() {
		// 创建定时器
		Timer t = new Timer();

		TimerTask task = newTask();
		// task:定时器任务
		// 2 * 60 * 1000:首次运行延迟毫秒数
		// 1000:周期
		t.schedule(task, 2 * 60 * 1000, 1000);
	}

	/**
	 * 创建任务
	 */
	private TimerTask newTask() {
		return new TimerTask() {
			// 创建定时器任务
			public void run() {
				Map<String, FSDataOutputStream> map = HDFSOutputStreamPool.pool;
				for(Entry<String, FSDataOutputStream> entry : map.entrySet()){
					String key = entry.getKey();					//key就是那个文件的路径
					FSDataOutputStream value = entry.getValue();
//					String yesterday = SysUtil.getYesterdaySysDate();	//正常来说应该是把昨天的删除的
					String todayhive = SysUtil.getSysDay();		//得出day=25
					String todayhdfs = SysUtil.getSysDate();	//得出2019/12/25
					if(key.contains(todayhive)){
						try {
							//判断是否包含昨天的，包含的话就关闭并删除
							value.close();
							map.remove(key);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if(key.contains(todayhdfs)){
						try {
							//判断是否包含昨天的，包含的话就关闭并删除
							value.close();
							map.remove(key);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
	}
}