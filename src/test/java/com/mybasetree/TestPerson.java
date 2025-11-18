package com.mybasetree;

import com.mybasetree.entity.Address;
import com.mybasetree.entity.AddressType;
import com.mybasetree.entity.Person;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public class TestPerson {
    public static void main(String[] args){
        Address birthAddress = new Address("Нижегородская",
                "Нижегородский",
                "Нижегородская",
                "Нижний Новгород",
                AddressType.BIRTH);

        Address lifeAddress = new Address("Нижегородская",
                "Нижегородский",
                "Нижегородская",
                "Нижний Новгород",
                AddressType.LIVEONE);


        // создаем список адресов рождение и проживание
        List<Address> addresses = new ArrayList<>();
        addresses.add(birthAddress);
        addresses.add(lifeAddress);

        //создадим person и установим адрес
        Person person = new Person("Тест", "Тест");
        person.setAddresses(addresses);

        System.out.println(person.toString());


    }
}


