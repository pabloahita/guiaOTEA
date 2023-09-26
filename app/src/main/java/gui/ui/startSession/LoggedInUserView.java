package gui.ui.startSession;

import cli.user.User;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private User user;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(User user) {
        this.user = user;
    }

    User getUser() {
        return user;
    }
}