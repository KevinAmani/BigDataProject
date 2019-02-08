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
	#Windows
	system_separator = "\\"
	
	#Linux
	#system_separator = "/"
	
    #  str = " "
    # user_name = str.join(argv)
    user_name = argv[0]
    
	# the path that save the data
    address = argv[1]
    
	# the path of the unzip file
	file_save = argv[2]
    
	file_name = "MyActivity.html"

    # Windows
    base_path = address + system_separator + "Takeout" + system_separator + "My Activity";

	# the class of the extracter
    Activity_Data = list()
    E_Activity = ExtractActivity.Extract_Activity()
	
	# get the file names
    dirs = os.listdir(base_path)
    for name in range(len(dirs)):
		# The file name of subfile
		# subfile_path = 
		subfile_path = base_path + system_separator + dirs[name] + system_separator + file_name
        Activity_Data = E_Activity.Extract_Data(subfile_path, Activity_Data, user_name)
		
    Activity_Data = json.dumps(Activity_Data)
    f = open(file_save + system_separator + "ActivityData.txt",'w')
    f.write(Activity_Data)
    f.close

if __name__ == "__main__":
    main(sys.argv[1:])