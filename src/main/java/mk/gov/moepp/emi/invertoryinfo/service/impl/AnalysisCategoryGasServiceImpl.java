package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalyseCategoryGasRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisCategoryGasService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisCategoryGasServiceImpl implements AnalysisCategoryGasService {

    private final AnalyseCategoryGasRepository analyseCategoryGasRepository;

    public AnalysisCategoryGasServiceImpl(AnalyseCategoryGasRepository analyseCategoryGasRepository) {
        this.analyseCategoryGasRepository = analyseCategoryGasRepository;
    }

    @Override
    public List<AnalysisCategoryGas> getAllAnalysisCategoryGas() {
        return analyseCategoryGasRepository.findAll();
    }

    @Override
    public AnalysisCategoryGas getAnalysisCategoryGasById(int id) {
        return analyseCategoryGasRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public AnalysisCategoryGas saveAnalysisCategoryGas(Analysis analysis, Category category, Gas gas) {
        if (analysis != null && category != null && gas != null)
            return analyseCategoryGasRepository.save(new AnalysisCategoryGas(analysis, category, gas));
        else throw new ResourceNotFoundException("Analysis, Category or Gas cant be null");
    }

    @Override
    public AnalysisCategoryGas editAnalysisCategoryGas(AnalysisCategoryGas analysisCategoryGas) {
        return analyseCategoryGasRepository.save(analysisCategoryGas);
    }

    @Override
    public void deleteAnalysisCategoryGas(int id) {
        analyseCategoryGasRepository.deleteById(id);
    }

    @Override
    public List<AnalysisCategoryGas> findByAnalysisAndCategory(Analysis analysis, Category category) {
        if (analysis != null && category != null)
            return analyseCategoryGasRepository.findByAnalysis_IdAndCategory_Id(analysis.getId(), category.getId());
        else throw new ResourceNotFoundException("Analysis and Category not founded");
    }

    @Override
    public List<AnalysisCategoryGas> findByGasAndCategory(Gas gas, Category category) {
        if (gas != null && category != null)
            return analyseCategoryGasRepository.findByGas_IdAndCategory_Id(gas.getId(), category.getId());
        else throw new ResourceNotFoundException("Gas and Category not founded");
    }

    @Override
    public AnalysisCategoryGas findByAnalyseCategoryAndGas(Analysis analysis, Category category, Gas gas) {
        if (analysis != null && gas != null && category != null)
            return analyseCategoryGasRepository.findByAnalysis_IdAndCategory_IdAndGas_Id(analysis.getId(), gas.getId(), category.getId());
        else throw new ResourceNotFoundException("Analyse, Gas and Category not founded");
    }
}
