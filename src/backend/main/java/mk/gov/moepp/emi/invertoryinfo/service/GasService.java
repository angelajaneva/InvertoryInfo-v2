package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;

import java.util.List;

public interface GasService {

    List<Gas> getAllGasses();

    Gas getGas(int id);

    Gas saveGas(Gas gas);

    Gas editGas(Gas gas);

    void deleteGas(int id);

}
