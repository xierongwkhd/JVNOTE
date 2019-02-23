package cn.moke.booksCity.book.service;

import cn.moke.booksCity.book.dao.BookDao;
import cn.moke.booksCity.book.domain.Book;

import java.util.List;

/**
 * Created by MOKE on 2019/2/2.
 */
public class BookService {
    private BookDao bookDao = new BookDao();

    public List<Book> findAll(){
        return bookDao.findAll();
    }

    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }

    public Book load(String bid) {
        return bookDao.findBybid(bid);
    }

    public void add(Book book) {
        bookDao.add(book);
    }

    public void delete(String bid){
        bookDao.delete(bid);
    }

    public void edit(Book book) {
        bookDao.edit(book);
    }
}
