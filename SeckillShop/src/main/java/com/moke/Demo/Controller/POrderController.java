package com.moke.Demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moke.Demo.Domain.PGoods;
import com.moke.Demo.Domain.POrderInfo;
import com.moke.Demo.Domain.PUser;
import com.moke.Demo.Service.PGoodsService;
import com.moke.Demo.Service.POrderService;
import com.moke.Demo.Vo.PGoodsVo;
import com.moke.Demo.Vo.POrderDetailVo;
import com.moke.Demo.base.CodeMsg;
import com.moke.Demo.base.Result;

@Controller
@RequestMapping("/order")
public class POrderController {

	@Autowired
	private POrderService pOrderService;
	@Autowired
	private PGoodsService pGoodsService;
	
	@RequestMapping("/detail")
	@ResponseBody
	public Result<POrderDetailVo> detail(Model model,PUser user,@RequestParam long orderId){
		if(user==null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		POrderInfo order = this.pOrderService.getOrderById(orderId);
		if(order==null)
			return Result.error(CodeMsg.ORDER_NOT_EXITS);
		PGoodsVo goods = pGoodsService.getGoodsVoByGoodsId(order.getGoodsId());
		POrderDetailVo odv = new POrderDetailVo();
		odv.setGoods(goods);
		odv.setOrder(order);
		return Result.success(odv);
	}
	
}
