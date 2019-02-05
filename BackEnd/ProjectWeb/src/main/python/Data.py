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
    """
    :param argv[0] file name: "md5File"
    :param argv[1] the file path  "C:\\Studio\\BigDataProject/data/source/md5File
    :param argv[2] the temp save path "C:\\Studio\\BigDataProject/data/result/md5File"
    :return
    """
    #  str = " "
    # user_name = str.join(argv)
    user_name = argv[0]
    # address = os.path.split(os.path.realpath(__file__))[0]
    address = argv[1]
    file_save = argv[2]
    file_name = "MyActivity.html"
    # base_path = address + "\\data\\source\\Takeout\\My Activity\\"

    # Linux
    # base_path = address +"/Takeout/My Activity";

    # Windows
    base_path = address +"\\Takeout\\My Activity";

    Activity_Data = list()
    E_Activity = ExtractActivity.Extract_Activity()
    dirs = os.listdir(base_path)
    for name in range(len(dirs)):
       # Activity_Data = E_Activity.Extract_Data(base_path+"/"+dirs[name]+"/"+file_name, Activity_Data, user_name)
        Activity_Data = E_Activity.Extract_Data(base_path+"\\"+dirs[name]+"\\"+file_name, Activity_Data, user_name)
    Activity_Data = json.dumps(Activity_Data)
    f = open(file_save + "\\ActivityData.txt",'w')
    f.write(Activity_Data)
    f.close
    # print(name)

if __name__ == "__main__":
    main(sys.argv[1:])