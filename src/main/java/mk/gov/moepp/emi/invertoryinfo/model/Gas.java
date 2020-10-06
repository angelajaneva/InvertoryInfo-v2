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

    @OneToMany(
            mappedBy = "gas",
            cascade = CascadeType.PERSIST)
    private List<AnalysisCategoryGas> analysisCategory;
    //Getters and Setters (nesto ne rabote lombok)

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getConcentrate() {
        return concentrate;
    }

    public void setConcentrate(double concentrate) {
        this.concentrate = concentrate;
    }


}
