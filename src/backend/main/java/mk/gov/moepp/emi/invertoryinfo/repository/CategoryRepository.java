package mk.gov.moepp.emi.invertoryinfo.repository;

import mk.gov.moepp.emi.invertoryinfo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Category findByPrefixEquals(String prefix);

    Category findByNameEquals(String name);

    Category findByEnglishNameEquals(String englishName);

}
