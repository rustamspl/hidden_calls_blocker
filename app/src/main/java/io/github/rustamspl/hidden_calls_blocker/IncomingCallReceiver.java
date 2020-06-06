package io.github.rustamspl.hidden_calls_blocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
