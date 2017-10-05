package com.showtime.model.entity.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b><code>Scholarship</code></b>
 * <p/>
 * Scholarship的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Oct 05 16:34:21 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@Entity
@Table(name="t_scholarship")
@DynamicInsert
@DynamicUpdate
public class Scholarship extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Scholarship(){
        super();
    }
    //奖学金等级
    @Column(name = "scholarship_level")
    private Integer scholarshipLevel;
    //奖学金数目
    @Column(name = "money")
    private BigDecimal money;

    public Integer getScholarshipLevel() {
        return scholarshipLevel;
    }

    public void setScholarshipLevel(Integer scholarshipLevel) {
        this.scholarshipLevel = scholarshipLevel;
    }
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

}
