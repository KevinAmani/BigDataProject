# -*- coding: utf-8 -*-
"""
Created on Wed Oct 31 22:05:40 2018

@author: Kaiwen Zhu

Extract the data by document name. All the document of "Activity" is used by same object.
"""

import os
import ExtractActivity 
import json
import sys

def main(argv):
    str = " "
    user_name = str.join(argv)
    address = os.path.split(os.path.realpath(__file__))[0]
    file_name = "MyActivity.html"
    base_path = address + "\\data\\source\\Takeout\\My Activity\\"
    Activity_Data = list()
    E_Activity = ExtractActivity.Extract_Activity()
    dirs = os.listdir(base_path)
    for name in range(len(dirs)):
       Activity_Data = E_Activity.Extract_Data(base_path+dirs[name]+"\\"+file_name, Activity_Data, user_name)
    Activity_Data = json.dumps(Activity_Data)
    f = open(address + "\\data\\result\\ActivityData.txt",'w')
    f.write(Activity_Data)
    f.close
    ##print(name)

if __name__ == "__main__":
    main(sys.argv[1:])