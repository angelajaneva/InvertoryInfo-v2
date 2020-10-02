package mk.gov.moepp.emi.invertoryinfo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gas {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    private double concentrate;

    @ManyToMany(mappedBy = "category")
    List<Category> categories;

    @ManyToMany
    List<Analysis> analyses;

}
