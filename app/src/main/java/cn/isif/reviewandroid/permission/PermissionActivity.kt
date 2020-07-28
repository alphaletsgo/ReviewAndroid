package cn.isif.reviewandroid.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import cn.isif.reviewandroid.R

/**
 * 测试Android权限
 * 这里模拟相机打开所需的权限声明，我们知道打开相机需要相机权限和位置权限，所以为了打开相机我们需要具备这两个权限
 * shouldShowRequestPermissionRationale
 * 1，在允许询问时返回true ；
 * 2，在权限通过 或者权限被拒绝并且禁止询问时返回false 但是有一个例外，就是重来没有询问过的时候，也是返回的false
 *
 * 所以单纯的使用shouldShowRequestPermissionRationale去做什么判断，是没用的，只能在请求权限回调后再使用。
 */
class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        requestPermission()
    }

    /**
     * 判断权限申请结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //不完善方案，不能处理不再提示情况
//        when(requestCode){
//            1 -> {
//                var allGranted = true
//                for (result in grantResults){
//                    if (result != PackageManager.PERMISSION_GRANTED){
//                        allGranted = false
//                    }
//                }
//                if (allGranted){
//                    takePic()
//                }else{
//                    //如果用户没有授权所需权限则重新弹出权限申请
//                    AlertDialog.Builder(this).apply {
//                        setMessage("拍照功能需要您同意相机和定位权限")
//                        setCancelable(false)
//                        setPositiveButton("确定"){ _,_ ->
//                            requestPermission()
//                        }
//                    }.show()
//                }
//            }
//        }

        //完善的方案
        when(requestCode){
            1 -> {
                var denied = ArrayList<String>() //记录可以再次询问的权限
                var deniedAndNeverAskAgain = ArrayList<String>() //记录不能再次授权的权限
                grantResults.forEachIndexed { index, result ->
                    if (result != PackageManager.PERMISSION_GRANTED){
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[index])){
                            denied.add(permissions[index])
                        }else{
                            deniedAndNeverAskAgain.add(permissions[index])
                        }
                    }
                }
                if (denied.isEmpty() && deniedAndNeverAskAgain.isEmpty()){//获得授权
                    takePic()
                }else{//未获得授权
                    if (denied.isNotEmpty()) {
                        AlertDialog.Builder(this).apply {
                            setMessage("拍照功能需要您同意相机和定位权限")
                            setCancelable(false)
                            setPositiveButton("确定") { _, _ ->
                                requestPermission()
                            }
                        }.show()
                    }else{
                        AlertDialog.Builder(this).apply {
                            setMessage("您可能需要去设置中同意使用相机和位置权限")
                            setCancelable(false)
                            setPositiveButton("确定") { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", packageName, null)
                                intent.data = uri
                                startActivityForResult(intent, 1)
                            }
                        }.show()

                    }

                }

            }
        }

    }

    /**
     * 申请权限
     */
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    /**
     * 模拟照相
     */
    private fun takePic(){
        Toast.makeText(this,"Take Photo ing",Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val TAG:String = "PermissionActivity"

        fun startActivity(activity:Activity){
            activity.startActivity(Intent(activity, PermissionActivity::class.java))
        }
    }
}