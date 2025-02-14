package models;

import java.math.BigDecimal;

public class Contacts {
    private BigDecimal number;
    private String name;

    Contacts (BigDecimal number , String name) {
        this.number = number;
        this.name = name;
    }

    public BigDecimal getNumber() {
        return number;
    }
    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
