package cn.isif.aidl

import android.os.Parcel
import android.os.Parcelable

class Book(var name: String? = "", var price:Double = 0.0) :Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "[name: ${name},price: ${price}$]"
    }

}