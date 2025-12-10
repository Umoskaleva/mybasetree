package com.mybasetree;

import com.mybasetree.entity.Address;
import com.mybasetree.entity.AddressType;
import com.mybasetree.entity.Person;
import com.mybasetree.entity.RelationshipRole;
import com.mybasetree.repository.PersonRepository;
import com.mybasetree.service.PersonService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
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


