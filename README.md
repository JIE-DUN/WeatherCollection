## 前言

主要是尝试自己做一个数据收集、存储、清洗的项目。

## 项目介绍

这是对全国460+个城市天气，每天进行数据收集、存储、清洗的项目。爬取的数据包括天气、风向、最高气温、最低气温。不同地区的天气数据分别在不同的节点爬取，最后均存储在HDFS。hive存储清洗过的数据，因为hbase的高效查询，所以对hive表进行关联，能查询计算过的数据。

## 环境搭建

### 开发工具

| 工具          |
| ------------- |
| eclipse       |
| maven         |


### 开发环境

| 工具          |
| ------------- |
| JDK           |
| python        |
| Mysql         |
| hadoop        |
| flume         |
| kafka         |
| zookeeper     |
| hive          |
| hbase         |

### 搭建步骤

## Windows部署
	eclipse需要集成python、maven等等
	
## Linux部署
	1.python需要安装weather_spider.py这个文件需要的库
	
	2.配置hadoop、hbase等工具，使其使用我们自己的zookeeper
	
	3.配置周期运行的crontab
		#中国天气网全国城市天气收集
		0 12 * * * /usr/local/bin/weather_spider.py #爬取天气数据，并写进txt文件。网站每天11:30分更新
		5 12 * * * /usr/local/bin/rollweather.sh    #复制数据文件至监控目录
		[/usr/local/bin/rollweather.sh]
		#!/bin/bash
		dataformat=`date +%Y-%m-%d`
		cp /home/ubuntu/weather/$dataformat.txt /home/ubuntu/weather/flume/$dataformat.txt 
		
	4.配置flume的conf文件
		[/soft/flume/conf/collectTem.conf]
		# comonents
		a1.sources = r1
		a1.channels = c1
		a1.sinks = k1
		# source
		a1.sources.r1.type = spooldir
		a1.sources.r1.spoolDir = /home/ubuntu/weather/flume
		# sink
		a1.sinks.k1.type = org.apache.flume.sink.kafka.KafkaSink
		a1.sinks.k1.topic = weathercollection
		a1.sinks.k1.brokerList = s101:9092,s102:9092,s103:9092
		a1.sinks.k1.requiredAcks = 1
		a1.sinks.k1.batchSize = 20
		# channel
		a1.channels.c1.type = memory
		# Bind
		a1.sources.r1.channels = c1
		a1.sinks.k1.channel = c1
		
	5.在hive上提前创建存储清洗过的数据的表以及关联到hbase上的表，这两个表之间的数据计算及复制还需要手动
	  进行（这一步也可以用crontab周期创建）
		

