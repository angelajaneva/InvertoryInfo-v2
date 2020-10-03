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

//    @ManyToMany(mappedBy = "category")
//    private List<Category> categories;
//
//    @ManyToMany(mappedBy = "gas")
//    private List<Gas> gases;

    @OneToMany(mappedBy = "analysis")
    private List<AnalysisCategoryGas> categoryGases;

    //Getters and Setters (ne rabote lombok)

    public int getId() {
        return id;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
