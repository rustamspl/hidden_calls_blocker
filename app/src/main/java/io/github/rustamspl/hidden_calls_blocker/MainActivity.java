package io.github.rustamspl.hidden_calls_blocker;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import io.github.rustamspl.hidden_calls_blocker.R;

public class MainActivity extends Activity {
    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED||
                checkSelfPermission(Manifest.permission.READ_CALL_LOG ) == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE,Manifest.permission.READ_CALL_LOG};
            requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }

}
