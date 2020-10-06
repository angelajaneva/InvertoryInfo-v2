package mk.gov.moepp.emi.invertoryinfo.model.Requests;

import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

public class CreateAnalysisRequest {

    private Year year;
    private String gas;
    private MultipartFile file;

    public CreateAnalysisRequest(Year year, String gas, MultipartFile file) {
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

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
