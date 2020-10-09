package mk.gov.moepp.emi.invertoryinfo.controller;

import mk.gov.moepp.emi.invertoryinfo.model.Category;
import mk.gov.moepp.emi.invertoryinfo.service.CategoryService;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/category", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/{id}")
    public Category getCategory(@PathVariable int id){
        return categoryService.getCategory(id);
    }

    @PostMapping(path = "/save")
    public Category saveCategory(Category category){
        return categoryService.saveCategory(category);
    }

    @PatchMapping(path = "/edit")
    public Category editCategory(Category category){
        return categoryService.editCategory(category);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
    }

    @GetMapping(path = "/byName")
    public Category getCategoryByName(String name){
        return categoryService.getCategoryByName(name);
    }

    @GetMapping(path = "byEnglishName")
    public Category getCategoryByEnglishName(String name){
        return categoryService.getCategoryByEnglishName(name);
    }

    @GetMapping(path = "byPrefix")
    public Category getByPrefix(String prefix){
        return categoryService.findByPrefix(prefix);
    }

}
