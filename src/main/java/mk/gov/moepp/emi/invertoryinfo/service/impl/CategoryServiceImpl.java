package mk.gov.moepp.emi.invertoryinfo.service.impl;

import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.repository.CategoryRepository;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(int id) {
        return categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category editCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByNameEquals(name);
    }

    @Override
    public Category getCategoryByEnglishName(String name) {
        return categoryRepository.findByEnglishNameEquals(name);
    }

    @Override
    public Category findByPrefix(String prefix) {
        return categoryRepository.findByPrefixEquals(prefix);
    }
}
