package mk.gov.moepp.emi.invertoryinfo.repository;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {
}
