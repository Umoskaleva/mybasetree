package com.mybasetree.service;

import com.mybasetree.entity.Person;
import com.mybasetree.entity.Relationship;
import com.mybasetree.entity.RelationshipRole;
import com.mybasetree.exception.PersonNotFoundException;
import com.mybasetree.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


//@Service - Содержит всю бизнес-логику приложения. Это "мозги" операции.
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }



    //создаем персону и проверяем на дубликаты
    public Person createPerson(Person person) {
        boolean exists = personRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                person.getFirstName(),
                person.getLastName(),
                person.getDateOfBirth());
        if (exists) {
            throw new IllegalArgumentException("Человек с таким именем, фамилией и датой рождения уже существует!");
        }
        return personRepository.save(person);
    }

    //создаем связи между людьми
    //Связь добавляется к списку связей 'fromPerson'. Сохраняется в базе данных
    //@param fromPerson Человек, который является "от кого" в связи (напримре, родитель)
    //@param toPerson Человек, который "к кому" в связи (например, ребенок)
    //@param role Роль 'fromPerson' по отношению 'toPerson' (мама, папа)
    public void createRelationship(Person fromPerson, Person toPerson, RelationshipRole role) {
        //создаем новый обьект Relationship
        Relationship relationship = new Relationship();
        relationship.setFromPerson(fromPerson);
        relationship.setToPerson(toPerson);
        relationship.setRole(role);
        fromPerson.getRelationships().add(relationship);

        //Сохраним 'fromPerson' благодаря cascade = CascadeType.ALL
        //новая связь будет в базе данных
        save(fromPerson);
    }


    //сохраним(обновим) персону без проверки на дубликаты
    public Person save(Person person) {
        return personRepository.save(person);
    }

    //обновление данных о персоне
    public Person updatePerson (Long personId, Person updatePerson){
        Person existingPerson = personRepository.findById(personId)
                        .orElseThrow(()-> new PersonNotFoundException("Не найден человек с идентификатором: " + personId));
        existingPerson.setFirstName(updatePerson.getFirstName());
        existingPerson.setLastName(updatePerson.getLastName());
        existingPerson.setTwoLastName(updatePerson.getTwoLastName());
        existingPerson.setMaidenName(updatePerson.getMaidenName());
        existingPerson.setSurName(updatePerson.getSurName());
        existingPerson.setNickName(updatePerson.getNickName());
        existingPerson.setGender(updatePerson.getGender());
        existingPerson.setDateOfBirth(updatePerson.getDateOfBirth());
        existingPerson.setDateOfMarriage(updatePerson.getDateOfMarriage());
        existingPerson.setDateOfDivorce(updatePerson.getDateOfDivorce());
        existingPerson.setDateOfDeath(updatePerson.getDateOfDeath());
        existingPerson.setAddresses(updatePerson.getAddresses());
//        existingPerson.setPhotos(updatePerson.getPhotos());
        existingPerson.setMainPhotoUrl(updatePerson.getMainPhotoUrl());
        existingPerson.setInterestingFact(updatePerson.getInterestingFact());

        return personRepository.save(existingPerson);
    }


    //обновление только интересного факта
    public Person updateInterestingFact(Long personId, String interestingFact) {
        Person person = personRepository.findById(personId)
                .orElseThrow(()-> new PersonNotFoundException("Не найден человек с идентификатором: " + personId));
        person.setInterestingFact(interestingFact);
        return personRepository.save(person);

    }

    //получение человека по ID
    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Не найден человек с идентификатором " + id));
    }

    //найти все персоны
    public List<Person> findAll() {
        return personRepository.findAll();
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

    //метод возвращает жен (ESPOSA) и мужей (MARIDO) человека
    public List<Person> findPartnersOf(Person partner){
        return partner.getRelationships().stream()
                .filter(relationship -> (
                        relationship.getRole() == RelationshipRole.ESPOSA ||
                                relationship.getRole() == RelationshipRole.MARIDO))
                .map(Relationship::getToPerson)
                .collect(Collectors.toList());
    }

    //метод для поиска роли для человека
    public List<Person> findRelatedOf(Person person, Set<RelationshipRole> roles) {
        return person.getRelationships().stream().filter(r -> roles.contains(r.getRole()))
                .map(Relationship::getToPerson)
                .collect(Collectors.toList());
    }

    //методы для работы с текстовым описанием человека
    public String getInterestingFactForPerson(Long personId) {
        Person person = findById(personId);//проверяем существование входящего personId
        return person.getInterestingFact();
    }

    public String updateInterestingFactForPerson(Long personId, String newFact) {
        Person person = findById(personId); //проверяем существование в базе данного id
        person.setInterestingFact(newFact); //устанавливаем новый факт
        personRepository.save(person);//сохраняем изменения
        return "Текст добавлен/отредактирован для " + personId + " " + person.getFirstName();
    }

   public void deletePerson(Long personId){
       //проверяем существует ли сущность с записью personID в базе данных
        if(!personRepository.existsById(personId)){
            throw new PersonNotFoundException("Не найден человек с идентификатором" + personId);
        }
        personRepository.deleteById(personId);
        System.out.println("Запись удалена ! " + personId);;
   }



}
