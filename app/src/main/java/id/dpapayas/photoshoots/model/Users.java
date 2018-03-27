package id.dpapayas.photoshoots.model;

import java.util.HashMap;

public class Users {

    private String fullName;
    private String photo;
    private String email;
    private HashMap<String,Object> timestampJoined;

    public Users() {
    }

    public Users(String mFullName, String mPhoneNo, String mEmail, HashMap<String, Object> timestampJoined) {
        this.fullName = mFullName;
        this.photo = mPhoneNo;
        this.email = mEmail;
        this.timestampJoined = timestampJoined;
    }


    public String getFullName() {
        return fullName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, Object> getTimestampJoined() {
        return timestampJoined;
    }
}