package cn.isif.aidlservice;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * 要想在Binder中传输对象，该对象必须实现Parcelable
 */
public class Book implements Parcelable {
    public String bookName;
    public double price;

    public Book(double price, String bookName) {
        this.price = price;
        this.bookName = bookName;
    }

    protected Book(Parcel in) {
        price = in.readDouble();
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(price);
        dest.writeString(bookName);
    }

    public void readFromParcel(Parcel reply) {
        price = reply.readDouble();
        bookName = reply.readString();
    }

    @NonNull
    @Override
    public String toString() {
        return "[name: " + bookName + ", price: " + price + "]";
    }
}
