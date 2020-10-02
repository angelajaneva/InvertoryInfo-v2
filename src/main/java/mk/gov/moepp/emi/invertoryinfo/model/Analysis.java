package mk.gov.moepp.emi.invertoryinfo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Year;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "analysis")
@Where(clause = "deleted=false")
public class Analysis {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private Year year; // ili int?

    @Column(name = "deleted")
    private boolean deleted = false;

    @ManyToMany(mappedBy = "category")
    private List<Category> categories;

    @ManyToMany(mappedBy = "gas")
    private List<Gas> gases;
}
