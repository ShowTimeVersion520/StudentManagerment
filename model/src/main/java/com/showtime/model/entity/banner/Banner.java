package com.showtime.model.entity.banner;

import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_banner")
@DynamicInsert
@DynamicUpdate
public class Banner extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Banner(){
        super();
    }

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "redirect_url")
    private String redirectUrl;

    @Column(name = "sequence")
    private Integer sequence;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", sequence=" + sequence +
                '}';
    }
}
