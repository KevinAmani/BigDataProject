package com.bigdata.controller.FileUpLoad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Reference:https://zhuanlan.zhihu.com/p/29526454
 */

@Controller
public class Uploader {
	
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
		String filepath=System.getProperty("user.dir");
		String data_file = filepath+"\\src\\main\\python\\data\\temp\\";
		String path = data_file+md5File+"\\";//The link where does the piece save
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
		String filepath=System.getProperty("user.dir");
		String data_file = filepath+"\\src\\main\\python\\data\\temp\\";
		String path = data_file+md5File+"\\";
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
		String filepath=System.getProperty("user.dir");
		String path = filepath+"\\src\\main\\python\\data\\";
		FileOutputStream fileOutputStream = new FileOutputStream(path+"zip\\"+name);  //The final file
		try {
			byte[] buf = new byte[1024];  
	        for(long i=0;i<chunks;i++) {
	             String chunkFile=i+".tmp";
	             File file = new File(path+"\\temp\\"+md5File+"\\"+chunkFile);
	             InputStream inputStream = new FileInputStream(file);
	             int len = 0;  
	             while((len=inputStream.read(buf))!=-1) {
                     fileOutputStream.write(buf, 0, len);
                 }
	             inputStream.close();
	         }
	         File file = new File(path+"\\temp\\"+md5File+"\\");
	        file.delete();
	        //delete the temp file
	        
		} catch (Exception e) {
			return false;
		}finally {
			fileOutputStream.close(); 
		}
		return true;
	}
}
