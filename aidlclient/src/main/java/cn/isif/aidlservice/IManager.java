/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package cn.isif.aidlservice;
// Declare any non-default types here with import statements

public interface IManager extends android.os.IInterface
{
  /** Default implementation for IManager. */
  public static class Default implements IManager
  {
    @Override public java.util.List<cn.isif.aidlservice.Book> getBookList() throws android.os.RemoteException
    {
      return null;
    }
    @Override public void addBook(cn.isif.aidlservice.Book book) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements IManager
  {
    private static final String DESCRIPTOR = "cn.isif.aidlservice.IManager";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an cn.isif.aidlservice.IManager interface,
     * generating a proxy if needed.
     */
    public static IManager asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof IManager))) {
        return ((IManager)iin);
      }
      return new Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_getBookList:
        {
          data.enforceInterface(descriptor);
          java.util.List<cn.isif.aidlservice.Book> _result = this.getBookList();
          reply.writeNoException();
          reply.writeTypedList(_result);
          return true;
        }
        case TRANSACTION_addBook:
        {
          data.enforceInterface(descriptor);
          cn.isif.aidlservice.Book _arg0;
          if ((0!=data.readInt())) {
            _arg0 = cn.isif.aidlservice.Book.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          this.addBook(_arg0);
          reply.writeNoException();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements IManager
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public java.util.List<cn.isif.aidlservice.Book> getBookList() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<cn.isif.aidlservice.Book> _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getBookList, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getBookList();
          }
          _reply.readException();
          _result = _reply.createTypedArrayList(cn.isif.aidlservice.Book.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void addBook(cn.isif.aidlservice.Book book) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((book!=null)) {
            _data.writeInt(1);
            book.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_addBook, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().addBook(book);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      public static IManager sDefaultImpl;
    }
    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    public static boolean setDefaultImpl(IManager impl) {
      if (Proxy.sDefaultImpl == null && impl != null) {
        Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static IManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
  }
  public java.util.List<cn.isif.aidlservice.Book> getBookList() throws android.os.RemoteException;
  public void addBook(cn.isif.aidlservice.Book book) throws android.os.RemoteException;
}
