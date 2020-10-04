package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.Requests.CreateAnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.model.enums.FileType;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalyseCategoryGasRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.AnalysisRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.CategoryRepository;
import mk.gov.moepp.emi.invertoryinfo.repository.GasRepository;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final CategoryRepository categoryRepository;
    private final GasRepository gasRepository;
    private final AnalyseCategoryGasRepository analyseCategoryGasRepository;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository, CategoryRepository categoryRepository, GasRepository gasRepository, AnalyseCategoryGasRepository analyseCategoryGasRepository) {
        this.analysisRepository = analysisRepository;
        this.categoryRepository = categoryRepository;
        this.gasRepository = gasRepository;
        this.analyseCategoryGasRepository = analyseCategoryGasRepository;
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
                                subcategory = categoryRepository.findByPrefixEquals(prefix);
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
         //           category.setGases(gases);
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

    @Override
    public Analysis saveFromFile(MultipartFile file) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            FileType fileType = FileType.GAS;
            Analysis analyse = new Analysis();
            String gasName;
            String type = null;
            int stringIndex = 1;

            while (sheetIterator.hasNext()){
                XSSFSheet xssfSheet = (XSSFSheet) sheetIterator.next();
                Iterator<Row> rowIterator = xssfSheet.rowIterator();

                List<String> list = new ArrayList<>();

                while (rowIterator.hasNext()){
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Category category = new Category();
                    while (cellIterator.hasNext()){
                        Cell cell = cellIterator.next();
                        //kakov tip na file doznavame
                        if (row.getRowNum() > 2 && cell.getCellType() == CellType._NONE || cell.getCellType() == CellType.BLANK){
                            break;
                        }
                        else if (row.getRowNum() == 0 && cell.getCellType() == CellType.STRING){
                            String text = cell.getStringCellValue();
                            stringIndex = cell.getColumnIndex() + 1;
                            if (text.toLowerCase().startsWith("inventory year")){
                                fileType = FileType.YEARLY;
                                analyse = getAnalyse(text);
                                if (analyse == null){
                                    throw new ResourceNotFoundException("Тhe file is not set properly");
                                }
                            }
                            else{
                                type = text;
                            }
                        }
                        else if (cell.getCellType() == CellType.STRING){
                            String text = cell.getStringCellValue().trim();
                            //gi dodavame kategoriite
                            if (row.getRowNum() == 2 && !text.toLowerCase().equals("categories")){
                                list.add(text);
                            }
                            else if (row.getRowNum() > 2){
                                category = setCategory(category, text, cell.getColumnIndex(), fileType);
                            }
                        }
                        else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA){
                            //tuka treba da prodolzam so za gas ili spored godina da se procitat podatocite
                            if (row.getRowNum() > 2) {
                                double concentrate = cell.getNumericCellValue();

                                createAnalyse(list, category, fileType, concentrate, analyse, type, cell.getColumnIndex(), stringIndex);


                            }else {
                                String year = String.valueOf(cell.getNumericCellValue());
                                year = year.substring(0, year.lastIndexOf("."));
                                list.add(year);
                            }
                        }


                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    Gas getGas(String type){
        Gas gas = gasRepository.findByNameEquals(type);
        if (gas != null){
            return gas;
        }
        gas = new Gas();
        gas.setName(type);
        return gas;
    }

    Analysis getAnalyse(String type){
        try {
            Year year = Year.parse(type);
            Analysis analysis = analysisRepository.findByYearEquals(year);
            if (analysis != null){
                return analysis;
            }
            Analysis newAnalyse = new Analysis();
            newAnalyse.setYear(year);
            return analysisRepository.save(newAnalyse);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    void createAnalyse(List<String> list, Category category, FileType fileType, double concentrate, Analysis analyse, String gasName, int index, int stringIndex){
        AnalysisCategoryGas analysisCategoryGas;
        String text = list.get(index - stringIndex);

        //najverojatno samo spored gas ce citame oti gi ima i na angliski i na makedonski iminjata
        if (fileType == FileType.YEARLY){
            //stringIndex ni e kolku koloni imame za iminjata za da go zemime tocniot gasso ni treba
            //nemozime spored gasName da prebaruvame oti sekako vekje postoe so toa ime ce mora da se kreira nov gas
            //ama problem ce bide so za 2 analizi ce treba 2 pati da cuvame za istiot gas
            Gas gas = gasRepository.findByNameEquals(text);

            if (gas == null){
                gas = new Gas();
                gas.setName(text);
                gas.setConcentrate(concentrate);
                gas = gasRepository.save(gas);
            }
            else gas.setConcentrate(concentrate);

            analysisCategoryGas = analyseCategoryGasRepository.findByAnalysis_IdAndCategory_IdAndGas_Id(analyse.getId(), category.getId(), gas.getId());

            analysisCategoryGas = new AnalysisCategoryGas(analyse, category, gas);
            analysisCategoryGas = analyseCategoryGasRepository.save(analysisCategoryGas);
        }
        else{
            analyse = getAnalyse(text);
            List<AnalysisCategoryGas> gases = analyseCategoryGasRepository.findByAnalysis_IdAndCategory_Id(analyse.getId(), category.getId());
            Gas gas = null;
            if (gases != null && gases.size() != 0) {
                for (AnalysisCategoryGas analysisCategoryGas1 : gases) {
                    if (analysisCategoryGas1.getGas().getName().equals(gasName)) {
                        gas = analysisCategoryGas1.getGas();
                        gas.setConcentrate(concentrate);
                        gas = gasRepository.save(gas);
                        break;
                    }
                }
            }
            if (gas == null){
                gas = new Gas();
                gas.setName(gasName);
                gas.setConcentrate(concentrate);
                gasRepository.save(gas);
                analysisCategoryGas = new AnalysisCategoryGas(analyse,category,gas);
                analyseCategoryGasRepository.save(analysisCategoryGas);
            }

        }
    }

    Category setCategory(Category category, String text, int columnIndex, FileType fileType){
        Category newCategory = null;
        //Ako e na makedonski da bara spored name ako ne spored EnglishName
        if (columnIndex == 0 && fileType == FileType.GAS){
            category.setName(text);
            newCategory = categoryRepository.findByNameEquals(text);
        }else{
            category.setEnglishName(text);
            newCategory = categoryRepository.findByEnglishNameEquals(text);
        }
        //ako postoe vekje takva kategorija da a vratime
        if (newCategory != null){
            return newCategory;
        }
        //ako ne postoe da kreirame nova
        if (text.contains("-")){
            String prefix = text.substring(0, text.indexOf("-"));
            if (prefix.contains(".")){
                prefix = prefix.substring(0, prefix.lastIndexOf("."));
            }
            category.setPrefix(prefix);
            Category subcategory = categoryRepository.findByPrefixEquals(prefix);
            category.setSubcategory(subcategory);
        }

        return categoryRepository.save(category);
    }
}
