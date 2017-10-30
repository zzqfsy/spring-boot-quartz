import commands
import time
import datetime

def get_yestoday():  
    myday = datetime.datetime.now()  
    delta = datetime.timedelta(days=-1)  
    my_yestoday = myday + delta  
    my_yes_time = my_yestoday.strftime('%Y-%m-%d')  
    return my_yes_time

command = r'wget https://yebprod.alipay.com/yeb/iframe/qiriIframe.htm -O /stone/script/python/xbao2Htmls/qiriIframe_' + get_yestoday() + r'.htm'
commands.getstatusoutput(command)
