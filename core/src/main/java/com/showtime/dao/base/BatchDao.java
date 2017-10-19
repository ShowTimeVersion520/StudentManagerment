package com.showtime.dao.base;



import java.util.List;


public interface BatchDao {
    public void batchInsert(List list);

    public void batchUpdate(List list);
}