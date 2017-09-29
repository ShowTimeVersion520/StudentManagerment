package com.showtime.model.view.banner;

import com.showtime.model.entity.banner.Banner;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel
public class BannerView extends Banner implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
