package company.tap.gosellapi.open.controllers;

public class SessionManager {

    private boolean sessionStatus;


    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final SessionManager INSTANCE = new SessionManager();
    }


    public boolean isSessionEnabled(){
        return sessionStatus;
    }

    public void setActiveSession(boolean status){
        sessionStatus = status;
    }


}
