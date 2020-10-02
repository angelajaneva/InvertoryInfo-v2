package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public List<Analysis> getAllAnalysis() {
        return analysisRepository.findAll();
    }

    @Override
    public Analysis getAnalysisById(int id) {
        return analysisRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Analysis saveAnalysis(Analysis analysis) {
        return analysisRepository.save(analysis);
    }

    @Override
    public Analysis editAnalysis(Analysis analysis) {
        return analysisRepository.save(analysis);
    }

    @Override
    public void deleteAnalysis(int id) {
        Analysis analysis = analysisRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        analysis.setDeleted(true);
        analysisRepository.save(analysis);
    }

    @Override
    public Analysis saveFromFile(MultipartFile file) {
        //Treba da se implementira
        return null;
    }
}
