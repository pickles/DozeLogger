package io.manabu.dozelogger;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
    private static final String LOG_FILE = "DozeLog.txt";
    private static final String TAG = "DozeLogger";

    public static void log(Context ctx, String message) {
        Log.d(TAG, message);
        write(ctx, message);
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    synchronized private static void write(Context ctx, String message) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(ctx.openFileOutput(LOG_FILE, Context.MODE_APPEND));
            out.println(sdf.format(new Date()) + " : " + message);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Failed to write log file.", e);
        } finally {
            if (out != null) out.close();
        }
    }
}
