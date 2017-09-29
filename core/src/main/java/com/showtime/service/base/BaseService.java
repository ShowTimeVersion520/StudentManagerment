package com.showtime.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BaseService<T> {

    /**
     * 保存 entity.
     *
     * @param entity the entity
     * @return the string
     */
    public String saveEntity(T entity);

    /**
     * 删除 entity.
     *
     * @param id the id
     */
    public void deleteEntity(long id);

    /**
     * 批量删除 entities.
     *
     * @param ids the ids
     * @return int int
     */
    public void deleteEntities(String ids);

    /**
     * 更新 entity.
     *
     * @param entity the entity
     */
    public void updateEntity(T entity);

    /**
     * 获取 entity.
     *
     * @param id the id
     * @return entity the entity
     */
    public T getEntity(long id);

    /**
     * 分页获取
     *
     * @param currentPage the current page
     * @param pageSize    the page size
     * @return Page<T>   entities
     */
    public Page<T> getEntities(int currentPage,
                               int pageSize);

    /**
     * 按条件分页获取
     *
     * @param currentPage the current page
     * @param pageSize    the page size
     * @return Page<T>   entities
     */
    public Page<T> getEntitiesByParms(T t, int currentPage,
                                     int pageSize);

    /**
     * 总记录数
     *
     * @return int entities count
     */
    public long getEntitiesCount();

    /**
     * 查找所有记录数，数据量大谨慎使用
     *
     * @return the list
     */
    public List<T> findAll();

}

