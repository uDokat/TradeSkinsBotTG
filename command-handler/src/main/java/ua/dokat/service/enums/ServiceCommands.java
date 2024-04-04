package ua.dokat.service.enums;

public enum ServiceCommands {

    HELP("/help"),
    START("/start"),
    MENU("/menu"),
    ITEMS("/items"),
    ADD_ITEM("/add_item"),
    REGISTRATION("/registration"),
    TOKEN("/token"),
    COOKIE("/cookie"),
    RELOAD_INVENTORY("/reload_inventory");

    private final String cmd;

    ServiceCommands(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean equals(String cmd){
        return this.toString().equals(cmd);
    }
}
