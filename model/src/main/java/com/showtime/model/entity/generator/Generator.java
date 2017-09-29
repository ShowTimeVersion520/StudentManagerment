package com.showtime.model.entity.generator;

import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_generator")
@DynamicInsert
@DynamicUpdate
public class Generator extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Generator(){
        super();
    }

    @Column(name = "name")
    private String name;

    @Column(name = "number_suffix")
    private String numberSuffix;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberSuffix() {
        return numberSuffix;
    }

    public void setNumberSuffix(String numberSuffix) {
        this.numberSuffix = numberSuffix;
    }
}
