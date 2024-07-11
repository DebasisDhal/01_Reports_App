package in.ait.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.ait.entity.CitizenPlan;
import in.ait.repo.CitizenPlanRepository;
import in.ait.request.SearchRequest;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private CitizenPlanRepository planRepo;

	@Override
	public List<String> getPlanNames() {
		// TODO Auto-generated method stub
		return planRepo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {
		// TODO Auto-generated method stub
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		// TODO Auto-generated method stub
		CitizenPlan entity = new CitizenPlan();
		
		if(null != request.getPlanName() && !"".equals(request.getPlanName())) {
			entity.setPlanName(request.getPlanName());
		}
		if(null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		if(null != request.getGender() && !"".equals(request.getGender())) {
			entity.setGender(request.getGender());
		}
		
//		if(null != request.getStartDate() && !"".equals(request.getStartDate())) {
//			String startDate = request.getStartDate();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//					LocalDate localDate =LocalDate.parse(startDate, formatter);
//					entity.setPlanStartDate(localDate);
//		}
//		
//		if(null != request.getEndDate() && !"".equals(request.getEndDate())) {
//			String startDate = request.getEndDate();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//					LocalDate localDate = LocalDate.parse(endDate, formatter);
//					entity.setPlanEndDate(localDate);
//		}
		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response)throws Exception {
		
		List<CitizenPlan> records = planRepo.findAll();
		
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
		
		List<CitizenPlan> record = planRepo.findAll();
		
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
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Paragraph p = new Paragraph("citizen Plans Info");
		document.add(p);
		
		
		PdfPTable table = new PdfPTable(6);
		
		table.addCell("Id");
		table.addCell("citizenName");
		table.addCell("planName");
		table.addCell("Plan Status");
		table.addCell("Start Date");
		table.addCell("End Date");
		
		List<CitizenPlan> plans = planRepo.findAll();
		
		for(CitizenPlan plan : plans) {
			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanStatus());
			table.addCell(String.valueOf(plan.getPlanStartDate()));
			table.addCell(String.valueOf(plan.getPlanEndDate()));
		}
		
		
		document.add(table);
		document.close();
		
		return true;
	}
	
	


}
