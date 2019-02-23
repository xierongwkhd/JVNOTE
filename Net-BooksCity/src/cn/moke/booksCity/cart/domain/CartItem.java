package cn.moke.booksCity.cart.domain;

import cn.moke.booksCity.book.domain.Book;

import java.math.BigDecimal;

/**
 * Created by MOKE on 2019/2/3.
 */
public class CartItem {
    private Book book;
    private  int count;

    public double getSubtotal(){
        BigDecimal d1 = new BigDecimal(book.getPrice() + "");
        BigDecimal d2 = new BigDecimal(count + "");
        return d1.multiply(d2).doubleValue();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
