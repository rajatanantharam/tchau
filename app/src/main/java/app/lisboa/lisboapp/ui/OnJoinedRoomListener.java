package app.lisboa.lisboapp.ui;

import android.view.View;
import android.widget.TextView;

import app.lisboa.lisboapp.model.Event;

/**
 * Created by anjali on 03/11/16.
 */
public interface OnJoinedRoomListener {
    void onJoinedRoom(Event event, View joinRoom);
}
