package mk.gov.moepp.emi.invertoryinfo.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Entity
public class AnalysisCategoryGas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_analysis")
    private Analysis analysis;

    @ManyToOne
    @JoinColumn(name = "fk_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "fk_gas")
    private Gas gas;

    public AnalysisCategoryGas(Analysis analysis, Category category, Gas gas) {

//        this.id = new AnalysisCategoryGasId(analysis.getId(), category.getId(), gas.getId());

        this.analysis = analysis;
        this.category = category;
        this.gas = gas;

        // update relationships to assure referential integrity
//        p.getBooks().add(this);
//        b.getPublishers().add(this);

    }

//    public AnalysisCategoryGasId getId() {
//        return id;
//    }
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
