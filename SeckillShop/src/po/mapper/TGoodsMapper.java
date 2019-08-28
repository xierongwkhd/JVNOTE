package po.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import po.pojo.TGoods;
import po.pojo.TGoodsExample;

public interface TGoodsMapper {
    long countByExample(TGoodsExample example);

    int deleteByExample(TGoodsExample example);

    int insert(TGoods record);

    int insertSelective(TGoods record);

    List<TGoods> selectByExampleWithBLOBs(TGoodsExample example);

    List<TGoods> selectByExample(TGoodsExample example);

    int updateByExampleSelective(@Param("record") TGoods record, @Param("example") TGoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") TGoods record, @Param("example") TGoodsExample example);

    int updateByExample(@Param("record") TGoods record, @Param("example") TGoodsExample example);
}