package moke.demo.ssm.service;

import moke.demo.ssm.common.PageHelper;
import moke.demo.ssm.entity.UserContent;

/**
 * Created by MOKE on 2019/2/19.
 */
public interface SolrService {
    /**
     * 根据关键字搜索文章并分页
     */
    PageHelper.Page<UserContent> findByKeyWords(String keyword, Integer pageNum, Integer pageSize);
    /**
     * 添加文章到solr索引库中
     */
    void addUserContent(UserContent userContent);

    /**
     * 根据solr索引库
     */
    void updateUserContent(UserContent userContent);

    /**
     * 根据文章id删除索引库
     */
    void deleteById(Long id);
}
