package com.showtime.dao.sc;


import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.showtime.dao.base.BatchDao;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public class ScDaoImpl implements BatchDao {

    @PersistenceContext
    protected EntityManager em;

    @Transactional
    public void batchInsert(List list) {
        for(int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
            if(i % 30== 0) {
                em.flush();
                em.clear();
            }
        }
    }

    @Transactional
    public void batchUpdate(List list) {
        for(int i = 0; i < list.size(); i++) {
            em.merge(list.get(i));
            if(i % 30== 0) {
                em.flush();
                em.clear();
            }
        }
    }
}