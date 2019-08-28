package com.moke.Demo.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.moke.Demo.Domain.PGoods;
import com.moke.Demo.Domain.PGoodsSeckill;
import com.moke.Demo.Vo.PGoodsVo;

@Mapper
public interface PGoodsDao {

	@Select("select t2.*,t1.stock_count,t1.start_date,t1.end_date,t1.seckill_price from t_goods_seckill t1 left join t_goods t2 on t1.goods_id = t2.id")
	public List<PGoodsVo> getGoodsVo();

	@Select("select t2.*,t1.stock_count,t1.start_date,t1.end_date,t1.seckill_price from t_goods_seckill t1 left join t_goods t2 on t1.goods_id = t2.id where t2.id = #{goodsId}")
	public PGoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

	@Update("update t_goods_seckill set stock_count = stock_count-1 where goods_id=#{goodsId}")
	public void reduceStock(PGoodsSeckill g);
}
