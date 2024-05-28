package ua.dokat;

import lombok.Getter;

public enum CommandButtonType {

    SET_TOKEN("set_token", "Установить токен"),
    SET_COOKIE("set_cookie", "Установить куки"),
    REGISTRATION("registration", "Регистрация");

    private final String cmd;
    @Getter
    private final String text;

    CommandButtonType(String cmd, String text) {
        this.cmd = cmd;
        this.text = text;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean equals(String cmd){
        return this.toString().equals(cmd);
    }
}
