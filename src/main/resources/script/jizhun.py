#! /usr/bin/env python
# -*- coding=utf-8 -*- 
# @Author pythontab.com
import urllib2
import re
import time
import datetime

def get_yestoday():
  myday = datetime.datetime.now()  
  delta = datetime.timedelta(days=-1)  
  my_yestoday = myday + delta  
  my_yes_time = my_yestoday.strftime('%Y-%m-%d')  
  return my_yes_time

def getInterest(html):
  reg=r'<td>整存整取\s*一年</td>\s*<td>(\S+)</td>'
  m = re.search(reg, html)
  if m:
    return m.group(1)
  else:
    return ''

url="http://www.cmbchina.com/CmbWebPubInfo/InterestRate.aspx?chnl=ckrate"
req_header = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11',
'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
'Accept-Language':'zh-CN,zh;q=0.8,en;q=0.6',
'Accept-Encoding':'gzip, deflate, sdch',
'Connection':'close',
'Host':'www.cmbchina.com',
'Referer':None #注意如果依然不能抓取的话，这里可以设置抓取网站的host
}
req_timeout = 5
req = urllib2.Request(url,None,req_header)
resp = urllib2.urlopen(req,None,req_timeout)
html = resp.read()
print getInterest(html)
