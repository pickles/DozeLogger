package io.manabu.dozelogger;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.Manifest.permission.*;
import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class MainActivity extends AppCompatActivity implements LocationListener, GpsStatus.Listener {

    private List<String> logs = new ArrayList<>();
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.log(this, "onCreate");

        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        checkPermissions();

        initLocation();

        IntentFilter filter = new IntentFilter();
        filter.addAction(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED);
        registerReceiver(new DeviceIdleReceiver(), filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.log(this, "onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.log(this, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.log(this, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.log(this, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.log(this, "onPause");
    }

    private static final int PERMISSION_REQUEST_CODE = 0;
    private void checkPermissions() {
        final String[] PERMISSIONS = new String[] {
                ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
        };

        for (String perm : PERMISSIONS) {
            if (checkSelfPermission(perm) == PERMISSION_DENIED) {
                requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_DENIED) {
                    InformationDialog dialog = new InformationDialog();
                    dialog.setTitle("エラー")
                            .setMessage("パーミッションを許可して下さい。")
                            .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).setIsCancelable(false)
                            .show(getFragmentManager(), "ERROR");

                    return;
                }
            }
        }

        initLocation();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
        locationManager.addGpsStatusListener(this);
    }



    @Override
    public void onLocationChanged(Location location) {
        LogUtil.log(this, "Location changed : " + location.getProvider()
                + " lat: " + location.getLatitude()
                + " lng: " + location.getLongitude()
                + " acc: " + location.getAccuracy());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                LogUtil.log(this, "LocationProvider status changed : " + provider + " Available");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                LogUtil.log(this, "LocationProvider status changed : " + provider + " Out of Service");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                LogUtil.log(this, "LocationProvider status changed : " + provider + " Temporarily Unavailable");
                break;
            default:
                LogUtil.log(this, "LocationProvider status changed : " + provider + " Unknown("+status+")");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        LogUtil.log(this, "LocationProvider enabled : " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        LogUtil.log(this, "LocationProvider disabled : " + provider);
    }

    @SuppressWarnings("Deprecate")
    @Override
    public void onGpsStatusChanged(int event) {
        GpsStatus status = locationManager.getGpsStatus(null);

        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED :
                LogUtil.log(this, "GpsStatusChanged : GPS_EVENT_STARTED");
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                LogUtil.log(this, "GpsStatusChanged : GPS_EVENT_STOPED");
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                LogUtil.log(this, "GpsStatusChanged : GPS_EVENT_FIRST_FIX (" + status.getTimeToFirstFix() + "ms)");
                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                Iterator<GpsSatellite> it = status.getSatellites().iterator();
                int num = 0;
                while (it.hasNext()) {it.next(); num++;}
                LogUtil.log(this, "GpsStatusChanged : GPS_EVENT_SATELLITE_STATUS (" + num + ")");
                break;
        }
    }
}
