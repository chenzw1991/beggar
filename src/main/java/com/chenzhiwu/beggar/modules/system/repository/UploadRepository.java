package com.chenzhiwu.beggar.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chenzhiwu.beggar.modules.system.domain.Upload;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
public interface UploadRepository extends JpaRepository<Upload, Long> {

    /**
     * 查找指定文件sha1记录
     * @param sha1 文件sha1值
     */
    public Upload findBySha1(String sha1);
}

