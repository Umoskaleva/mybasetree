package com.mybasetree.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Embeddable //встраиваемый компонент адресов в основную БД
public class Address {
    @Column(name = "gubernia", length = 500)
    private String gubernia;
    @Column(name = "uezd", length = 500)
    private String uezd;
    @Column(name = "volost", length = 500)
    private String volost;
    @Column(name = "naseleniyPunct", length = 500)
    private String naseleniyPunct;

    @Enumerated(EnumType.STRING) //сохраняется имя константы
    @Column(name = "address_type")
    private AddressType addressType;

    public Address() {

    }

    public Address(String gubernia, String uezd, String volost, String naseleniyPunct, AddressType addressType) {
        this.gubernia = gubernia;
        this.uezd = uezd;
        this.volost = volost;
        this.naseleniyPunct = naseleniyPunct;
        this.addressType = addressType;
    }

    public String getGubernia() {
        return gubernia;
    }

    public String getUezd() {
        return uezd;
    }

    public String getVolost() {
        return volost;
    }

    public String getNaseleniyPunct() {
        return naseleniyPunct;
    }

    public AddressType getAddressType() {
        return addressType;
    }


    // добавим методы показа адресов по типам BIRTH,LIVEONE,LIVETWO,LIVETHREE,WORKONE,WORKTWO,WORKTHREE


    public void setGubernia(String gubernia) {
        this.gubernia = gubernia;
    }

    public void setUezd(String uezd) {
        this.uezd = uezd;
    }

    public void setVolost(String volost) {
        this.volost = volost;
    }

    public void setNaseleniyPunct(String naseleniyPunct) {
        this.naseleniyPunct = naseleniyPunct;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getFormatAddress(){
        StringBuilder sb = new StringBuilder();
        if (gubernia != null) sb.append(gubernia).append(" губерния, ");
        if (uezd != null) sb.append(uezd).append(" уезд, ");
        if (volost != null) sb.append(volost).append(" волость, ");
        if (naseleniyPunct != null) sb.append(naseleniyPunct).append(" населенный пункт");
        if (addressType != null) sb.append(" (").append(addressType.getDisplayName()).append(")");
        return sb.toString();
    }



    @Override
    public String toString(){
        return getFormatAddress();
    }
}


