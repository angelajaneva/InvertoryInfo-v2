package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.Requests.CreateAnalysisRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnalysisService {

    List<Analysis> getAllAnalysis();

    Analysis getAnalysisById(int id);

    Analysis saveAnalysis(Analysis analysis);

    Analysis editAnalysis(Analysis analysis);

    void deleteAnalysis(int id);

    Analysis saveFromFile(CreateAnalysisRequest request);

}
