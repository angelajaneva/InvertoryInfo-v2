package mk.gov.moepp.emi.invertoryinfo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Gas {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String name;
    private double concentrate;

    @OneToMany(mappedBy = "gas", cascade = CascadeType.ALL)
    @JsonIgnore
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
