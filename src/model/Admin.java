package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.Database;


public class Admin extends User {
	
	public Admin() {}
	
	@Override
	public boolean login() {
		super.conn = Database.getConnection();
		
		if (conn == null) return false;
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM administration WHERE id = ? AND type = 'admin';");
			stmt.setInt(1, super.userId);
			ResultSet rs = stmt.executeQuery();
			
			if (!rs.next()) return false;
			
			String password = rs.getString("password");
			return super.isPasswordValid(password);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean addRegistry(Registry r) {
		return r.add(this.conn);
	}
	
	public boolean deleteRegistry(Registry r) {
		return r.delete(this.conn);
	}
	
	public boolean addLecturer(Lecturer l) {
		return l.add(this.conn);
	}
	
	public boolean updateLecturer(Lecturer l) {
		return l.update(this.conn);
	}
	
	public boolean assignModuleToLecturer(Lecturer l, int moduleCode) {
		
		if (l.teaches(moduleCode)) return false;
		
		return l.assignModule(this.conn, moduleCode);
	}
	
	public boolean addCourse(Course c) {
		return c.add(this.conn);
	}
	
	public boolean updateCourse(Course c) {
		return c.update(this.conn);
	}
	
	public boolean loadReport(Map<LocalDate, ArrayList<Boolean>> attendances, ArrayList<Integer> studentIds, ArrayList<String> studentNames, String moduleName, int lecturerId) {
		// Reference: https://www.baeldung.com/java-microsoft-excel
		
		String excelSheetName = String.format("%s-lecturerId<%d>#%s", moduleName, lecturerId, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(excelSheetName);

		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);

		Cell headerCell;
		Cell cell;
		Row row;
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);

		// Populate StudentId column
		headerCell = header.createCell(0);
		headerCell.setCellValue("studentId");
		headerCell.setCellStyle(headerStyle);
		sheet.setColumnWidth(0, 4000);

		int rowNumber = 1;
		int colNumber = 0;
		for (int studentId : studentIds) {
			row = sheet.createRow(rowNumber++);
			cell = row.createCell(colNumber);
			cell.setCellValue(studentId);
			cell.setCellStyle(style);
		}
		
		// Populate StudentName column
		headerCell = header.createCell(1);
		headerCell.setCellValue("studentName");
		headerCell.setCellStyle(headerStyle);
		sheet.setColumnWidth(1, 6000);

		rowNumber = 1;
		colNumber++;
		for (String studentName : studentNames) {
			row = sheet.getRow(rowNumber++);
			cell = row.createCell(colNumber);
			cell.setCellValue(studentName);
			cell.setCellStyle(style);
		}
		
		// Populate Attendances columns
		colNumber++;
		for (Map.Entry<LocalDate, ArrayList<Boolean>> entry : attendances.entrySet()) {
			// Set attendance date
			headerCell = header.createCell(colNumber);
			headerCell.setCellValue(entry.getKey().toString());
			headerCell.setCellStyle(headerStyle);
			sheet.setColumnWidth(colNumber, 6000);
			
			// Set column date
			rowNumber = 1;
			for (boolean presence : entry.getValue()) {
				row = sheet.getRow(rowNumber++);
				cell = row.createCell(colNumber);
				cell.setCellValue( (presence) ? "Present" : "Absent" );
				cell.setCellStyle(style);
			}
			colNumber++;
		}
		
		// Write to file
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream("./" + excelSheetName + ".xlsx");
			workbook.write(outputStream);
			workbook.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean loadDefaultersList(Map<Integer, ArrayList<LocalDate>> defaulterDatesAbsentMap, Map<Integer, String> defaulterIdNameMap, String moduleName, int lecturerId) {
		// Reference: https://www.baeldung.com/java-microsoft-excel
		
		String excelSheetName = String.format("Defaulters-%s-lecturerId<%d>#%s", moduleName, lecturerId, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(excelSheetName);

		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);

		Cell headerCell;
		Cell cell;
		Row row;
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);

		headerCell = header.createCell(0);
		headerCell.setCellValue("studentId");
		headerCell.setCellStyle(headerStyle);
		sheet.setColumnWidth(0, 4000);
		
		headerCell = header.createCell(1);
		headerCell.setCellValue("studentName");
		headerCell.setCellStyle(headerStyle);
		sheet.setColumnWidth(1, 6000);

		int rowNumber = 1;
		int colNumber;
		for (Map.Entry<Integer, ArrayList<LocalDate>> entry : defaulterDatesAbsentMap.entrySet()) {
			row = sheet.createRow(rowNumber++);
			
			// Student Id
			colNumber = 0;
			cell = row.createCell(colNumber++);
			cell.setCellValue(entry.getKey());
			cell.setCellStyle(style);
			
			// Student Name
			cell = row.createCell(colNumber++);
			cell.setCellValue(defaulterIdNameMap.get(entry.getKey()));
			cell.setCellStyle(style);
			
			// Dates Absent
			for (LocalDate dateAbsent : entry.getValue()) {
				sheet.setColumnWidth(colNumber, 6000);
				cell = row.createCell(colNumber++);
				cell.setCellValue(dateAbsent.toString());
				cell.setCellStyle(style);
			}
		}
		
		// Write to file
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream("./" + excelSheetName + ".xlsx");
			workbook.write(outputStream);
			workbook.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
