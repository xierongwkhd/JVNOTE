package cn.moke.booksCity.book.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.moke.booksCity.book.domain.Book;
import cn.moke.booksCity.category.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by MOKE on 2019/2/2.
 */
public class BookDao {
    private QueryRunner qr = new TxQueryRunner();

    public List<Book> findAll(){//查询所有
        String sql = "select * from book where del=false";
        try {
            return qr.query(sql,new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findByCategory(String cid) {//分类查询
        String sql = "select * from book where cid=? and del=false";
        try {
            return qr.query(sql,new BeanListHandler<Book>(Book.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book findBybid(String bid) {//图书详细
        String sql = "select * from book where bid=?";
        try {
            //需要在book对象中保存category信息
            Map<String,Object> map =  qr.query(sql,new MapHandler(),bid);
            Category category = CommonUtils.toBean(map,Category.class);
            Book book = CommonUtils.toBean(map,Book.class);
            book.setCategory(category);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCountByCid(String cid) {
        String sql = "select count(*) from book where cid=?";
        try {
            Number num = (Number)qr.query(sql,new ScalarHandler(),cid);
            return num.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Book book) {
        String sql = "insert into book values(?,?,?,?,?,?)";
        Object[] params = {book.getBid(),book.getBname(),book.getPrice(),book.getAuthor(),book.getImage(),book.getCategory().getCid(),false};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  void delete(String bid){//后台（假）删除图书
        String sql = "update book set del=true where bid=?";
        try {
            qr.update(sql,bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(Book book) {
        String sql = "update book set bname=?,price=?,author=?,image=?,cid=? where bid=?";
        Object[] params = {book.getBname(),book.getPrice(),book.getAuthor(),book.getImage(),book.getCategory().getCid(),book.getBid()};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
