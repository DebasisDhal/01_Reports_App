package in.ait.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.ait.entity.CitizenPlan;

@Component
public class PdfGenerator {
	
	public void generatePdf(HttpServletResponse response, List<CitizenPlan> records, File f)throws Exception {
	
	Document document = new Document(PageSize.A4);
	PdfWriter.getInstance(document, response.getOutputStream());
	PdfWriter.getInstance(document, new FileOutputStream(f));
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
	
	
	for(CitizenPlan plan : records) {
		table.addCell(String.valueOf(plan.getCitizenId()));
		table.addCell(plan.getCitizenName());
		table.addCell(plan.getPlanName());
		table.addCell(plan.getPlanStatus());
		table.addCell(String.valueOf(plan.getPlanStartDate()));
		table.addCell(String.valueOf(plan.getPlanEndDate()));
	}
		
	document.add(table);
	document.close();

 }
}
