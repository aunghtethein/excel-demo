package com.ai.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai.model.TeamStructure;
import com.ai.repo.TeamStructureRepo;
import com.ai.util.ExcelUtil;

@Service
public class FileService {
	@Autowired
	private TeamStructureRepo teamRepo;
	
	//Store File Data to database
	public void store(MultipartFile file) throws IOException {
		try {
			List<TeamStructure> tsList = ExcelUtil.parseExcelFile(file.getInputStream());
		//	System.out.println(tsList.get(2).getName());
			//Save Team Structure to Database
			teamRepo.saveAll(tsList);
			
		}catch (IOException e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}
	
	//Load Data to Excel File
	public ByteArrayInputStream loadFile() {
		List<TeamStructure> tsList = teamRepo.findAll();
		
		try {
			ByteArrayInputStream input = ExcelUtil.tsToExcel(tsList);
			return input;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
