package com.bigdata.other;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PythonManager {


    //Used to run python command
    public String RunPythonCommand(String python_command){

        String output = null;

        try{
            Process process = Runtime.getRuntime().exec(python_command);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while((line=in.readLine())!=null){
                output = output + line + "\n";
            }
            in.close();
            int re = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return output;
        }
    }

}
