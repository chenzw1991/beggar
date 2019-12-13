package com.chenzhiwu.beggar.dao;

import com.chenzhiwu.beggar.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:IGG
 * @date:2019/12/12-17 : 09
 */
public interface UserDao extends JpaRepository<User, Long> {
    public User getUserById(Long id);
}
