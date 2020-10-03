package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.Requests.CreateAnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.CategoryRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final CategoryRepository categoryRepository;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository, CategoryRepository categoryRepository) {
        this.analysisRepository = analysisRepository;
        this.categoryRepository = categoryRepository;
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
        MultipartFile file = request.getFile();
        //Prvicno test zimanje na podatoci treba da se naprave ubavo
        try {
            //Citame od excel
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(8);

            Iterator<Row> rowIterator = worksheet.iterator();
            List<String> gasesName = new ArrayList<>();
            List<Category> categories = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                //Creating Category
                Category category = new Category();
                List<Gas> gases = new ArrayList<>();
                //Iterating cells
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == CellType.STRING){
                        String text = cell.getStringCellValue().trim();
                        //gi zimame site iminja na gasovi
                        if (row.getRowNum() == 2 && !text.toLowerCase().equals("categories")){
                            gasesName.add(text);
                        }else if (row.getRowNum() > 2){
                            //otkako ce gi pominime iminjata na site gasovi stavame ime na category
                            category.setEnglishName(text);

                            String prefix;
                            Category subcategory = null;
                            if (text.contains("-")){
                                prefix = text.substring(0, text.indexOf('-')).trim();
                                category.setPrefix(prefix);

                                if (prefix.contains(".")){
                                    prefix = prefix.substring(0, prefix.lastIndexOf("."));
                                }
                                subcategory = categoryRepository.findAllByPrefixEquals(prefix);
                                category.setSubcategory(subcategory);
                            }
                        }
                    }
                    else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA){
                        //System.out.println(cell.getNumericCellValue());
                        double concentrate = cell.getNumericCellValue();
                        Gas gas = new Gas();
                        gas.setName(gasesName.get(cell.getColumnIndex()-1));
                        gas.setConcentrate(concentrate);
                        gases.add(gas);
                    }
                    else if (cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.BLANK)
                    {
                        break;
                    }

                }
                if(!gases.isEmpty() || (category.getName() != null || category.getEnglishName() != null)){
                    category.setGases(gases);
                    categoryRepository.save(category);
//                    categories.add(category);
                }
            }

//            categoryRepository.saveAll(categories);

        }
        catch (IllegalStateException | IOException e){
            e.printStackTrace();
        }

        return new Analysis();
    }
}
