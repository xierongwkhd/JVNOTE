package com.moke.Demo.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.moke.Demo.Domain.POrder;
import com.moke.Demo.Domain.POrderInfo;

@Mapper
public interface POrderDao {

	@Select("select * from t_order where user_id = #{userId} and goods_id = #{goodsId}")
	public POrder getMiaoShaOrderByUserIdGoodsId(@Param("userId")long userId, @Param("goodsId")long goodsId);

	@Insert("insert into t_order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
			+ "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
	@SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
	public long insertOrderInfo(POrderInfo orderInfo);

	@Insert("insert into t_order(user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
	public void insertOrder(POrder order);
}
