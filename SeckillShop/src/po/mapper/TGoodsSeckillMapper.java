package po.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import po.pojo.TGoodsSeckill;
import po.pojo.TGoodsSeckillExample;

public interface TGoodsSeckillMapper {
    long countByExample(TGoodsSeckillExample example);

    int deleteByExample(TGoodsSeckillExample example);

    int insert(TGoodsSeckill record);

    int insertSelective(TGoodsSeckill record);

    List<TGoodsSeckill> selectByExample(TGoodsSeckillExample example);

    int updateByExampleSelective(@Param("record") TGoodsSeckill record, @Param("example") TGoodsSeckillExample example);

    int updateByExample(@Param("record") TGoodsSeckill record, @Param("example") TGoodsSeckillExample example);
}