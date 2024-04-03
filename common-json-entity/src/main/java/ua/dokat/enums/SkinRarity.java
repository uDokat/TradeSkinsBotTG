package ua.dokat.enums;

public enum SkinRarity {

    CONSUMER(""),
    INDUSTRIAL(""),
    MIL_SPEC(""),
    RESTRICTED(""),
    CLASSIFIED(""),
    COVERT(""),
    CONTRABAND(""),
    NON_GRADE("");

    private final String cmd;

    SkinRarity(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd(){
        return cmd;
    }

    public static SkinRarity getRarity(String cmd){
        for (SkinRarity type : SkinRarity.values()){
            if (type.cmd.equals(cmd)){
                return type;
            }
        }

        return NON_GRADE;
    }
}
