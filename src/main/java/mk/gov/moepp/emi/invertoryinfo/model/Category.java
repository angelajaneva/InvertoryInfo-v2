package mk.gov.moepp.emi.invertoryinfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private int prefix;

    //subcategory?
    @ManyToOne
    @JoinColumn(name = "subcategory", referencedColumnName = "id")
    @JsonManagedReference
    private Category subcategory;

    @OneToMany(mappedBy = "subcategory",  cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Category> children = new HashSet<>();

    @ManyToMany
    private List<Analysis> analyses;

    @ManyToMany
    private List<Gas> gases;
}
