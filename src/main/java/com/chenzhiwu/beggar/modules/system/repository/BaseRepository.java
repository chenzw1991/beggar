package com.chenzhiwu.beggar.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.chenzhiwu.beggar.common.constant.StatusConst;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T,ID> {

    /**
     * 查找ID且状态正常
     * @param id 主键ID
     * @param status 状态
     */
    public T findByIdAndStatus(Long id, Byte status);

    /**
     * 批量更新数据状态
     * #{#entityName} 实体类对象
     * @param status 状态
     * @param id ID列表
     * @return 更新数量
     */
    @Modifying
    @Transactional
    @Query("update #{#entityName} set status = ?1  where id in ?2 and status <> " + StatusConst.DELETE)
    public Integer updateStatus(Byte status, List<Long> id);
}
