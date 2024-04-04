package ua.dokat.enums;

public enum ButtonType {

    BUY_ORDER_BUFF("buy_order_buff");

    private final String cmd;

    ButtonType(String cmd) {
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
