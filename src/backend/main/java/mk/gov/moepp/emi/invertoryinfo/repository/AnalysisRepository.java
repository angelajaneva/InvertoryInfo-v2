package mk.gov.moepp.emi.invertoryinfo.repository;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;

public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {

    Analysis findByYearEquals(String year);

}
