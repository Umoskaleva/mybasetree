package com.mybasetree.entity;

//import javax.persistence.*;
import jakarta.persistence.*;
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ссылка на Яндекс.диск
    @Column(name = "photo_url", length = 500) // адрес фото
    private String photoUrl;


    @Column(name = "description", length = 500) // описание фото
    private String description;

    @ManyToOne(fetch = FetchType.LAZY) // связь многие фото к одному Person. Ленивая загрузка, данные загружаются только после обращения к ним
    @JoinColumn(name = "person_id", nullable = false) //фото не может существовать без person
    private Person person;


    public Photo() {

    }

    public Photo(String photoUrl, String description, Person person) {
        this.photoUrl = photoUrl;
        this.description = description;
        this.person = person; //связь с конкретным person
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
