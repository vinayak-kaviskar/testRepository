/**
 * 
 */
package com.practice.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author vinayakk5
 *
 */



@RestController
@RequestMapping(value = "/uploadFile")
public class FileContoller {
	
	private static String UPLOADED_FOLDER = "D://UploadedFiles1//folder1//";
	
	@RequestMapping(value = "/pdffile",method = RequestMethod.POST)
	public String getFile(@RequestParam(value = "file") MultipartFile file,@RequestParam(value = "file2", required=false) MultipartFile file2,@RequestParam("file3") String req,   RedirectAttributes redirectAttributes)
	
	// public String getFile(@RequestBody String file)
	{
		System.out.println(req);
		if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
		
		
		File directory = new File(UPLOADED_FOLDER);
		
		if (! directory.exists()){
	        directory.mkdir();
	        System.out.println("making dir");
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
		
        try {
        	System.out.println(file.getContentType());
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            System.out.println(path.toString());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
        	System.out.println(file2.getContentType());
            // Get the file and save it somewhere
            byte[] bytes = file2.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file2.getOriginalFilename());
            System.out.println(path.toString());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file2.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
	}
	

}
