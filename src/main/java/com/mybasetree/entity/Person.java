package com.mybasetree.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // будет 1,2,3 ...

    @Column(name = "first_name", nullable = false) //имя
    private String firstName;

    @Column(name = "last_name") //фамилия
    private String lastName;

    @Column(name = "two_last_name") // вторая фамилия после смены, 2ого замужества
    private String twoLastName;

    @Column(name = "maiden_name") // девичья фамилия, может быть пустым
    private String maidenName;

    @Column(name = "sur_name") // отчество, может быть пустым
    private String surName;

    @Column(name = "nick_name") // прозвище, может быть пустым
    private String nickName;

    @Enumerated(EnumType.STRING) // храним пол как строку в БД мужской, женский, другой (MALE/FEMALE/OTHER)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth; //дата рождения
    @Column(name = "date_of_marriage")
    private LocalDate dateOfMarriage;//дата брака
    @Column(name = "date_of_divorce")
    private LocalDate dateOfDivorce;//дата развода
    @Column(name = "date_of_death")
    private LocalDate dateOfDeath; //дата смерти


    //список адресов каждой person
    @ElementCollection
    @CollectionTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "person_id")
    )
    private List<Address> addresses = new ArrayList<>();

    @Column(name = "interesting_fact", columnDefinition = "TEXT") // TEXT для длинного текста
    private String interestingFact;


    @OneToMany(mappedBy = "fromPerson", cascade = {CascadeType.ALL})
    // все варианты каскадных операций,в т.ч. удаление связей при удалении человека
    private List<Relationship> relationships = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    @Column(name = "main_photo_url", length = 500)
    private String mainPhotoUrl;


    public Person() {

    }

    //на входе имя, фамилия
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //конструктор включает все поля Person
    public Person(String firstName, String lastName, String twoLastName, String maidenName, String surName, String nickName, Gender gender, LocalDate dateOfBirth, LocalDate dateOfMarriage, LocalDate dateOfDivorce, LocalDate dateOfDeath, List<Photo> photos, String mainPhotoUrl, String interestingFact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.twoLastName = twoLastName;
        this.maidenName = maidenName;
        this.surName = surName;
        this.nickName = nickName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateOfMarriage = dateOfMarriage;
        this.dateOfDivorce = dateOfDivorce;
        this.dateOfDeath = dateOfDeath;
        this.photos = photos;
        this.mainPhotoUrl = mainPhotoUrl;
        this.interestingFact = interestingFact;
    }

    //конструктор без поля interestingFact для обратной совместимости
    public Person(String firstName, String lastName, String twoLastName, String maidenName, String surName, String nickName, Gender gender, LocalDate dateOfBirth, LocalDate dateOfMarriage, LocalDate dateOfDivorce, LocalDate dateOfDeath, List<Photo> photos, String mainPhotoUrl){
        this(firstName, lastName, twoLastName, maidenName, surName, nickName, gender, dateOfBirth, dateOfMarriage, dateOfDivorce, dateOfDeath, photos, mainPhotoUrl, null);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTwoLastName() {
        return twoLastName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public String getSurName() {
        return surName;
    }

    public String getNickName() {
        return nickName;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getDateOfMarriage() {
        return dateOfMarriage;
    }

    public LocalDate getDateOfDivorce() {
        return dateOfDivorce;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public String getInterestingFact() {
        return interestingFact;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTwoLastName(String twoLastName) {
        this.twoLastName = twoLastName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfMarriage(LocalDate dateOfMarriage) {
        this.dateOfMarriage = dateOfMarriage;
    }

    public void setDateOfDivorce(LocalDate dateOfDivorce) {
        this.dateOfDivorce = dateOfDivorce;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public void setInterestingFact(String interestingFact) {
        this.interestingFact = interestingFact;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }


    //методы для работы с адресами

    public Address getBirthAddress() { // вернет адрес рождения person
        return addresses.stream().filter(addr -> addr.getAddressType() == AddressType.BIRTH)
                .findFirst().orElse(null);
    }

    public Address getLifeAddress() {  // вернет адрес проживания person
        return addresses.stream().filter(addr -> addr.getAddressType() == AddressType.LIVEONE)
                .findFirst().orElse(null);
    }

    public Address getLiveAddressTwo() {
        return addresses.stream().filter(adrr -> adrr.getAddressType() == AddressType.LIVETWO)
                .findFirst().orElse(null);
    }


    public Address getLiveAddressThree(){
        return addresses.stream().filter(addr -> addr.getAddressType() == AddressType.LIVETHREE)
                .findFirst().orElse(null);
    }

    public Address getWorkAddressOne(){
        return addresses.stream().filter(addr -> addr.getAddressType() == AddressType.WORKONE)
                .findFirst().orElse(null);
    }

    public Address getWorkAddressTwo(){
        return addresses.stream().filter(addr -> addr.getAddressType() == AddressType.WORKTWO)
                .findFirst().orElse(null);
    }

    public Address getWorkAddressThree(){
        return addresses.stream().filter(addr -> addr.getAddressType() == AddressType.WORKTHREE)
                .findFirst().orElse(null);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("имя: '").append(firstName).append('\'')
                .append(", фамилия: '").append(lastName).append('\'');

        Address birthAddress = getBirthAddress();
        Address lifeAddress = getLifeAddress();

        if (birthAddress != null && lifeAddress != null) {
            sb.append(", адрес рождения: ").append(birthAddress)
                    .append(", адрес проживания: ").append(lifeAddress);
        } else if (birthAddress != null) {
            sb.append(", адрес рождения: ").append(birthAddress);
        } else if (lifeAddress != null) {
            sb.append(", адрес проживания: ").append(lifeAddress);
        }
        // если оба адреса null - выводим только имя и фамилию

        return sb.toString();
    }

}
