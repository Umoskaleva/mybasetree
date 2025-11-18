package com.mybasetree.entity;

public enum AddressType {
    BIRTH ("место рождения"),
    LIVEONE ("место проживания1"),
    LIVETWO ("место проживания2"),
    LIVETHREE ("место проживания3"),
    WORKONE("место работы1"),
    WORKTWO("место работы2"),
    WORKTHREE("место работы3");

    private final String displayName;
    AddressType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }


}
