package mk.gov.moepp.emi.invertoryinfo.controller;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisCategoryGasService;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)

public class AnalysisCategoryGasController {

    private final AnalysisCategoryGasService analysisCategoryGasService;

    public AnalysisCategoryGasController(AnalysisCategoryGasService analysisCategoryGasService) {
        this.analysisCategoryGasService = analysisCategoryGasService;
    }

    @GetMapping(path = "/all")
    public List<AnalysisCategoryGas> getAll (){
        return analysisCategoryGasService.getAllAnalysisCategoryGas();
    }

    @GetMapping(path = "/{id}")
    public AnalysisCategoryGas getById(@PathVariable int id){
        return analysisCategoryGasService.getAnalysisCategoryGasById(id);
    }

    @PostMapping(path = "/save")
    public AnalysisCategoryGas save(Analysis analysis, Category category, Gas gas){
        //voa ako se upotrebuva moze so DTO da se naprave
        // samo da ni pokaze treba koga kje se najave kako kje vmetnuva
        return analysisCategoryGasService.saveAnalysisCategoryGas(analysis, category, gas);
    }

    @PatchMapping(path = "/edit")
    public AnalysisCategoryGas editAnalysisCategoryGas(AnalysisCategoryGas analysisCategoryGas){
        //isto i voa treba da ni kaze dali i kje treba i tuka def DTO kje treba
        return analysisCategoryGasService.editAnalysisCategoryGas(analysisCategoryGas);
    }

    @DeleteMapping(path = "/delete")
    public void delete (int id){
        analysisCategoryGasService.deleteAnalysisCategoryGas(id);
    }
    @GetMapping(path = "/ByAnalysisAndCategory")
    public List<AnalysisCategoryGas> getByAnalysisAndCategory(Analysis analysis, Category category){
        return analysisCategoryGasService.findByAnalysisAndCategory(analysis, category);
    }

    @GetMapping(path = "/ByGasAndCategory")
    public List<AnalysisCategoryGas> getByGasAndCategory(Gas gas, Category category){
        return analysisCategoryGasService.findByGasAndCategory(gas, category);
    }

    @GetMapping(path = "/ByAnalysisCategoryAndGas")
    public AnalysisCategoryGas getByAnalyseCategoryAndGas(Analysis analysis, Category category, Gas gas){
        return analysisCategoryGasService.findByAnalysisCategoryAndGas(analysis, category, gas);
    }

    @GetMapping(path = "/ByAnalysisCategoryAndGasName")
    public AnalysisCategoryGas getByAnalysisCategoryAndGasName(Analysis analysis, Category category, Gas gas){
        return analysisCategoryGasService.findByAnalysisCategoryAndGasName(analysis, category, gas);
    }

    @PostMapping(path = "/saveAll")
    List<AnalysisCategoryGas> saveAllAnalysisCategoryGas(List<AnalysisCategoryGas> analysisCategoryGases){
        return analysisCategoryGasService.saveAllAnalysisCategoryGas(analysisCategoryGases);
    }

    @GetMapping(path = "ByGasName")
    List<AnalysisCategoryGas> getByGas_Name(String name){
        return analysisCategoryGasService.findByGas_Name(name);
    }


}
