package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisCategoryGasRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisCategoryGasService;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AnalysisCategoryGasServiceImpl implements AnalysisCategoryGasService {

    private final AnalysisCategoryGasRepository analysisCategoryGasRepository;
    private final CategoryService categoryService;
    private final GasService gasService;
    private final AnalysisRepository analysisRepository;


    public AnalysisCategoryGasServiceImpl(AnalysisCategoryGasRepository analysisCategoryGasRepository, CategoryService categoryService, GasService gasService, AnalysisRepository analysisRepository) {
        this.analysisCategoryGasRepository = analysisCategoryGasRepository;
        this.categoryService = categoryService;
        this.gasService = gasService;
        this.analysisRepository = analysisRepository;
    }

    @Override
    public List<AnalysisCategoryGas> getAllAnalysisCategoryGas() {
        return analysisCategoryGasRepository.findAll();
    }

    @Override
    public AnalysisCategoryGas getAnalysisCategoryGasById(int id) {
        return analysisCategoryGasRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public AnalysisCategoryGas saveAnalysisCategoryGas(Analysis analysis, Category category, Gas gas) {
        if (analysis != null && category != null && gas != null) {
            analysis = analysisRepository.save(analysis);
            category = categoryService.saveCategory(category);
            gas = gasService.saveGas(gas);

            AnalysisCategoryGas analysisCategoryGas = new AnalysisCategoryGas();
            analysisCategoryGas.setAnalysis(analysis);
            analysisCategoryGas.setCategory(category);
            analysisCategoryGas.setGas(gas);
            return analysisCategoryGasRepository.save(analysisCategoryGas);
        }
        else throw new ResourceNotFoundException("Analysis, Category or Gas cant be null");
    }

    @Override
    public AnalysisCategoryGas editAnalysisCategoryGas(AnalysisCategoryGas analysisCategoryGas) {
        return analysisCategoryGasRepository.save(analysisCategoryGas);
    }

    @Override
    public void deleteAnalysisCategoryGas(int id) {
        analysisCategoryGasRepository.deleteById(id);
    }

    @Override
    public List<AnalysisCategoryGas> findByAnalysisAndCategory(Analysis analysis, Category category) {
        if (analysis != null && category != null)
            return analysisCategoryGasRepository.findByAnalysis_IdAndCategory_Id(analysis.getId(), category.getId());
        else throw new ResourceNotFoundException("Analysis and Category not founded");
    }

    @Override
    public List<AnalysisCategoryGas> findByGasAndCategory(Gas gas, Category category) {
        if (gas != null && category != null)
            return analysisCategoryGasRepository.findByGas_IdAndCategory_Id(gas.getId(), category.getId());
        else throw new ResourceNotFoundException("Gas and Category not founded");
    }

    @Override
    public AnalysisCategoryGas findByAnalysisCategoryAndGas(Analysis analysis, Category category, Gas gas) {
        if (analysis != null && gas != null && category != null)
            return analysisCategoryGasRepository.findByAnalysis_IdAndCategory_IdAndGas_Id(analysis.getId(), gas.getId(), category.getId());
        else throw new ResourceNotFoundException("Analyse, Gas and Category not founded");
    }

    @Override
    public AnalysisCategoryGas findByAnalysisCategoryAndGasName(Analysis analysis, Category category, Gas gas) {
        if (analysis != null && gas != null && category != null)
            return analysisCategoryGasRepository.findByAnalysis_IdAndCategory_IdAndGasName(analysis.getId(), gas.getId(), category.getName());
        else throw new ResourceNotFoundException("Analyse, Gas and Category not founded");    }

    @Override
    @Transactional
    public List<AnalysisCategoryGas> saveAllAnalysisCategoryGas(List<AnalysisCategoryGas> analysisCategoryGases) {
        return analysisCategoryGasRepository.saveAll(analysisCategoryGases);
    }

    @Override
    public List<AnalysisCategoryGas> findByGas_Name(String name) {
        return analysisCategoryGasRepository.findByGas_Name(name);
    }
}
