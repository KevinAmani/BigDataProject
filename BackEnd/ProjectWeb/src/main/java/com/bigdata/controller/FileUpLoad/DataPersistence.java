package com.bigdata.controller.FileUpLoad;

import com.bigdata.model.UserGoogleZip.UserActivity;
import com.bigdata.other.PythonManager;
import com.bigdata.repository.UserGoogleZip.UserActivityRepository;
import com.bigdata.other.UnZip;
import com.bigdata.service.UserGoogleZip.UserActivityServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

@Controller
public class DataPersistence {

    @Autowired
    private UserActivityServiceImpl userActivityService;

    private PythonManager pythonManager = new PythonManager();

    //The name of the zip file that updated by user
    private String file_name;
    private String md5File;

    //The base path where saving the file of data
    @Value(value="${data_path}")
    private String data_dir_path;

    //The root path of the python script
    @Value(value = "${python_path}")
    private String python_dir_path;

    private String upload_save_path;

    private String unzip_path;

    /**
     *
     * @param md5File
     * @param filename
     * @return
     * @throws IOException
     */


    @PostMapping("data_persistence")
    public @ResponseBody String persistentData(@RequestParam(value="md5File") String md5File, @RequestParam(value="filename") String filename) throws IOException {
        this.file_name = filename;
        this.md5File = md5File;

        if(unZip_File()){
            //success : fetch data from unzip document
            if(fetchDatafromGoogleDocument()){
                if(saveDatafromTxtFile()){
                    return "Success";
                }

            }
        }
        return "Fail";
    }



    /**
     * This functionality is used to unzip
     * @return
     */

    public boolean unZip_File(){

        //The dir path of data    "data_dir_path/data"
        String data_dir_data_path = data_dir_path +  File.separator +"data";

        try{
            UnZip unZip = new UnZip();

            //The link where the document saved  "data_dir_path/data/zip/md5File/file_name"
            upload_save_path = data_dir_data_path + File.separator +"zip" + File.separator + md5File + File.separator + file_name;

            //The link where the zip file be extracted  "data_dir_path/data/source/md5File/fil"
            unzip_path = data_dir_data_path + File.separator +"source" + File.separator + md5File;

            //Unzip the file
            if(unZip.unzip(upload_save_path, unzip_path)){
                return true;
            }else{
                return false;
            }

        }catch(Exception e){
            return false;
        }
    }




    /**
     *
     * @return
     * @throws IOException
     * etch data from unzip document and create temp txt file
     */

    public boolean fetchDatafromGoogleDocument() throws IOException {

        //The python script path   "python_dir_path/Data.py"
        String python_file_Data = python_dir_path + File.separator + "Data.py";

        //The place export the txt file data   "data_dir_path/data/result/md5File"
        String data_txt_save_path = data_dir_path + File.separator + "data" + File.separator + "result" + File.separator + md5File;

        //Check if the temp document exist
        File f = new File(data_txt_save_path);
        if(!f.exists()){// not exist
            f.mkdir();
        }

        try {
            // Use command to run python
            String python_command = "python " + python_file_Data + " " + md5File + " " + upload_save_path + " " + data_txt_save_path;
            pythonManager.RunPythonCommand(python_command);
            //String arguments = "python C:\\BigDataProject\\ProjectWeb\\src\\main\\java\\com\\bigdata\\service\\python\\Data.py";
        }catch (Exception e){
            return false;
        }
        return true;

    }



    /**
     * Save the data to database from datafile
     */

    public boolean saveDatafromTxtFile(){
        try{
            //The path of activityData.txt  "data_dir_path/data/result/md5File/ActivityData.txt"
            String data_file = data_dir_path + File.separator + "data" + File.separator + "result" + File.separator + md5File + File.separator + "ActivityData.txt";

            //open Activity.txt
            File file = new File(data_file);
            JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            Gson gson = new GsonBuilder().create();

            //read file n stream mode
            reader.beginArray();
            while(reader.hasNext()){
                UserActivity activity = gson.fromJson(reader, UserActivity.class);
                //Enhance: Check if it is replication
                userActivityService.saveUserActivity(activity);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
