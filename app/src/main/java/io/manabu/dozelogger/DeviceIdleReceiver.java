package io.manabu.dozelogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class DeviceIdleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        LogUtil.log(context, "IDLE Mode changed. Doze : " + pm.isDeviceIdleMode());
    }
}
