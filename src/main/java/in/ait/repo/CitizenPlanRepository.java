package in.ait.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import in.ait.entity.CitizenPlan;

@EnableJpaRepositories
public interface CitizenPlanRepository extends JpaRepository<CitizenPlan, Integer>{
	
	@Query("select distinct(planName) from CitizenPlan")
	public List<String> getPlanNames();
	
	@Query("select distinct (planStatus) from CitizenPlan")
	public List<String> getPlanStatus();

}
