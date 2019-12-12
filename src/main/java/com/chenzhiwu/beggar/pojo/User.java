package com.chenzhiwu.beggar.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * @author:IGG
 * @date:2019/12/12-11 : 33
 */
@Entity(name = "user")
@Table(name = "user")
public class User {
    @Id //主键策略，递增
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "salt")
    private String salt;

    @Column(name = "head")
    private String head;

    @Column(name = "register_date")
    private Date registerDate;

    @Column(name = "last_login_time")
    private Date lastLoginDate;

    @Column(name = "login_count")
    private Integer loginCount;

    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getHead() {
        return head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public Date getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    public Date getLastLoginDate() {
        return lastLoginDate;
    }
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    public Integer getLoginCount() {
        return loginCount;
    }
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

}
