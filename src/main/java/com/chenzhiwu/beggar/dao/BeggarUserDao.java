package com.chenzhiwu.beggar.dao;

import com.chenzhiwu.beggar.pojo.BeggarUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:IGG
 * @date:2019/12/17-12 : 19
 */
public interface BeggarUserDao extends JpaRepository<BeggarUser, Long> {
    public BeggarUser getUserById(Long id);
}
