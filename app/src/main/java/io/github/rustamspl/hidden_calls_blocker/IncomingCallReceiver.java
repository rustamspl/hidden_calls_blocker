package io.github.rustamspl.hidden_calls_blocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Looper;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Date;

public class IncomingCallReceiver extends BroadcastReceiver {
    private static Boolean waitNum=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.PHONE_STATE".equals(intent.getAction())) {
            try {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
                  this.endCallIfBlocked(context,number);
                }else{
                    waitNum=false;
                }
            } catch (Exception e) {
                waitNum=false;
                e.printStackTrace();
            }
        }else{
            waitNum=false;
        }
    }
    private void endCallIfBlocked(final Context context, String number) {
        if(number==null){
            if(android.os.Build.VERSION.SDK_INT <28){
                endCall(context);
                return;
            }
            if(waitNum) {
                endCall(context);
            }else{
                waitNum=true;
            }
        }else{
            waitNum=false;
        }
    }

    private void endCall(Context context){
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method m1 = tm.getClass().getDeclaredMethod("getITelephony");
            m1.setAccessible(true);
            Object iTelephony = m1.invoke(tm);
            Method m3 = iTelephony.getClass().getDeclaredMethod("endCall");
            m3.invoke(iTelephony);
            clearLogDelayed(context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void   clearLogDelayed(final Context context){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                       clearLog(context);
                    }
                },
                3000);
    }
    private void clearLog(Context context){
        long date = new Date(System.currentTimeMillis() - 5L * 60 * 1000).getTime();
        context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,
                CallLog.Calls.NUMBER + "='' and "+CallLog.Calls.DATE+ " > ?",
                new String[]{""+date});
    }
}
