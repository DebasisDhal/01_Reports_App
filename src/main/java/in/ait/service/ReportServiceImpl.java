package in.ait.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
	public boolean exportExcel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exportPdf() {
		// TODO Auto-generated method stub
		return false;
	}

}
