# -*- coding: utf-8 -*-
"""
Created on Wed Oct 31 13:58:46 2018

@author: Kaiwen Zhu
"""

import re
from bs4 import BeautifulSoup




class Extract_Activity:
    
        def __init___(self):
            self.amount = 0
            
        
        def Extract_Data(self, path, Datas, user_name):
            soup = BeautifulSoup(open(path),"html.parser")
            Soups = soup.find_all('div','outer-cell mdl-cell mdl-cell--12-col mdl-shadow--2dp')
            for s in range(len(Soups)):
                data = dict()

                ## User_Name
                data["user_name"] = user_name

                ## 0: The Devcie name or Website Name
                try:
                    data["type"] = Soups[s].find('p','mdl-typography--title').get_text().strip(' ')
                except:
                    data["type"] = None
    
                link_block = Soups[s].find('div','content-cell mdl-cell mdl-cell--6-col mdl-typography--body-1')
    
    
                ## 1: The website link, some items don't have link
                try:
                    data["link"] = link_block.a['href'].strip(' ')
                except:
                    data["link"] = None
        
    
                ## 2: The Time
                try:
                    Time = re.search(r'[A-Z][a-z]{1,2} [0-9]{1,2}, 20[0-9]{1,2}, [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} [A-Z]{1,2} [A-Z]{1,3}',link_block.get_text()).group()
                    data["time"] = Time
                except:
                    data["time"] = None
    
    
                ## 3: The Action or the title of the link
                try:
                    data["content"] = link_block.text.strip(Time)
                except:
                    data["content"] = None
                try:
                    Detail = Soups[s].find('div','content-cell mdl-cell mdl-cell--12-col mdl-typography--caption')
                    Txt = Detail.text

                    D = Txt.split(u"\u2003")
                    if len(D)>=2:
                        From = D[1]
                        From = From.strip("Locations:")
                        data["from_source"] = From
                    else:
                        data["from_source"] = None
                except:
                    data["from_source"] = None

                try:
                    ## 4: The detail
                    Location = Detail.a.text
                    ## The location from google map
                    data["map_location"] = Location
                except:
                    data["map_location"] = None
                Datas.append(data)
            return Datas
