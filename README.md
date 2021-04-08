# Printer
杭州迈冲科技小票打印机实例

## 一、创建 AndroidStudio 项目，导入库文件

将 printerlibs.jar 和对应平台的 so 文件拷贝到 libs 目录下。

在 app 目录中的 build.gradle 文件中添加如下依赖：
```
sourceSets {
    main {
        jniLibs.srcDirs = ['libs']
    }
}
```

## 二、使用说明

### 初始化小票打印机
小票打印机使用串口与开发板连接，使用小票打印机前需要初始化串口。
``` java
Pos pos = new Pos();
COMIO comio = new COMIO();

pos.Set(comio);
comio.SetCallBack(new IOCallBack() {
    @Override
    public void OnOpen() {
        Log.i(TAG, "Print is connected");
    }

    @Override
    public void OnOpenFailed() {
        Log.e(TAG, "Print open failed");
        Toast.makeText(MainActivity.this, "连接打印机失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnClose() {
        Log.i(TAG, "Print is close");
    }
});    

execTask.execute(new Runnable() {
    @Override
    public void run() {
        comio.Open("/dev/ttyS3", 9600, 0);
    }
});
```

### 执行打印命令
``` java
// 打印文字
PrintUtil.printText(pos, 384, false, false, 1);
// 打印二维码
PrintUtil.printQrCode(pos, 384, true, false, 1);
// 打印模板小票
PrintUtil.printTicket(pos, 384, true, true, 1);
```

## 三、下载体验

[小票打印机实例 apk 下载](https://github.com/Hangzhou-Maichong-Technology/Printer/raw/master/apk/Printer.apk)