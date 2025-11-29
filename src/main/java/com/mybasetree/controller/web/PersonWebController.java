package com.mybasetree.controller.web;

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

    //показать дерево семьи в виде списка
    @GetMapping("/family-tree")
    public String showFamilyTree(Model model) {
        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);
        return "family-tree"; // имя HTML шаблона
    }

    //показать детально данные о персоне
    @GetMapping("/person/{id}")
    public String showPersonDetails(@PathVariable Long id, Model model) {
        Person person = personService.findById(id);
        if (person == null) {
            return "error"; // шаблон error.html
        }
        model.addAttribute("person", person);
        return "person-details";// имя HTML шаблона
    }

    //показать форму поиска
    @GetMapping("/search-form")
    public String showSearchForm(){
        return "search-form";
    }

    //поиск по имени и фамилии
    @GetMapping("/search")
    public String searchByFirstNameAndLastName(@RequestParam String firstName,
                                               @RequestParam String lastName,
                                               Model model){
        List<Person> persons = personService.findByFirstNameAndLastName(firstName, lastName);
        model.addAttribute("persons", persons);
        if(persons.isEmpty()){
            model.addAttribute("errorMessage", "Ничего не найдено по запросу: " + firstName + " " + lastName);
        }
        return "family-tree";//возвращаем на шаблон family-tree со списком людей
    }

    //показать людей родившихся до ДАТЫ
    @GetMapping("/search/before")
    public String searchBeforeDate(@RequestParam("date") LocalDate date, Model model) {
        List<Person> results = personService.findByDateOfBirthBefore(date);
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, родившихся до " + date);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    //показать людей родившихся после ДАТЫ
    @GetMapping("/search/after")
    public String searchAfterDate(@RequestParam("date") LocalDate date, Model model) {
        List<Person> results = personService.findByDateOfBirthAfter(date);
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, родившихся после " + date);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    //показать людей родившихся в ГУБЕРНИИ
    @GetMapping("/search/birth-gubernia")
    public String searchByBirthGubernia(@RequestParam("gubernia") String gubernia, Model model) {
        List<Person> results = personService.findByPersonsByBirthGubernia(gubernia.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, родившихся в губернии: " + gubernia);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    //показать людей родившихся в НАСЕЛЕННОМ ПУНКТЕ
    @GetMapping("/search/birth-naseleniyPunct")
    public String searchByBirthNaseleniyPunct(@RequestParam("naseleniyPunct") String naseleniyPunct, Model model) {
        List<Person> results = personService.findByPersonsByBirthNaseleniyPunct(naseleniyPunct.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, родившихся в населенном пункте: " + naseleniyPunct);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    //показать людей живших в ГУБЕРНИИ
    @GetMapping("/search/live-gubernia")
    public String searchByLiveGubernia(@RequestParam("gubernia") String gubernia, Model model) {
        List<Person> results = personService.findByPersonsByLiveGubernia(gubernia.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, проживающих в губернии:" + gubernia);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    //показать людей живших в НАСЕЛЕННОМ ПУНКТЕ
    @GetMapping("/search/live-naseleniyPunct")
    public String searchByLiveNaseleniyPunct(@RequestParam("naseleniyPunct") String naseleniyPunct, Model model) {
        List<Person> results = personService.findByPersonByLiveNaseleniyPunct(naseleniyPunct.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, проживающих в населенном пункте: " + naseleniyPunct);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    //показать всех людей в ГУБЕРНИИ
    @GetMapping("/search/all-gubernia")
    public String searchByAllGubernia(@RequestParam("gubernia") String gubernia, Model model) {
        List<Person> results = personService.findByAllAddressGubernia(gubernia.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей связанных с губернией: " + gubernia);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    //показать всех людей в НАСЕЛЕННОМ ПУНКТЕ
    @GetMapping("/search/all-naseleniyPunct")
    public String searchByAllNaseleniyPunct(@RequestParam("naseleniyPunct") String naseleniyPunct, Model model) {
        List<Person> results = personService.findByAllAddressNaseleniyPunct(naseleniyPunct.trim());
        if (results.isEmpty()) {
            throw new PersonNotFoundException("Нет людей, связанных с населенным пунктом: " + naseleniyPunct);
        }
        model.addAttribute("persons", results);
        return "family-tree";
    }

    //показать подробную инфу о человеке
    @GetMapping("/person/{id}/update-fact") // POST-эндпоинт для обновления факта
    public String updateFact(@PathVariable long id, @RequestParam String fact, Model model) {
        //вызываем сервис для обновления
        String message = personService.updateInterestingFactForPerson(id, fact);
        System.out.println(message);//логирование результата
        //перенаправляем обратно на страницу деталей человека person-details.html
        return "redirect:/person/{id}";
    }


}
