package app.lisboa.lisboapp.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import app.lisboa.lisboapp.model.Event;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Rajat Anantharam on 04/11/16.
 */
public class NotificationBuilder {

    private static final int TIMEOUT_SECONDS = 15;
    private String notificationBody;

    public void send(Event event) {
        notificationBody = "Uh oh! Looks like " + event.hostName + " is up to something at " + event.locationName;
        new PostNotificationTask().execute("");

    }

    private Response performHttpPost(String postBody) throws IOException {

        OkHttpClient client = buildHttpClient();
        Request.Builder builder = new Request.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), postBody);
        builder.url("https://fcm.googleapis.com/fcm/send");
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("Authorization", "key=AIzaSyArxrRc6aQflDmjtWsKbMeEMf-HQZ4HaH0");
        builder.post(requestBody);
        return client.newCall(builder.build()).execute();

    }

    private OkHttpClient buildHttpClient(){
        return new OkHttpClient.Builder().connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIMEOUT_SECONDS,TimeUnit.SECONDS).build();
    }

    private class PostNotificationTask extends AsyncTask<String, Void, Response> {

        @Override
        protected Response doInBackground(String... strings) {

            Response response = null;
            try {
//                response = performHttpPost("{\n" +
//                        "  \"to\": \"/topics/events\",\n" +
//                        "  \"data\": {\n" +
//                        "    \"message\": \"Wazzzzaaaa!\"\n" +
//                        "   }\n" +
//                        "}");

                response = performHttpPost("{\"to\":\"/topics/events\",\"priority\":\"high\",\"notification\":{\"body\":"+notificationBody+"}}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Log.i(getClass().getSimpleName(), String.valueOf(response.body()));
        }
    }

}
