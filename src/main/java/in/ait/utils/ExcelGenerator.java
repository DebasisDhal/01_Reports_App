package in.ait.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import in.ait.entity.CitizenPlan;


@Component
public class ExcelGenerator {
	
	public void generateExcel(HttpServletResponse response, List<CitizenPlan> records, File file)throws Exception {
	
	//List<CitizenPlan> records = citizenPlanRepository.findAll();
	
	Workbook workbook = new HSSFWorkbook();
	Sheet sheet = workbook.createSheet("plans-data");
	Row headerRow = sheet.createRow(0);
	
	headerRow.createCell(0).setCellValue("ID");
	headerRow.createCell(1).setCellValue("Citizen Name");
	headerRow.createCell(2).setCellValue("Plan Name");
	headerRow.createCell(3).setCellValue("gender");
	headerRow.createCell(4).setCellValue("Plan StartDate");
	headerRow.createCell(5).setCellValue("Plan endDate");
	headerRow.createCell(6).setCellValue("Benifit Amount");
	
	
	
	int dataIndex =1;
	
	for(CitizenPlan plan : records) {
		Row dataRow = sheet.createRow(dataIndex);
		dataRow.createCell(0).setCellValue(plan.getCitizenId());
		dataRow.createCell(1).setCellValue(plan.getCitizenName());
		dataRow.createCell(2).setCellValue(plan.getPlanName());
		dataRow.createCell(3).setCellValue(plan.getGender());
		if(null != plan.getPlanStartDate()) {
		dataRow.createCell(4).setCellValue(plan.getPlanStartDate()+ "");
		}else {
			dataRow.createCell(6).setCellValue("N/A");
		}
		
		if(null != plan.getPlanEndDate()) {
		dataRow.createCell(5).setCellValue(plan.getPlanEndDate()+ "");
		}else {
			dataRow.createCell(6).setCellValue("N/A");
		}
		
		if(null != plan.getBenefitAmt()) {
		dataRow.createCell(6).setCellValue(plan.getBenefitAmt());
		}else {
			dataRow.createCell(6).setCellValue("N/A");
		}
	
		dataIndex ++;
	}
	
	FileOutputStream fos = new FileOutputStream(file);
	workbook.write(fos);
	fos.close();
	
	ServletOutputStream outputStream = response.getOutputStream();
	workbook.write(outputStream);
	workbook.close();
	
 }
}
