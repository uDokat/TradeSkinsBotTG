package ua.dokat.enums;

public enum SkinType {
    KNIFE("csgo_type_knife"),
    PISTOL(""),
    SNIPER_RIFLE("csgo_type_sniperrifle"),
    RIFLE(""),
    CONTAINER(""),
    GLOVES(""),
    SHOTGUN(""),
    MUSIC_KIT(""),
    STICKER(""),
    SUBMACHINE_GUN(""),
    MACHINE_GUN(""),
    AGENT(""),
    NON_TYPE("");

    private final String cmd;

    SkinType(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd(){
        return cmd;
    }

    public static SkinType getType(String cmd){
        for (SkinType type : SkinType.values()){
            if (type.cmd.equals(cmd)){
                return type;
            }
        }

        return NON_TYPE;
    }
}
