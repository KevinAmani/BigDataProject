package com.bigdata.controller.FileUpLoad;

import com.bigdata.model.UserGoogleZip.UserActivity;
import com.bigdata.model.UserGoogleZip.UserActivityRepository;
import com.bigdata.service.UnZip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

@Controller
public class Unziper {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @PostMapping("unzip")
    @ResponseBody
    public boolean unzip_File(@RequestParam(value="md5File") String md5File, @RequestParam(value="filename") String filename, @Value(value="${data_path}") String filepath, @Value(value = "${python_path}") String python_path){
        String store_path = File.separator+"data"+File.separator;
        String data_path = filepath+store_path;
        try{
            UnZip unZip = new UnZip();

            //The link where the document save
            String save_Path = data_path +"zip"+File.separator+md5File+File.separator+filename;
            String unzip_Path = data_path +"source"+File.separator+md5File;
            //String save_Path = filepath + "//src//main//python//data//"+"//zip//" + md5File+name;
            //String unzip_Path = filepath + "//src//main//python//data//"+"source";
            if(unZip.unzip(save_Path, unzip_Path)){
                fetchDatafromZip(unzip_Path,md5File, filepath, python_path);
                return true;
            }else{
                return false;
            }
            //fetch data from file

        }catch(Exception e){
            return false;
        }
    }


    public void fetchDatafromZip(String file_link, String md5File, String save_path, String python_path) throws IOException {

        String filepath=System.getProperty("user.dir");

        //The python script path
        String python_file = python_path+File.separator+"Data.py";
        //Need to change, just for test
        String user_name = md5File;
        //String python_file = filepath+"\\src\\main\\python\\Data.py ";
        //The place save the temp data
        String data_save_path = save_path + File.separator + "data" + File.separator + "result" + File.separator + md5File;

        //Check if the temp document exist
        File f = new File(data_save_path );
        if(!f.exists()){// not exist
            f.mkdir();
        }
        // Use command to run python
        String arguments = "python "+ python_file +" " + user_name + " " + file_link + " " + data_save_path;

        //String arguments = "python C:\\BigDataProject\\ProjectWeb\\src\\main\\java\\com\\bigdata\\service\\python\\Data.py";
        try{
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while((line=in.readLine())!=null){
                System.out.println(line);
            }
            in.close();
            int re = process.waitFor();
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //save the data to database
        saveDatafromFile(md5File, save_path);
    }

    /**
     * Save the data to database from datafile
     * @param md5File
     * @param filepath
     */

    public void saveDatafromFile(String md5File, String filepath){
        try{
            //The path of activityData.txt
            String data_file = filepath + File.separator + "data" + File.separator + "result" + File.separator + md5File + File.separator + "ActivityData.txt";
            //String data_file = filepath+File.separator+"src"+File.separator+"main"+File.separator+"python"+File.separator+"data"+File.separator+"result"+File.separator+md5File+File.separator+"ActivityData.txt";
            File file = new File(data_file);
            JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            Gson gson = new GsonBuilder().create();

            //read file n stream mode
            reader.beginArray();
            while(reader.hasNext()){
                UserActivity activity = gson.fromJson(reader, UserActivity.class);
                //Enhance: Check if it is replication
                userActivityRepository.save(activity);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
