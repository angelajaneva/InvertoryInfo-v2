package mk.gov.moepp.emi.invertoryinfo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "analysis_category_gas", indexes = {
        @Index(name = "IX_Analysis_Category", columnList = "analysis_id,category_id"),
        @Index(name = "IX_Gasses_Category", columnList = "gas_id,category_id")
})
public class AnalysisCategoryGas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
//    @JoinColumn(name = "fk_analysis", insertable = false, updatable = false)
    @JsonManagedReference
    private Analysis analysis;

    @ManyToOne
//    @JoinColumn(name = "fk_category", insertable = false, updatable = false)
    @JsonManagedReference
    private Category category;

    @ManyToOne
//    @JoinColumn(name = "fk_gas", insertable = false, updatable = false)
    @JsonManagedReference
    private Gas gas;

    public int getId(){
        return id;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }
}
