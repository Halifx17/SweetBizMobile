package com.example.sweetbizmobile;

public class UserMessage {

    String nickname;
    String profileUri;

    public UserMessage() {
    }

    public UserMessage(String nickname, String profileUri) {
        this.nickname = nickname;
        this.profileUri = profileUri;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }
}
