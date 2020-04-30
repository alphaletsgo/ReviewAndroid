// IManager.aidl
package cn.isif.aidlservice;
import cn.isif.aidlservice.Book;

// Declare any non-default types here with import statements
interface IManager {
    List<Book> getBookList();
    Book addBookIn(in Book book);//in
    Book addBookOut(out Book book);//out
    Book addBookInOut(inout Book book);//inout
}
