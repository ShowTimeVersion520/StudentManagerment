package com.showtime.model.entity.change;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>Change</code></b>
 * <p/>
 * Change的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 20:01:08 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@Entity
@Table(name="t_change")
@DynamicInsert
@DynamicUpdate
public class Change extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Change(){
        super();
    }
    //变动
    @Column(name = "change_name")
    private String changeName;
    //0-未变动 1-已变动
    @Column(name = "is_change")
    private String isChange;

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }
    public String getIsChange() {
        return isChange;
    }

    public void setIsChange(String isChange) {
        this.isChange = isChange;
    }

}
