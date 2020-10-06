package mk.gov.moepp.emi.invertoryinfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String name;

    //privremeno englishName
    @Column(name = "en_name")
    private String englishName;

    private String prefix;

    //subcategory?
    @ManyToOne
    @JoinColumn(name = "subcategory", referencedColumnName = "Id")
    @JsonManagedReference
    private Category subcategory;

    @OneToMany(mappedBy = "subcategory",  cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Category> children = new HashSet<>();

//    @ManyToMany
//    private List<Analysis> analyses;
//
//    @ManyToMany
//    private List<Gas> gases;

    //mapped by???
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AnalysisCategoryGas> analysisGases;

    //Getters and Setters (ne rabote lombok)

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Category getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Category subcategory) {
        this.subcategory = subcategory;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }
}
