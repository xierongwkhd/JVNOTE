package moke.demo.ssm.dao;


import moke.demo.ssm.entity.UserContent;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */
public interface UserContentMapper extends Mapper<UserContent> {
    List<UserContent> findCategoryByUid(@Param("uid")long uid);
    /**
     *  插入文章并返回主键id 返回类型只是影响行数  id在UserContent对象中
     */
    int inserContent(UserContent userContent);

    /**
     * user_content与user连接查询
     */
    List<UserContent> findByJoin(UserContent userContent);

    List<UserContent> findByASC(UserContent userContent);

}
