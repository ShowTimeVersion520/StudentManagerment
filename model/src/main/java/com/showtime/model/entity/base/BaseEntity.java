package com.showtime.model.entity.base;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <b><code>BaseEntity</code></b>
 * <p/>
 * BaseEntity
 * <p/>
 * <b>Creation Time:</b> 2017/05/17 21:18.
 *
 * @author lvxin
 * @version 1.0.0
 * @since model 1.0.0
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
