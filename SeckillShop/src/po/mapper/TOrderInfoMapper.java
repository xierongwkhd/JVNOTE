package po.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import po.pojo.TOrderInfo;
import po.pojo.TOrderInfoExample;

public interface TOrderInfoMapper {
    long countByExample(TOrderInfoExample example);

    int deleteByExample(TOrderInfoExample example);

    int insert(TOrderInfo record);

    int insertSelective(TOrderInfo record);

    List<TOrderInfo> selectByExample(TOrderInfoExample example);

    int updateByExampleSelective(@Param("record") TOrderInfo record, @Param("example") TOrderInfoExample example);

    int updateByExample(@Param("record") TOrderInfo record, @Param("example") TOrderInfoExample example);
}