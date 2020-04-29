// IManager.aidl
package cn.isif.aidlservice;
import cn.isif.aidlservice.Book;

// Declare any non-default types here with import statements

interface IManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
