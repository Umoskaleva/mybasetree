package com.mybasetree.entity;

//import javax.persistence.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "relationship")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //связь ОТ person
    //fromPerson - родитель
    @ManyToOne //многие к одному
    @JoinColumn(name = "from_person_id", nullable = false)
    private Person fromPerson;

    //связь К person
    //toPerson - ребенок
    @ManyToOne
    @JoinColumn(name = "to_person_id", nullable = false)
    private Person toPerson;

    //роль fromPerson по отношению к toPerson
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RelationshipRole role;

    //дата начала связи (брак, рождение, усыновление)
    @Column(name = "startDate")
    private LocalDate startDate;

    //дата окончания связи (смерть, развод)
    @Column(name = "gameOver_Date")
    private LocalDate gameOverDate;

    //текстовое примечание
    @Column(name = "notes")
    private String notes;

    public Relationship() {

    }

    public Relationship(Person fromPerson,
                        Person toPerson,
                        RelationshipRole role) {
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.role = role;
    }

    public Relationship(Person fromPerson,
                        Person toPerson,
                        RelationshipRole role,
                        LocalDate startDate,
                        LocalDate gameOverDate, String notes){
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.role = role;
        this.startDate = startDate;
        this.gameOverDate = gameOverDate;
        this.notes = notes;
    }

    public Relationship(Person fromPerson,
                        Person toPerson,
                        RelationshipRole role,
                        LocalDate gameOverDate) {
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.role = role;
        this.gameOverDate = gameOverDate;
    }

    // родитель
    public Person getFromPerson() {
        return fromPerson;
    }

    //ребенок
    public Person getToPerson() {
        return toPerson;
    }

    public LocalDate getGameOverDate() {
        return gameOverDate;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setFromPerson(Person fromPerson) {
        this.fromPerson = fromPerson;
    }

    public void setToPerson(Person toPerson) {
        this.toPerson = toPerson;
    }

    public void setRole(RelationshipRole role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setGameOverDate(LocalDate gameOverDate) {
        this.gameOverDate = gameOverDate;
    }

   public String getRoleName(){
        switch (role){
            case MAMA:return "матерью";
            case PAPA:return "отцом";
            case MARIDO:return "мужем";
            case ESPOSA:return "женой";
            case HIJO:return "сыном";
            case HIJA:return "дочерью";
            case MADRASTA:return "мачехой";
            case PADRASTO:return "отчимом";
            case HIJAADOPTIVA:return "приемной дочерью";
            case NINOADOPTIVO:return "приемным ребенком";
            case HERMANO:return "братом";
            case HERMANA:return "сестрой";
            case HERMANASTRO:return "свобным братом";
            case HERMANASTRA:return "сводной сестрой";
            case ABUELA:return "бабушкой";
            case ABUELO:return "дедушкой";
            case NIETA:return "внучкой";
            case NIETO:return "внуком";
            case MADRINA:return "крестной";
            case PADRINO:return "крестным";
            case TIA:return "тетей";
            case TIO:return "дядей";
            default:return role.name().toLowerCase(); //если добавили новую роль в RelationshipRole, но забыли добавить case в метод, отдаст значение
        }
   }

    public RelationshipRole getRole() {
        return role;
    }
}
