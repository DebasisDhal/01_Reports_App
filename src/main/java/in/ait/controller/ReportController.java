package in.ait.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ait.entity.CitizenPlan;
import in.ait.request.SearchRequest;
import in.ait.service.ReportService;

@Controller
public class ReportController {
	
	@Autowired
	private ReportService service;
	
	@PostMapping("/search")
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {
		System.out.println(search);
		
		List<CitizenPlan> plans = service.search(search);
		model.addAttribute("plans", plans);
		
		init(model);
		
		return "index";
	}
	
	@GetMapping("/")
	public String indexPage(Model model) {
		model.addAttribute("search", new SearchRequest());
		
	//	SearchRequest searchObj;
		
		
		init(model);
		return "index";
	}

	private void init(Model model) {
		
		model.addAttribute("names", service.getPlanNames());
		model.addAttribute("status", service.getPlanStatus());
	}

}
