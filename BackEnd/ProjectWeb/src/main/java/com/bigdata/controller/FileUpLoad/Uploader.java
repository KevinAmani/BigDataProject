package com.bigdata.controller.FileUpLoad;

import com.bigdata.service.UnZip;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Reference:https://zhuanlan.zhihu.com/p/29526454
 */

@Controller
public class Uploader {

    private String filepath;

    public Uploader(@Value(value="${data_path}") String basepath){
        this.filepath = basepath;
        String resultpath=filepath+File.separator+"data"+File.separator+"result"+File.separator;
        File resultlink = new File(resultpath);
        if(!resultlink.exists()){
            resultlink.mkdirs();
        }

        String sourcepath = filepath+File.separator+"data"+File.separator+"source"+File.separator;
        File sourcelink = new File(sourcepath);
        if(!sourcelink.exists()){
            sourcelink.mkdirs();
        }

        String temppath = filepath+File.separator+"data"+File.separator+"temp"+File.separator;
        File templink = new File(temppath);
        if(!templink.exists()){
            templink.mkdirs();
        }

        String zippath = filepath+File.separator+"data"+File.separator+"zip"+File.separator;
        File ziplink = new File(zippath);
        if(!ziplink.exists()){
            ziplink.mkdirs();
        }
    }
	
	@GetMapping( "/")
    public String page(){
        return "upload";  
	}
	
	/**
	 * @author van
	 * Check if the file is unique
	 */
	@PostMapping("checkFile")
    @ResponseBody
    public Boolean checkFile(@RequestParam(value = "md5File") String md5File) {
		Boolean exist = false;
		
		//Save the md5File to
		/*if(true) {
			exist = true;
		}*/
		return exist;
	}
	
	/**
	 * @author van
	 * Check if the piece is exist
	 */
	@PostMapping("checkChunk")
	@ResponseBody
	public Boolean checkChunk(@RequestParam(value = "md5File") String md5File,
                              @RequestParam(value = "chunk") Integer chunk) {
		Boolean exist = false;
		String store_path = File.separator+"data"+File.separator;
		String data_file = filepath+store_path+"temp"+File.separator;
		String path = data_file+md5File+File.separator;//The link where does the piece save
		String chunkName = chunk+ ".tmp";//The name of piece
		File file = new File(path+chunkName);
        if (file.exists()) {
        	exist = true;
        }
        return exist;
	}
	
	/**
	 * @author van
	 * Modify uploading
	 */
	@PostMapping("upload")
	@ResponseBody
	public Boolean upload(@RequestParam(value = "file") MultipartFile file,
                          @RequestParam(value = "md5File") String md5File,
                          @RequestParam(value = "chunk",required= false) Integer chunk) { //The temp file starts from 0
        String store_path = File.separator+"data"+File.separator;
		String data_file = filepath+store_path+"temp"+File.separator;
		String path = data_file+md5File+File.separator;
		File dirfile = new File(path);
		if (!dirfile.exists()) {//if the file is not exist, create one
			dirfile.mkdirs();
		}
		String chunkName;
		if(chunk == null) {//small file
			chunkName = "0.tmp";
        }else {
        	chunkName = chunk+ ".tmp";
        }
		String filePath = path+chunkName;
		File savefile = new File(filePath);
		
		try {
			if (!savefile.exists()) {
				savefile.createNewFile();//create it
			}
			file.transferTo(savefile);//save file
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * @author van
	 * Merge
	 */
	@PostMapping("merge")
    @ResponseBody
    public Boolean merge(@RequestParam(value = "chunks",required =false) Integer chunks,
                         @RequestParam(value = "md5File") String md5File,
                         @RequestParam(value = "name") String name) throws Exception {
        String store_path = File.separator+"data"+File.separator;
		String data_path = filepath+store_path;

		//All zip files are saved at here
		File dirfile = new File(data_path+"zip"+File.separator+md5File);
		if (!dirfile.exists()) {//if the file is not exist, create one
			dirfile.mkdirs();
		}

		//The file name is md5File+name
		FileOutputStream fileOutputStream = new FileOutputStream(data_path+"zip"+File.separator+md5File+File.separator+name);  //The final file
		try {
			byte[] buf = new byte[1024];
	        for(long i=0;i<chunks;i++) {
	             String chunkFile=i+".tmp";
	             File file = new File(data_path+"temp"+File.separator+md5File+File.separator+chunkFile);
	             InputStream inputStream = new FileInputStream(file);
	             int len = 0;
	             while((len=inputStream.read(buf))!=-1) {
                     fileOutputStream.write(buf, 0, len);
                 }
	             inputStream.close();
	             file.delete();
	         }
            File dir_file = new File(data_path+"temp"+File.separator+md5File+File.separator);
	        dir_file.delete();
	        //delete the temp file
	        
		}catch (Exception e) {
			return false;
		}finally {
			fileOutputStream.close();
		}

		return true;
	}
}
