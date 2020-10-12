package mk.gov.moepp.emi.invertoryinfo.model.Requests;

import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

public class CreateAnalysisRequest {

    private String year;
    private String gas;
    private MultipartFile file;

    public CreateAnalysisRequest(String year, String gas, MultipartFile file) {
        this.year = year;
        this.gas = gas;
        this.file = file;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String  getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
