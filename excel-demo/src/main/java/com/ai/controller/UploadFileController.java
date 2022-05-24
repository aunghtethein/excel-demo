package com.ai.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ai.service.FileService;

@Controller
public class UploadFileController {
	@Autowired
	private FileService service;
	
	@GetMapping("/")
    public String index() {
        return "uploadform.html";
    }
	@PostMapping("/")
	public String uploadMultipartFile(@RequestParam("uploadfile")MultipartFile file,Model model) {
		try {
			service.store(file);
			model.addAttribute("message", "File uploaded successfully!");
		}catch (Exception e) {
			model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
		}
		return "uploadform.html";
	}
	
	/**
	 * Download File
	 *
	 */
	@GetMapping("/file")
	public ResponseEntity<InputStreamResource> downloadFile() {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=Team Structure.xlsx");
		
		return ResponseEntity
                .ok()
                .headers(header)
                .body(new InputStreamResource(service.loadFile()));
	}
}
