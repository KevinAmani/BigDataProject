package com.bigdata.service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class UnZip {

    /*
     * @param srcZipFile name
     * @return  true/false
     * reference: https://my.oschina.net/lovesesun/blog/76823
     */

    public boolean unzip(String srcZipFile, String saveZipPath) {
        boolean isSuccessful = true;
        try {
            File file = new File(srcZipFile);
            File outFile = null;
            ZipFile zipFile = new ZipFile(file);
            ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
            ZipEntry entry = null;
            InputStream input = null;
            OutputStream output = null;

            while((entry = zipInput.getNextEntry())!=null){
                String subfile_path = saveZipPath + File.separator + entry.getName();
                //subfile_path = new String(subfile_path.getBytes("GBK"),"UTF-8");
                outFile = new File(subfile_path);
                if(!outFile.getParentFile().exists()){
                    outFile.getParentFile().mkdirs();
                }
                if(!outFile.exists()){
                    outFile.createNewFile();
                }
                input = zipFile.getInputStream(entry);
                InputStreamReader input_reader = new InputStreamReader(input, "UTF-8");

                output = new FileOutputStream(outFile);
                OutputStreamWriter output_writer = new OutputStreamWriter(output, "UTF-8");

                int temp = 0;
                while ((temp = input_reader.read())!=-1){
                    output_writer.write(temp);
                }
                input_reader.close();
                output_writer.close();
            }

        } catch (ZipException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }



}
