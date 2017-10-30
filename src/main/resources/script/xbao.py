#! /usr/bin/env python
# -*- coding=utf-8 -*- 
# @Author pythontab.com
import urllib2
import re
import time
import datetime

def get_yestoday():  
    #myday = datetime.datetime( int(mytime[0:4]),int(mytime[4:6]),int(mytime[6:8]) )  
    myday = datetime.datetime.now()  
    delta = datetime.timedelta(days=-1)  
    my_yestoday = myday + delta  
    my_yes_time = my_yestoday.strftime('%Y-%m-%d')  
    return my_yes_time

def getInterest(html):
  now = get_yestoday()
  reg=r'<td class="alignLeft">' + now + r'</td>\s*<td class="alienRight bold">\S+</td>\s*<td class="alienRight bold">(\S+)%</td>'
  m = re.search(reg, html)
  if m:
    return m.group(1)
  else:
    return ''

url="http://fund.eastmoney.com/000198.html?spm=aladin"
req_header = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11',
'Accept':'text/html;q=0.9,*/*;q=0.8',
'Accept-Charset':'ISO-8859-1,utf-8;q=0.7,*;q=0.3',
'Accept-Encoding':'gzip',
'Connection':'close',
'Referer':None #注意如果依然不能抓取的话，这里可以设置抓取网站的host
}
req_timeout = 5
req = urllib2.Request(url,None,req_header)
resp = urllib2.urlopen(req,None,req_timeout)
html = resp.read()
print getInterest(html)
