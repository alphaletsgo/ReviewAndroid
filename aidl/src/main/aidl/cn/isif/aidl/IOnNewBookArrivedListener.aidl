// IOnNewBookArrivedListener.aidl
package cn.isif.aidl;
import cn.isif.aidl.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
