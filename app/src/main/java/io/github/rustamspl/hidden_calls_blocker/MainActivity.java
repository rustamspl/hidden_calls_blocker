package io.github.rustamspl.hidden_calls_blocker;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class MainActivity extends Activity {
    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.READ_CALL_LOG ) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.WRITE_CALL_LOG ) == PackageManager.PERMISSION_DENIED
        )
        {
            String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE,Manifest.permission.READ_CALL_LOG,Manifest.permission.WRITE_CALL_LOG};
            requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
        }

//        Button b=(Button) findViewById(R.id.button);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //clearLog();
//                //readLogs();
//            }
//        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }
//    private void clearLog(){
//        long date = new Date(System.currentTimeMillis() - 1L * 24 * 3600 * 1000).getTime();
//        getContentResolver().delete(CallLog.Calls.CONTENT_URI,
//                CallLog.Calls.NUMBER + "='' and "+CallLog.Calls.DATE+ " > ?",
//                new String[]{""+date});
//    }
//    private void readLogs(){
//        Cursor mCur = this.getContentResolver().query(CallLog.Calls.CONTENT_URI,
//               null, null, null, null);
//        mCur.moveToFirst();
//        int number = mCur.getColumnIndex(CallLog.Calls.NUMBER);
//        int type = mCur.getColumnIndex(CallLog.Calls.TYPE);
////        int date = mCur.getColumnIndex(CallLog.Calls.DATE);
////        int duration = mCur.getColumnIndex(CallLog.Calls.DURATION);
//
//        if (mCur != null && mCur.moveToFirst() && mCur.getCount() > 0) {
//            while (mCur.isAfterLast() == false) {
//                String phNumber = mCur.getString(number);
//                String callType = mCur.getString(type);
//
//                String dir = null;
//                int dircode = Integer.parseInt(callType);
//                switch (dircode) {
//                    case CallLog.Calls.OUTGOING_TYPE:
//                        dir = "OUTGOING";
//                        break;
//
//                    case CallLog.Calls.INCOMING_TYPE:
//                        dir = "INCOMING";
//                        break;
//
//                    case CallLog.Calls.MISSED_TYPE:
//                        dir = "MISSED";
//                        break;
//                }
//
//                Log.d("ZZZ","Num:"+phNumber+"; ct:"+dir);
//
//                mCur.moveToNext();
//            }
//        }
//    }


}
