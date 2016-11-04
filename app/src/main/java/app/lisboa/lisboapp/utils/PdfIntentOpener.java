package app.lisboa.lisboapp.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Rajat Anantharam on 04/11/16.
 */
public class PdfIntentOpener {

    @SuppressLint("WorldReadableFiles")
    public static void openFile(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();

        InputStream in;
        OutputStream out;

        File file = new File(context.getFilesDir() , fileName);
        try {
            in = assetManager.open(fileName);
            out = context.openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            copyFile(in, out);
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + context.getFilesDir() + "/" + fileName),
                               "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent1 = Intent.createChooser(intent, "Open With");
        try {
            context.startActivity(intent1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No viewer available to view pdf", Toast.LENGTH_SHORT);
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }
}
