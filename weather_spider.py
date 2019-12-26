#!/usr/bin/python
# --*-- coding:utf-8 --*--

import requests
import time
import os
from bs4 import BeautifulSoup


def parse_page(url,file_name,data):
    headers = {
        "User-Agent": "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
                      "(KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36"
    }

    file_obj = open(file_name,'a')

    response = requests.get(url, headers=headers)
    text = response.content.decode('utf-8')
    soup = BeautifulSoup(text, 'html5lib')  # lxml的容错能力不够，在解释港澳台时不行
    conMidtab = soup.find('div', class_='conMidtab')
    tables = conMidtab.find_all('table')
    for table in tables:
        trs = table.find_all('tr')[2:]
        # for tr in trs:
        for index, tr in enumerate(trs):
            tds = tr.find_all('td')
            tr.find_all
            
            city_td = tds[0].stripped_strings       #获取城市名
            if index == 0:  # 首个不同
                city_td = tds[1].stripped_strings
            city = list(city_td)[0]
            city_str = city.encode('utf-8')

            temp_td = tds[-2].stripped_strings      #获取最低气温
            min_temp = list(temp_td)[0]
            min_temp_str = str(min_temp)
            
            max_temp_td = tds[3].stripped_strings   #获取最高气温
            if index == 0:
                max_temp_td = tds[4].stripped_strings
            max_temp = list(max_temp_td)[0]
            max_temp_str = str(max_temp)
            
            weather_td = tds[4]                      #获取天气
            if index == 0:
                weather_td = tds[5]
            weather = list(weather_td)[0]
            weather_str = weather.encode('utf-8')
            
            wind_direction_td = tds[5].stripped_strings      #获取风向
            if index == 0:
                wind_direction_td = tds[6].stripped_strings
            wind_direction = list(wind_direction_td)[0]
            wind_direction_str = wind_direction.encode('utf-8')
            
            file_obj.write(data + '|' + city_str + '|' + weather_str + '|' + wind_direction_str + '|' + min_temp_str + '|' + max_temp_str + '\n')
    file_obj.close()
    
def main():
    urls = [
        "http://www.weather.com.cn/textFC/hb.shtml",
        "http://www.weather.com.cn/textFC/db.shtml",
        "http://www.weather.com.cn/textFC/hd.shtml",
        "http://www.weather.com.cn/textFC/hz.shtml",
        "http://www.weather.com.cn/textFC/hn.shtml",
        "http://www.weather.com.cn/textFC/xb.shtml",
        "http://www.weather.com.cn/textFC/xn.shtml",
        "http://www.weather.com.cn/textFC/gat.shtml"
    ]
    
    head = "/home/ubuntu/weather"
    data = time.strftime("%Y-%m-%d", time.localtime())
    suffix = '.txt'
    
    file_name = os.path.join(head,data + suffix)
#     print file_name
    
    for url in urls:
        parse_page(url,file_name,data)

if __name__ == '__main__':
    main()