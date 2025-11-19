package com.mybasetree.controller;

import com.mybasetree.entity.Person;
import com.mybasetree.exception.PersonNotFoundException;
import com.mybasetree.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@Controller Принимает HTTP-запросы от клиента (браузера, мобильного приложения) и возвращает имена HTML-шаблонов (Thymeleaf)
//шаблоны:
//person-details
//family-tree
//error
@Controller
public class PersonWebController {

    @Autowired
    private PersonService personService;

    @GetMapping("/family-tree")
    public String showFamilyTree(Model model) {
        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);
        return "family-tree"; // имя HTML шаблона
    }

    @GetMapping("/person/{id}")
    public String showPersonDetails(@PathVariable Long id, Model model) {
        Person person = personService.findById(id);
        if (person == null) {
            return "error"; // шаблон error.html
        }
        model.addAttribute("person", person);
        return "person-details";// имя HTML шаблона
    }


    //поиск по имени и фамилии/только по имени/только по фамилии
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String firstName,
                         @RequestParam(required = false) String lastName,
                         Model model) {
        //убираем лишние пробелы
        if (firstName != null) firstName = firstName.trim();
        if (lastName != null) lastName = lastName.trim();
        List<Person> results = new ArrayList<>();
        boolean clickedSearch = false; //начинаем искать -> поиск не запрашивался clickedSearch = false
        if (firstName != null && firstName.isEmpty() && lastName != null && lastName.isEmpty()) {
            results = personService.findByFirstNameAndLastName(firstName, lastName);
            clickedSearch = true; //был поиск по имени и фамилии
        } else if (firstName != null && firstName.isEmpty()) {
            results = personService.findByFirstName(firstName);
            clickedSearch = true; //был поиск по имени
        } else if (lastName != null && lastName.isEmpty()) {
            results = personService.findByLastName(lastName);
            clickedSearch = true; //был поиск по фамилии
        }
        if (clickedSearch && results.isEmpty()) {
            throw new PersonNotFoundException("Человек с указанными данными не найден в базе");
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    @GetMapping("/search/before")
    public String searchBeforeDate(@RequestParam("date") LocalDate date, Model model) {
        List<Person> results = personService.findByDateOfBirthBefore(date);
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, родившихся до " + date);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    @GetMapping("/search/after")
    public String searchAfterDate(@RequestParam("date") LocalDate date, Model model) {
        List<Person> results = personService.findByDateOfBirthAfter(date);
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, родившихся после " + date);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    @GetMapping("/search/birth-gubernia")
    public String searchByBirthGubernia(@RequestParam("gubernia") String gubernia, Model model) {
        List<Person> results = personService.findByPersonsByBirthGubernia(gubernia.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, родившихся в губернии: " + gubernia);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    @GetMapping("/search/birth-naseleniyPunct")
    public String searchByBirthNaseleniyPunct(@RequestParam("naseleniyPunct") String naseleniyPunct, Model model) {
        List<Person> results = personService.findByPersonsByBirthNaseleniyPunct(naseleniyPunct.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, родившихся в населенном пункте: " + naseleniyPunct);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    @GetMapping("/search/live-gubernia")
    public String searchByLiveGubernia(@RequestParam("gubernia") String gubernia, Model model) {
        List<Person> results = personService.findByPersonsByLiveGubernia(gubernia.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, проживающих в губернии:" + gubernia);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    @GetMapping("/search/live-naseleniyPunct")
    public String searchByLiveNaseleniyPunct(@RequestParam("naseleniyPunct") String naseleniyPunct, Model model) {
        List<Person> results = personService.findByPersonByLiveNaseleniyPunct(naseleniyPunct.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, проживающих в населенном пункте: " + naseleniyPunct);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    @GetMapping("/search/all-gubernia")
    public String searchByAllGubernia(@RequestParam("gubernia") String gubernia, Model model) {
        List<Person> results = personService.findByAllAddressGubernia(gubernia.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей связанных с губернией: " + gubernia);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    @GetMapping("/search/all-naseleniyPunct")
    public String searchByAllNaseleniyPunct(@RequestParam("naseleniyPunct") String naseleniyPunct, Model model) {
        List<Person> results = personService.findByAllAddressNaseleniyPunct(naseleniyPunct.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, связанных с населенным пунктом: " + naseleniyPunct);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }


}
