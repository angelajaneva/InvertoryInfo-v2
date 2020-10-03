package mk.gov.moepp.emi.invertoryinfo.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Entity
public class AnalysisCategoryGas {

    @Embeddable
    public static class AnalysisCategoryGasId implements Serializable {

        @Column(name = "fk_analysis")
        protected int analysisId;

        @Column(name = "fk_category")
        protected int categoryId;

        @Column(name = "fk_gas")
        protected int gasId;


        public AnalysisCategoryGasId() {
        }

        public AnalysisCategoryGasId(int analysisId, int categoryId, int gasId) {
            this.analysisId = analysisId;
            this.categoryId = categoryId;
            this.gasId = gasId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AnalysisCategoryGasId)) return false;
            AnalysisCategoryGasId that = (AnalysisCategoryGasId) o;
            return analysisId == that.analysisId &&
                    categoryId == that.categoryId &&
                    gasId == that.gasId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(analysisId, categoryId, gasId);
        }
    }

    @EmbeddedId
    private AnalysisCategoryGasId id;

    @ManyToOne
    @JoinColumn(name = "fk_analysis", insertable = false, updatable = false)
    private Analysis analysis;

    @ManyToOne
    @JoinColumn(name = "fk_category", insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "fk_gas", insertable = false, updatable = false)
    private Gas gas;

    public AnalysisCategoryGas(Analysis analysis, Category category, Gas gas) {

        this.id = new AnalysisCategoryGasId(analysis.getId(), category.getId(), gas.getId());

        this.analysis = analysis;
        this.category = category;
        this.gas = gas;

        // update relationships to assure referential integrity
//        p.getBooks().add(this);
//        b.getPublishers().add(this);

    }
}
