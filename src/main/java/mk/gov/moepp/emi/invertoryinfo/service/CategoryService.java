package mk.gov.moepp.emi.invertoryinfo.service;

import mk.gov.moepp.emi.invertoryinfo.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategory(int id);

    Category saveCategory(Category category);

    Category editCategory(Category category);

    void deleteCategory(int id);

}
