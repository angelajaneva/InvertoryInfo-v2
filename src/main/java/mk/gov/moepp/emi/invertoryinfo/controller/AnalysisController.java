package mk.gov.moepp.emi.invertoryinfo.controller;


import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.Requests.CreateAnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import mk.gov.moepp.emi.invertoryinfo.service.impl.AnalysisServiceImpl_v2;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/analysis", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class AnalysisController {

    private final AnalysisServiceImpl_v2 analysisService;

    public AnalysisController(AnalysisServiceImpl_v2 analysisService){
        this.analysisService = analysisService;
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public Analysis singleFileUpload(@RequestParam("file") MultipartFile file) throws FileNotFoundException {

        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            throw new FileNotFoundException("File not found");
        }

        return analysisService.saveFromFile(file);
    }

    @PostMapping("/create") // //new annotation since 4.3
    public Analysis CreateAnalyse(@RequestBody CreateAnalysisRequest request) throws FileNotFoundException {

        if (request.getFile().isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            throw new FileNotFoundException("File not found");
        }

        return analysisService.saveFromFile(request);
    }
}
