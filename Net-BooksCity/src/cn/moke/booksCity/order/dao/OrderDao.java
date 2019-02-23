package cn.moke.booksCity.order.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.moke.booksCity.book.domain.Book;
import cn.moke.booksCity.order.domain.Order;
import cn.moke.booksCity.order.domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MOKE on 2019/2/3.
 */
public class OrderDao {
    private QueryRunner qr = new TxQueryRunner();

    public void addOrder(Order order){
        String sql = "insert into orders values(?,?,?,?,?,?)";
        Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
        Object[] params = {order.getOid(),timestamp,order.getTotal(),order.getState(),order.getOwner().getUid(),order.getAddress()};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void  addOrderItemList(List<OrderItem> orderItemList){
        String sql = "insert into orderitem value(?,?,?,?,?)";
        Object[][] params = new Object[orderItemList.size()][];
        for(int i=0;i<orderItemList.size();i++){
            OrderItem item = orderItemList.get(i);
            params[i] = new Object[]{item.getIid(),item.getCount(),item.getSubtotal(),item.getOrder().getOid(),item.getBook().getBid()};
        }
        try {
            qr.batch(sql,params);//批处理
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> findByUid(String uid) {
        String sql = "select * from orders where uid=?";
        try {
            List<Order> orderList = qr.query(sql,new BeanListHandler<Order>(Order.class),uid);
            for(Order order : orderList){
                loadOrderItems(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderItems(Order order){//加载指定订单的所有订单条目
        String sql = "select * from orderitem i,book b where i.bid=b.bid and oid=?";
        try {
            List<Map<String,Object>> mapList = qr.query(sql,new MapListHandler(),order.getOid());
            List<OrderItem> orderItems = toOrderItemList(mapList);
            order.setOrderItemList(orderItems);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for(Map<String,Object> map : mapList){
            OrderItem item = toOrderItem(map);
            orderItemList.add(item);
        }
        return orderItemList;
    }

    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map,OrderItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        orderItem.setBook(book);
        return orderItem;
    }

    public Order load(String oid) {
        String sql = "select * from orders where oid=?";
        try {
            Order order = qr.query(sql,new BeanHandler<Order>(Order.class),oid);
            loadOrderItems(order);//加载所有条目
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getStateByOid(String oid){
        String sql = "select state from orders where oid=?";
        try {
            Number num = (Number)qr.query(sql,new ScalarHandler(),oid);
            return num.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  void updateState(String oid,int state){
        String sql = "update orders set state=? where oid=?";
        try {
            qr.update(sql,state,oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
