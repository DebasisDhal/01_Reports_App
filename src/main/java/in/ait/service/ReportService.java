package in.ait.service;

import java.util.List;

import in.ait.entity.CitizenPlan;
import in.ait.request.SearchRequest;

public interface ReportService {
	
	public List<String> getPlanNames();

	public List<String> getPlanStatus();
	
	public List<CitizenPlan> search(SearchRequest request);
	
	public boolean exportExcel();
	
	public boolean exportPdf();

}
