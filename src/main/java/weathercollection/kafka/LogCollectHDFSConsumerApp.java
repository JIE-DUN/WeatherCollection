package weathercollection.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import weathercollection.task.CloseOutputStreamTask;
import weathercollection.util.SysUtil;

/**
 * 日志收集器消费者
 */
public class LogCollectHDFSConsumerApp {
	
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.put("zookeeper.connect", "s101:2181,s102:2181,s103:2181");
		props.put("group.id", "g1");
		props.put("auto.offset.reset", "largest");
		props.put("zookeeper.session.timeout.ms", "500");
		props.put("zookeeper.sync.time.ms", "250");
		props.put("auto.commit.interval.ms", "1000");
		
		//创建消费者配置对象
		ConsumerConfig conf = new ConsumerConfig(props);
		//创建连接器
		ConsumerConnector conn = Consumer.createJavaConsumerConnector(conf);
		
		//
		Map<String, Integer> topicCount = new HashMap<String, Integer>();
		topicCount.put("weathercollection", 1);
		
		new CloseOutputStreamTask().startTask();
		
		//Key == topic
		Map<String, List<KafkaStream<byte[], byte[]>>> map = conn.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> list = map.get("weathercollection");
		for(KafkaStream<byte[], byte[]> s : list){
			ConsumerIterator<byte[], byte[]> it = s.iterator();
			while(it.hasNext()){
				MessageAndMetadata<byte[], byte[]> entry = it.next();
				String msg = new String(entry.message());
				//这个就是以后在hdfs上的文件名
				String path = "/user/ubuntu/weather/raw/" + SysUtil.getDate(msg) + "/" + SysUtil.getHost() + ".txt";
				HDFSOutputStreamPool.writeHdfsLog(path,msg);
				System.out.println(msg);
			}
		}
		conn.shutdown();
	}
	
}

