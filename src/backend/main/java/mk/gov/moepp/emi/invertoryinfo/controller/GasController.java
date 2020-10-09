package mk.gov.moepp.emi.invertoryinfo.controller;

import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/gas", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class GasController {

    private final GasService gasService;

    public GasController(GasService gasService) {
        this.gasService = gasService;
    }

    @GetMapping
    public List<Gas> getAllGasses(){
        return gasService.getAllGasses();
    }

    @GetMapping(path = "/{id}")
    public Gas getGas(@PathVariable int id){
        return gasService.getGas(id);
    }

    @PostMapping("/save")
    public Gas saveGas(Gas gas){
        return gasService.saveGas(gas);
    }

    @PatchMapping("/edit")
    public Gas editGas(Gas gas){
        return gasService.editGas(gas);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteGas(@PathVariable int id){
        gasService.deleteGas(id);
    }

}
