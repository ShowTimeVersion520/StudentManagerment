package com.showtime.dao.generator;


import com.showtime.model.entity.generator.Generator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratorDao extends JpaRepository<Generator, Long>, JpaSpecificationExecutor<Generator> {

    /**
     * 根据编码名字获得后缀数字
     * @param name
     * @return
     */
    public Generator getByName(String name);

    /**
     * 清理Generator表
     * @param s
     */
    @Modifying
    @Query("UPDATE Generator g set g.numberSuffix = ?1")
    void clearGenerator(String s);
}
