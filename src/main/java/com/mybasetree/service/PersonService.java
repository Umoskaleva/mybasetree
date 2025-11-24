package com.mybasetree.service;

import com.mybasetree.entity.Person;
import com.mybasetree.entity.Relationship;
import com.mybasetree.entity.RelationshipRole;
import com.mybasetree.exception.PersonNotFoundException;
import com.mybasetree.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


//@Service - Содержит всю бизнес-логику приложения. Это "мозги" операции.
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(Person person) { // создаем персону
        boolean exists = personRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                person.getFirstName(),
                person.getLastName(),
                person.getDateOfBirth());
        if (exists) {
            throw new IllegalArgumentException("Человек с таким именем, фамилией и датой рождения уже существует!");
        }
        return personRepository.save(person);
    }

    //найти все персоны
    public List<Person> findAll() { //найти все персоны
        return personRepository.findAll();
    }

    //поиск по ID
    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Не найден человек с идентификатором " + id));
    }

    //проверка существования по ID
    public boolean existsByID(Long id) {
        return personRepository.existsById(id);
    }

    //поиск по имени,
    // по фамилии,
    // по имени и фамилии
    public List<Person> findByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    public List<Person> findByLastName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    public List<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        return personRepository.findByFirstNameAndLastName(firstName, lastName);
    }


    //поиск в АДРЕСЕ РОЖДЕНИЯ:
    ////поиск в адресе рождения по губернии
    public List<Person> findByPersonsByBirthGubernia(String gubernia) {
        return personRepository.findByPersonsByBirthGubernia(gubernia);
    }

    ////поиск в адресе рождения по населенному пункту
    public List<Person> findByPersonsByBirthNaseleniyPunct(String naseleniyPunct) {
        return personRepository.findByPersonsByBirthNaseleniyPunct(naseleniyPunct);
    }

    //поиск в АДРЕСЕ ПРОЖИВАНИЯ:
    ////поиск в адресе проживания по губернии
    public List<Person> findByPersonsByLiveGubernia(String gubernia) {
        return personRepository.findByPersonsByLiveGubernia(gubernia);
    }

    ////поиск в адресе проживания по населенному пункту
    public List<Person> findByPersonByLiveNaseleniyPunct(String naseleniyPunct) {
        return personRepository.findByPersonByLiveNaseleniyPunct(naseleniyPunct);
    }

    //поиск по ЛЮБОМУ АДРЕСУ (рождения, проживания и т д.)
    public List<Person> findByAllAddressGubernia(String gubernia) {
        return personRepository.findByAllAddressGubernia(gubernia);
    }

    public List<Person> findByAllAddressNaseleniyPunct(String naseleniyPunct) {
        return personRepository.findByAllAddressNaseleniyPunct(naseleniyPunct);
    }


    //родившиеся ДО даты
    public List<Person> findByDateOfBirthBefore(LocalDate date) {
        return personRepository.findByDateOfBirthBefore(date);
    }

    //родившиеся ПОСЛЕ даты
    public List<Person> findByDateOfBirthAfter(LocalDate date) {
        return personRepository.findByDateOfBirthAfter(date);
    }


    //метод возвращает всех детей человека: дочка, сын, приемный сын, приемная дочка
    public List<Person> findChildrenOf(Person parent) {
        return parent.getRelationships().stream()
                .filter(relationship -> (
                        relationship.getRole() == RelationshipRole.PAPA ||
                                relationship.getRole() == RelationshipRole.MAMA ||
                                relationship.getRole() == RelationshipRole.PADRASTO ||
                                relationship.getRole() == RelationshipRole.MADRASTA))
                .map(Relationship::getToPerson)
                .collect(Collectors.toList());
    }

    //методы для работы с текстовым описанием человека
    public String getInterestingFactForPerson(Long personId){
        Person person = findById(personId);//проверяем существование входящего personId
        return person.getInterestingFact();
    }

    public String updateInterestingFactForPerson(Long personId, String newFact){
        Person person = findById(personId); //проверяем существование в базе данного id
        person.setInterestingFact(newFact); //устанавливаем новый факт
        personRepository.save(person);//сохраняем изменения
        return "Текст добавлен/отредактирован для " + personId + " " + person.getFirstName();
    }
}
