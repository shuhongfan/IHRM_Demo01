package com.shf.system.dao;

import com.shf.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

    public User findByMobile(String mobile);
}
