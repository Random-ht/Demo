package com.example.random.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 上传错误日志   此工具类只需要在首页调用
 * 即： SubmitCrash2Server.getInstance(MainActivity.this).SubmitCrash();
 */
public class SubmitCrashServer {

    private static SubmitCrashServer crash2Server;
    private Context context;
    private boolean taskIsStart = false;//定时器是否已经启动

    public static SubmitCrashServer getInstance(Context context) {
        if (crash2Server == null) {
            synchronized (SubmitCrashServer.class) {
                if (crash2Server == null) {
                    crash2Server = new SubmitCrashServer(context);
                }
            }
        }
        return crash2Server;
    }


    public SubmitCrashServer(Context context) {
        this.context = context;
    }

    /**
     * 上传错误日志到 服务器     如果上传成功就删除本地保存的错误日志  如果上传失败则继续上传
     */
    public void SubmitCrash() {

        if (TextUtils.isEmpty(getCrashInfo())) {
            return;
        }
        if (context == null) {
            return;
        }

        /**
         * 以下是联网操作
         */
//        VolleyResult volleyResult = new VolleyResult(context, Urls.UserInfoUrl + Urls.submitCrashInfo, Params.submitCrash2Server(getDeviceInfo(), getCrashInfo()), new VolleyResultCallBack() {
//            @Override
//            public void Success(String result) {
//                Result response = JSON.parseObject(result, Result.class);
//                if (Constant.SUCCESS_CODE == response.getCode()) {
//
//
//                    /**
//                     * 如果上传成功   就删除上传的本地文件
//                     */
//                    if (getCrashFile() != null) {
//                        //干掉本地异常日志
//                        FileUtils.deleteDir(getCrashFile());
//
//                        if (timer != null && task != null) {
//                            //上传成功   定时器取消
//                            timer.cancel();
//                            task.cancel();
//                            timer = null;
//                            task = null;
//                        }
//                    }
//
//                } else {
//
//                    if (!taskIsStart && timer != null && task != null) {
//                        //上传失败   等五分钟再继续传
//                        timer.schedule(task, 1000 * 60 * 5);
//                        //设置定时器已经启动
//                        taskIsStart = true;
//                    }
//                }
//            }
//
//            @Override
//            public void Fail(String error_message) {
//                if (!taskIsStart && timer != null && task != null) {
//                    //上传失败   等五分钟再继续传
//                    timer.schedule(task, 1000 * 60 * 5);
//                    //设置定时器已经启动
//                    taskIsStart = true;
//                }
//            }
//        });
//        volleyResult.StartUsePostMethodToAchieveJsonObjectData();

    }

    /**
     * 获取日志存储路径
     *
     * @return
     */
    private File getCrashFile() {
        String defaultDir = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && Utils.getContext().getExternalCacheDir() != null)
            defaultDir = Utils.getContext().getExternalCacheDir() + File.separator + "crash" + File.separator;
        else {
            defaultDir = Utils.getContext().getCacheDir() + File.separator + "crash" + File.separator;
        }

        if (FileUtils.listFilesInDir(defaultDir) != null && !FileUtils.listFilesInDir(defaultDir).isEmpty()) {

            return FileUtils.listFilesInDir(defaultDir).get(0);
        }

        return null;
    }

    /**
     * 获取上传的错误日志的内容
     *
     * @return
     */
    private String getCrashInfo() {

        //判断文件是否为空
        if (getCrashFile() == null) {
            return "";
        }

        //读取日志文件的内容
        String str = FileIOUtils.readFile2String(getCrashFile());

        return str;

    }

    /**
     * 获取设备信息
     *
     * @return
     */
    private String getDeviceInfo() {

        String deviceInfo = "";
        String versionName = "";
        int versionCode = 0;
        try {
            PackageInfo pi = Utils.getContext().getPackageManager().getPackageInfo(Utils.getContext().getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        deviceInfo = "\n************* Crash Log Head ****************" +
//                "\nDevice Manufacturer|| " + Build.MANUFACTURER +// 设备厂商
//                "\nDevice Model       || " + Build.MODEL +// 设备型号
//                "\nAndroid Version    || " + Build.VERSION.RELEASE +// 系统版本
//                "\nAndroid SDK        || " + Build.VERSION.SDK_INT +// SDK版本
//                "\nApp VersionName    || " + versionName +
//                "\nApp VersionCode    || " + versionCode +
//                "\n************* Crash Log Head ****************\n\n";

        deviceInfo = Build.MANUFACTURER + "||" +  // 设备厂商
                Build.MODEL + "||" +            // 设备型号
                Build.VERSION.RELEASE + "||" + // 系统版本
                Build.VERSION.SDK_INT + "||" + // SDK版本
                versionName + "||" +
                versionCode;

        return deviceInfo;
    }


    /**
     * 定义一个定时器   在上传日志失败的时候  等五分钟再传
     */

    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //如果上传失败  则写个定时器  等会再传
            SubmitCrash();
        }
    };

}
