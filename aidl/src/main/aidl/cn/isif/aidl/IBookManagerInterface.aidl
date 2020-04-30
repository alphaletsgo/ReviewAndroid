// IBookManagerInterface.aidl
package cn.isif.aidl;
import cn.isif.aidl.Book;
import cn.isif.aidl.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManagerInterface {
    void addBook(in Book book);
    List<Book> bookList();
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
