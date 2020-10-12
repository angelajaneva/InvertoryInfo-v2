package mk.gov.moepp.emi.invertoryinfo.model;


import lombok.*;
import mk.gov.moepp.emi.invertoryinfo.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Gas implements ValueObject {


//    @GeneratedValue(strategy= GenerationType.AUTO)
//    @Column(name = "gas_id")
//    private int id;
//
    private String name;
    private double concentrate;

    public Gas(String name, double concentrate) {
        this.name = name;
        this.concentrate = concentrate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gas)) return false;
        Gas gas = (Gas) o;
        return Double.compare(gas.concentrate, concentrate) == 0 &&
                name.equals(gas.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, concentrate);
    }
}
