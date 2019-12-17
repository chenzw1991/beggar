package com.chenzhiwu.beggar.modules.system.domain;

import lombok.Data;

/**
 * Description :
 * Author : Chen.MeiJie
 * Create Time : 2019/7/18.
 */
@Data
public class TreeNode {
    private Long id;
    private String title;
    private Long pid;
    private Integer sort;
}
