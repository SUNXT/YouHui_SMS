package com.sun.youhui_sms;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sun.youhui_sms.contact.Contact;
import com.sun.youhui_sms.sms.SmsService;
import com.sun.youhui_sms.utils.Log2FileUtils;
import com.sun.youhui_sms.utils.OkhttpUtils;
import com.sun.youhui_sms.utils.PermissionUtils;
import com.sun.youhui_sms.utils.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

//    public static final int MESSAGE_CODE = 1;
//    private TextView mTextMessage;

    private String TAG = "Sms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTextMessage = (TextView) findViewById(R.id.tv_sms_content);
//        Uri uri = Uri.parse("content://sms");
//        getContentResolver().registerContentObserver(uri, true, mSmsObserve);
//        registerBroadcastReceiver();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (PERMISSION_GRANTED != checkSelfPermission(Manifest.permission_group.SMS)){
//                //动态申请
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 1);
//            }
//        }
        PermissionUtils.checkReadSmsPermission(this);
        startService(new Intent(getApplicationContext(), SmsService.class));

        //测试是否可以连接服务器
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log2FileUtils.saveLog2File(getApplicationContext(), "测试");
            }
        });
    }

    private void onTestConnection(){
        //进行网络请求
        Map<String, String> map = new HashMap<>();
        map.put(Contact.TAIL_NUM, "00000");
        map.put(Contact.MESSAGE_CODE, "00000");
        JSONObject jsonObject = new JSONObject(map);
        // TODO: 2017/6/19 这里需要处理url格式错误的异常
        OkhttpUtils.enqueue(Contact.URL_SEND_MESSAGE, jsonObject.toString(), new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "请求失败！" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.i(TAG, "请求成功！");

                }else {
                    Log.i(TAG, "请求失败！");
                }
            }
        });
    }

}
