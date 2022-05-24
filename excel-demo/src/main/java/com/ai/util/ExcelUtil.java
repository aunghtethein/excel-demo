package com.ai.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ai.model.TeamStructure;

public class ExcelUtil {
	public static ByteArrayInputStream tsToExcel(List<TeamStructure> teamStructures) throws IOException {
		String [] COLUMNS = {"No", "Name", "StaffID", "Team", "Project", "Position"};
	
		try(
				Workbook workBook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		   ) {
			CreationHelper createHelper = workBook.getCreationHelper();
			
			Sheet sheet = workBook.createSheet("TeamStructure");
			
			Font headerFont = workBook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
			 
			CellStyle headerCellStyle = workBook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			
			//Row for Header
			Row headerRow = sheet.createRow(0);
			
			//Header
			for(int col=0; col < COLUMNS.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNS[col]);
				cell.setCellStyle(headerCellStyle);
			}
			
			//Cell Style for StaffID
			CellStyle staffIdCellStyle = workBook.createCellStyle();
			staffIdCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
			
			int rowIdx = 1;
			for(TeamStructure ts : teamStructures) {
				Row row = sheet.createRow(rowIdx++);
				
				row.createCell(0).setCellValue(ts.getNo());
				row.createCell(1).setCellValue(ts.getName());
				
				Cell staffIdCell = row.createCell(2);
				staffIdCell.setCellValue(ts.getStaffId());
				staffIdCell.setCellStyle(staffIdCellStyle);
				
				row.createCell(3).setCellValue(ts.getTeam());
				row.createCell(4).setCellValue(ts.getProject());
				row.createCell(5).setCellValue(ts.getPosition());
				
			}
			workBook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	public static List<TeamStructure> parseExcelFile(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
		  //  System.out.println(workbook.getSheetName(0)); 
		    
    		Sheet sheet = workbook.getSheet("Sheet1");
    		System.out.println(sheet);
        	
    		Iterator<Row> rows = sheet.iterator();
    		List<TeamStructure> tsList = new ArrayList<TeamStructure>();
	    		
    		int rowNum = 0;
    		while(rows.hasNext()) {
    			Row currentRow = rows.next();
    			
    			//Skip Header
    			if(rowNum == 0) {
    				rowNum++;
    				continue;
    			}
    			Iterator<Cell> cellsInRow = currentRow.iterator();
    			
    			TeamStructure ts = new TeamStructure();
    			
    			int cellIndex = 0;
    			while(cellsInRow.hasNext()) {
    				Cell currentCell = cellsInRow.next();
    				
    				if(cellIndex == 0) {	//No
    					ts.setNo((int)currentCell.getNumericCellValue());
    				}else if(cellIndex == 1) { //Name
    					ts.setName(currentCell.getStringCellValue());
    				}else if(cellIndex == 2) { //Staff ID
    					ts.setStaffId(currentCell.getStringCellValue());
    				}else if(cellIndex == 3) { //Team
    					ts.setTeam(currentCell.getStringCellValue());
    				}else if(cellIndex == 4) { //Project
    					ts.setProject(currentCell.getStringCellValue());
    				}else if(cellIndex == 5){ //Position
						ts.setPosition(currentCell.getStringCellValue());
					}
    				cellIndex++;
    			}
    			tsList.add(ts);
    		}
    		//Close Workbook
    		workbook.close();
	    	return tsList;
		} catch (IOException e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
		
	}
	
}
