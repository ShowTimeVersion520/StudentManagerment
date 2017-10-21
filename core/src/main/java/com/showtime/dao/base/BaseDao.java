package com.showtime.dao.base;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface BaseDao<T> {
    Page<T> getByMultiCondition(String sortType, String sortDirection,Pageable pageable, T entity, Object condition);
}
