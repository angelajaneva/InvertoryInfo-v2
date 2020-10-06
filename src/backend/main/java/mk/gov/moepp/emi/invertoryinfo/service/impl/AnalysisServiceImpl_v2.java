package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.Requests.CreateAnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.model.enums.FileType;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisCategoryGasService;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AnalysisServiceImpl_v2 implements AnalysisService{
    private final AnalysisRepository analysisRepository;
    private final CategoryService categoryService;
    private final GasService gasService;
    private final AnalysisCategoryGasService analysisCategoryGasService;

    public AnalysisServiceImpl_v2(AnalysisRepository analysisRepository, CategoryService categoryService, GasService gasService, AnalysisCategoryGasService analysisCategoryGasService) {
        this.analysisRepository = analysisRepository;
        this.categoryService = categoryService;
        this.gasService = gasService;
        this.analysisCategoryGasService = analysisCategoryGasService;
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
    public Analysis saveFromFile(CreateAnalysisRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void saveFromFile(MultipartFile file) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++){

                XSSFSheet xssfSheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = xssfSheet.rowIterator();
                //vo zavisnost od tipot na analizata cuvame analiza ili gas
                Analysis analysis = null;
                Gas gas = null;
                FileType fileType = FileType.GAS;

                //doznavame na koj red ni se kategoriite
                int categoriesRowNum = Integer.MAX_VALUE;

                //cuvam godina ili gas
                List<String> list = new ArrayList<>();
                List<AnalysisCategoryGas> analysisCategoryGases = new ArrayList<>();

                while (rowIterator.hasNext()){
                    Row row = rowIterator.next();

                    Category category = new Category();

                    boolean skipRow = false;
                    int rowNum = row.getRowNum();

                    int whichCategoryName = 0;
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()){
                        Cell cell = cellIterator.next();
                        if (cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.BLANK){
                            break;
                        }
                        //doznavame koj tip e
                        if (!isNumber(cell) && categoriesRowNum == Integer.MAX_VALUE){
                            //momentalno i godinata ni e vo string verzija (za sekoj slucaj proverka)
                            String text = cell.getStringCellValue().trim();

                            if (rowNum == 0){
                                if (text.toLowerCase().startsWith("inventory year:")){
                                    fileType = FileType.YEARLY;
                                    String[] parts = text.split("\\s+");
                                    String year = parts[parts.length-1].trim();
                                //    Year year = getYearFromString(strYear);
                                    analysis = getAnalysis(year);
                                } else if (!emptyString(text)){
                                    fileType = FileType.GAS;
                                    gas = new Gas();
                                    gas.setName(text);
                                }
                            } else if (text.toLowerCase().equals("categories")){
                                //go nagoduvame deka tuka ni se kategoriite
                                categoriesRowNum = rowNum;
                            }
                        }
                        else if (categoriesRowNum == rowNum){
                            String text = null;
                            if (isNumber(cell)) {
                                double strYear = cell.getNumericCellValue();
                                Year year = getYearFromString(String.valueOf(strYear));
                                text = year.toString();
                            }else {
                                text = cell.getStringCellValue();
                            }
                           list.add(text);
                        }
                        else if(rowNum > categoriesRowNum && list.size() > 0){
                            //moze da pocnime da gi citame kategoriite
                            //proveruvame dali e kategorija ili e concentrate
                            if (cell.getCellType() == CellType.STRING){
                                //znaci e kategorija
                                whichCategoryName = cell.getColumnIndex() + 1;
                                String text = cell.getStringCellValue().trim();
                                if (!emptyString(text)) {
                                    category = getCategory(category, text, cell.getColumnIndex(), fileType);
                                }
                            } else if (isNumber(cell)){
                                double concentrate = cell.getNumericCellValue();
                                if (fileType == FileType.GAS){
                                    //a barame analizata za dadenata godina
                                    String year = list.get(cell.getColumnIndex() - whichCategoryName);
                                    analysis = getAnalysis(year);
                                    //kreirame nov Gas za dadenata vrska
                                    //dokolku pogore ispadnalo greska i gas ni e null togas error
                                    if (gas == null){
                                        throw new ResourceNotFoundException("Gas not found");
                                    }
                                    //go cuvame name vo promenliva
                                    String name = gas.getName();
                                    //kreirame nov gas za da imame novo id
                                    gas = new Gas();
                                    gas.setName(name);
                                    gas.setConcentrate(concentrate);
                                    if (category != null) {
                                        //gasService.saveGas(gas);
                                        analysisCategoryGases.add(createAnalysisCategoryGas(analysis, category, gas, fileType));
                                    }
                                }
                                else {
                                    Gas newGas = new Gas();
                                    newGas.setName(list.get(cell.getColumnIndex() - whichCategoryName));
                                    newGas.setConcentrate(concentrate);

                                    if (category != null) {
                                        //gasService.saveGas(gas);
                                        analysisCategoryGases.add(createAnalysisCategoryGas(analysis, category, gas, fileType));
                                    }
                                }
                            } else if (cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.BLANK){
                                skipRow = true;
                            }
                        }
                    }
                    //dokolku imame prazen string da skipnime
                    if (skipRow){
                        break;
                    }
                }

                analysisCategoryGases = analysisCategoryGasService.saveAllAnalysisCategoryGas(analysisCategoryGases);

            }
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    AnalysisCategoryGas createAnalysisCategoryGas(Analysis analysis, Category category, Gas gas, FileType fileType){
        List<AnalysisCategoryGas> gasses;

        categoryService.saveCategory(category);

        if (fileType == FileType.YEARLY) {
            gasses = analysisCategoryGasService.findByAnalysisAndCategory(analysis, category);
        } else {
            gasses = analysisCategoryGasService.findByGasAndCategory(gas, category);
        }

        for (AnalysisCategoryGas temp : gasses) {
            if (temp.getGas().getName().equals(gas.getName())){
                double concentrate = gas.getConcentrate();
                gas = temp.getGas();
                gas.setConcentrate(concentrate);
                break;
            }
        }
        gasService.saveGas(gas);

        AnalysisCategoryGas analysisCategoryGas = new AnalysisCategoryGas();
        analysisCategoryGas.setAnalysis(analysis);
        analysisCategoryGas.setCategory(category);
        analysisCategoryGas.setGas(gas);

        return analysisCategoryGas;
    }


    Category getCategory(Category category, String text, int columnIndex, FileType fileType){
        // dokolku ima - znaci ima nekoj prefix
        if (text.contains("-")){
            String prefix = text.substring(0, text.indexOf("-")).trim();
            category.setPrefix(prefix);
            //prebaruvame dali imame vekje nekoja kategorija so toj prefix i dokolku go nema angliskoto ili makedonskoto ime da se stavi
            Category oldCategory = categoryService.findByPrefix(category.getPrefix());
            if (oldCategory != null){
                category = oldCategory;
            }
            //mestime subcategory
            if (prefix.contains(".")){
                prefix = prefix.substring(0, prefix.lastIndexOf("."));
                Category subcategory = categoryService.findByPrefix(prefix);
                if (subcategory != null) {
                    category.setSubcategory(subcategory);
                }
            }

        }
        if (fileType == FileType.GAS){
            //vo gas prvata kolona ni e makedonski vtorata na angliski
            if (columnIndex == 0){
                category.setName(text);
            }else {
                category.setEnglishName(text);
            }
        } else if (columnIndex == 0){
            // ako e YEARLY prvata kolona ni e angliski imeto
            category.setEnglishName(text);
        }

        return category;
    }

    Analysis getAnalysis(String year){
        Analysis analysis = analysisRepository.findByYearEquals(year);
        if (analysis != null){
            return analysis;
        }
        analysis = new Analysis();
        analysis.setYear(year);

        return analysisRepository.save(analysis);
    }

    Year getYearFromString(String strYear){
        //dokolku a zeme double vrednost ima 1990.00 a da parsiram vo integer mi dava error
        if (strYear.contains(".")){
            strYear = strYear.substring(0, strYear.indexOf("."));
        }
        return Year.parse(strYear);
    }

    boolean isNumber(Cell cell){
        return cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA;
    }

    boolean emptyString(String text){
       return text.isEmpty() || text.isBlank() || text.equals("");
    }
}
