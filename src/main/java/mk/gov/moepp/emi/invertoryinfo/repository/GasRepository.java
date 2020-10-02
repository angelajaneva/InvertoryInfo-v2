package mk.gov.moepp.emi.invertoryinfo.repository;

import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GasRepository extends JpaRepository<Gas,Integer> {
}
