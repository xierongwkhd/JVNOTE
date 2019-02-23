package cn.moke.booksCity.category.service;

import cn.moke.booksCity.book.dao.BookDao;
import cn.moke.booksCity.category.dao.CategoryDao;
import cn.moke.booksCity.category.domain.Category;
import cn.moke.booksCity.category.servlet.admin.CategoryException;

import java.util.List;

/**
 * Created by MOKE on 2019/2/2.
 */
public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();
    private BookDao bookDao = new BookDao();

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public void add(Category category) {
        categoryDao.add(category);
    }

    public void delete(String cid) throws CategoryException {
        int count = bookDao.getCountByCid(cid);//获取该分类下图书的本数

        if(count>0) throw new CategoryException("该分类下还有图书，无法删除！");
        categoryDao.delete(cid);//无图书则删除该分类
    }

    public Category load(String cid) {
        return categoryDao.load(cid);
    }

    public void edit(Category category) {
        categoryDao.edit(category);
    }
}
