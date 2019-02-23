package cn.moke.booksCity.order.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.moke.booksCity.cart.domain.Cart;
import cn.moke.booksCity.cart.domain.CartItem;
import cn.moke.booksCity.order.domain.Order;
import cn.moke.booksCity.order.domain.OrderItem;
import cn.moke.booksCity.order.service.OrderException;
import cn.moke.booksCity.order.service.OrderService;
import cn.moke.booksCity.user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//付款
        //易宝13个参数
        Properties props = new Properties();
        InputStream input = this.getClass().getClassLoader()
                .getResourceAsStream("merchantInfo.properties");
        props.load(input);
        String p0_Cmd = "Buy";
        String p1_MerId = props.getProperty("p1_MerId");//测试账号
        String p2_Order = request.getParameter("oid");
        String p3_Amt = "0.01";
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        String p8_Url = props.getProperty("p8_Url");//回馈地址
        String p9_SAF = "";
        String pa_MP = "";
        String pd_FrpId = request.getParameter("pd_FrpId");
        String pr_NeedResponse = "1";

        //计算hmac
        String keyValue = props.getProperty("keyValue");//13+1
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);

        StringBuilder url = new StringBuilder(props.getProperty("url"));//生成重定向支付页面
        url.append("?p0_Cmd=").append(p0_Cmd);
        url.append("&p1_MerId=").append(p1_MerId);
        url.append("&p2_Order=").append(p2_Order);
        url.append("&p3_Amt=").append(p3_Amt);
        url.append("&p4_Cur=").append(p4_Cur);
        url.append("&p5_Pid=").append(p5_Pid);
        url.append("&p6_Pcat=").append(p6_Pcat);
        url.append("&p7_Pdesc=").append(p7_Pdesc);
        url.append("&p8_Url=").append(p8_Url);
        url.append("&p9_SAF=").append(p9_SAF);
        url.append("&pa_MP=").append(pa_MP);
        url.append("&pd_FrpId=").append(pd_FrpId);
        url.append("&pr_NeedResponse=").append(pr_NeedResponse);
        url.append("&hmac=").append(hmac);

        response.sendRedirect(url.toString());
        return null;
    }

    public String back(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//支付回调
        //获取回馈的11+1参数（两种回调方式：点对点、重定向到指定页面）
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");

        String hmac = request.getParameter("hmac");

        //校验访问者是否为易宝
        Properties props = new Properties();
        InputStream input = this.getClass().getClassLoader()
                .getResourceAsStream("merchantInfo.properties");
        props.load(input);
        String keyValue = props.getProperty("keyValue");
        boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);
        if(!bool) {//如果校验失败
            request.setAttribute("msg", "您不是什么好东西！");
            return "f:/jsps/msg.jsp";
        }
        //获取状态订单，确定是否要修改订单状态，以及添加积分等业务操作
        orderService.zhiFu(r6_Order);
        if(r9_BType.equals("2")) {//判断当前回调方式
            response.getWriter().print("success");//如果为点对点，需要回馈以success开头的字符串
        }
        request.setAttribute("msg", "支付成功！请等待卖家发货！");
        return "f:/jsps/msg.jsp";
    }

    public String comfirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//确认收货
        String oid = request.getParameter("oid");
        try {
            orderService.confirm(oid);
            request.setAttribute("msg","恭喜，交易成功");
        } catch (OrderException e) {
            request.setAttribute("msg",e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }

    public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//付款
        request.setAttribute("order",orderService.load(request.getParameter("oid")));
        return "f:/jsps/order/desc.jsp";
    }

    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Order order = new Order();
        order.setOid(CommonUtils.uuid());
        order.setOrdertime(new Date());
        order.setState(1);
        User user = (User)request.getSession().getAttribute("session_user");
        order.setOwner(user);
        order.setTotal(cart.getTotal());
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for(CartItem cartItem : cart.getCartItems()){
            OrderItem oi = new OrderItem();
            oi.setIid(CommonUtils.uuid());
            oi.setCount(cartItem.getCount());
            oi.setBook(cartItem.getBook());
            oi.setSubtotal(cartItem.getSubtotal());
            oi.setOrder(order);
            orderItemList.add(oi);
        }
        order.setOrderItemList(orderItemList);

        cart.clear();
        orderService.add(order);
        request.setAttribute("order",order);
        return "f:/jsps/order/desc.jsp";
    }

    public String myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//订单
        User user = (User)request.getSession().getAttribute("session_user");
        List<Order> orderList = orderService.myOrders(user.getUid());
        request.setAttribute("orderList",orderList);
        System.out.println(orderList.toString());
        return "f:/jsps/order/list.jsp";
    }
}
