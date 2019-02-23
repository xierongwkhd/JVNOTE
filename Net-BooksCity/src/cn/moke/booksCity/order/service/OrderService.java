package cn.moke.booksCity.order.service;

import cn.itcast.jdbc.JdbcUtils;
import cn.moke.booksCity.order.dao.OrderDao;
import cn.moke.booksCity.order.domain.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by MOKE on 2019/2/3.
 */
public class OrderService {
    private OrderDao orderDao = new OrderDao();

    public void add(Order order){

        try {
            JdbcUtils.beginTransaction();//开启事务
            orderDao.addOrder(order);
            orderDao.addOrderItemList(order.getOrderItemList());

            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();//回滚事务
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    public List<Order> myOrders(String uid) {

        return orderDao.findByUid(uid);
    }

    public Order load(String oid) {
        return orderDao.load(oid);
    }

    public void confirm(String oid) throws OrderException{//确认收货
        int state = orderDao.getStateByOid(oid);
        if(state!=3)throw new OrderException("订单确认失败,你可能未付款");
        orderDao.updateState(oid,4);
    }

    public void zhiFu(String oid) {
        int state = orderDao.getStateByOid(oid);
        if(state==1){
            orderDao.updateState(oid,2);
        }
    }
}
