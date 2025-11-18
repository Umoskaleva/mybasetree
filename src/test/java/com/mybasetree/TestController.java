package com.mybasetree;

import com.mybasetree.entity.Person;
import com.mybasetree.repository.PersonRepository;
import com.mybasetree.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    @GetMapping
    public String testWorking(){
        return "Приложение работает!";
    }

    @GetMapping("/test")
    public String testCreatePerson() {
        Person person = new Person();
        person.setFirstName("Test Person");
        personRepository.save(person);
        return "Person created!";
    }
}
