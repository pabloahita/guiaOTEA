package gui.ui.startSession;

import com.google.gson.JsonObject;

//import cli.user.User;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private JsonObject data;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(JsonObject data) {
        this.data=data;
    }

}