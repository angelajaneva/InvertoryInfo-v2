package mk.gov.moepp.emi.invertoryinfo.repository;

import mk.gov.moepp.emi.invertoryinfo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyseCategoryGasRepository extends JpaRepository<AnalysisCategoryGas, Integer> {

    AnalysisCategoryGas findByAnalysis_IdAndCategory_IdAndGas_Id(int analysisId, int categoryId, int gasId);

    List<AnalysisCategoryGas> findByAnalysis_IdAndCategory_Id(int analysisId, int categoryId);

    List<AnalysisCategoryGas> findByGas_IdAndCategory_Id(int gasId, int categoryId);

}
