package mk.gov.moepp.emi.invertoryinfo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Year;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Analysis {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private Year year; // ili int?

    @ManyToMany(mappedBy = "category")
    private List<Category> categories;

    @ManyToMany(mappedBy = "gas")
    private List<Gas> gases;
}
