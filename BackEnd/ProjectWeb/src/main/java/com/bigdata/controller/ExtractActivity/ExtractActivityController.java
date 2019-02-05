package com.bigdata.controller.ExtractActivity;

import com.bigdata.model.UserGoogleZip.UserActivity;
import com.bigdata.model.UserGoogleZip.UserActivityRepository;
import com.bigdata.service.FetchClientDatafromZip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.*;

@Controller
public class ExtractActivityController {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @GetMapping("/savedata")
    public void fetchDatafromFile(){
        try{
            String filepath=System.getProperty("user.dir");
            String data_file = filepath+File.separator+"src"+File.separator+"main"+File.separator+"python"+File.separator+"data"+File.separator+"result"+File.separator+"ActivityData.txt";
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

    public void unZipUserFile(){

    }

}
