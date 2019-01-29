package com.bigdata.service;



import com.bigdata.model.UserGoogleZip.UserActivity;
import com.bigdata.model.UserGoogleZip.UserActivityRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class FetchClientDatafromZip {
    private UserActivityRepository userActivityRepository;

    public void fetchDatafromZip() throws IOException {

        //Need to change, just for test
        String user_name="Kaiwen Zhu";

        String filepath=System.getProperty("user.dir");
        String python_file = filepath+"\\src\\main\\python\\Data.py ";
        String arguments = "python "+ python_file + user_name;
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
    }
    /**
    public void fetchDatafromFile(){

        try{
            String filepath=System.getProperty("user.dir");
            String data_file = filepath+"\\src\\main\\python\\data\\result\\ActivityData.txt";
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
    **/
    /**
    public static void main(String[] args) throws IOException {
       FetchClientDatafromZip fetch = new FetchClientDatafromZip();
      fetch.fetchDatafromZip();
    }
    **/
}
