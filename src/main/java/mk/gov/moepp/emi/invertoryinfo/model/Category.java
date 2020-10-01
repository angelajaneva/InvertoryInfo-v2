package mk.gov.moepp.emi.invertoryinfo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany
    private List<Analysis> analyses;

    @ManyToMany
    private List<Gas> gases;
}
