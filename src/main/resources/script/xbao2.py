#! /usr/bin/env python
# -*- coding=utf-8 -*- 
# @Author pythontab.com
import urllib2
import re
import time
import datetime
import commands

def get_yestoday():  
    myday = datetime.datetime.now()  
    delta = datetime.timedelta(days=-1)  
    my_yestoday = myday + delta  
    my_yes_time = my_yestoday.strftime('%Y-%m-%d')  
    return my_yes_time

def getInterest(html):
  now = get_yestoday()
  reg=r'{"date":"' + now + r'","qiri":"(\d*.\d*)","'
  m = re.search(reg, html)
  if m:
    return m.group(1)
  else:
    return 'not match'

file = r'/stone/script/python/xbao2Htmls/qiriIframe_' + get_yestoday() + r'.htm'
command = r'wget https://yebprod.alipay.com/yeb/iframe/qiriIframe.htm -O ' + file
commands.getstatusoutput(command)
file_object = open(file)
try:
     html = file_object.read()
finally:
     file_object.close()
print getInterest(html)
