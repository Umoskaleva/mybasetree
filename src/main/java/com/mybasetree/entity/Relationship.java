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
    @ManyToOne //многие к одному
    @JoinColumn(name = "from_person_id", nullable = false)
    private Person fromPerson;

    //связь К person
    @ManyToOne
    @JoinColumn(name = "to_person_id", nullable = false)
    private Person toPerson;

    //роль fromPerson по отношению к toPerson
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RelationshipRole role;

    //дата окончания связи (смерть, развод)
    @Column(name = "gameOver_Date")
    private LocalDate gameOverDate;

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
                        LocalDate gameOverDate) {
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.role = role;
        this.gameOverDate = gameOverDate;
    }

    public Person getFromPerson() {
        return fromPerson;
    }

    public Person getToPerson() {
        return toPerson;
    }

    public LocalDate getGameOverDate() {
        return gameOverDate;
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

    public void setGameOverDate(LocalDate gameOverDate) {
        this.gameOverDate = gameOverDate;
    }

   public String getRoleName(){
        switch (role){
            case MAMA:return "матерью";
            case PAPA:return "отцом";
            case MARIDO:return "мужем";
            case ESPOSA:return "женой";
            case HIHO:return "сыном";
            case HIJA:return "дочерью";
            case MADRASTA:return "мачехой";
            case PADRASTO:return "отчимом";
            case HIJAADOPTIVA:return "приемной дочерью";
            case NINOADOPTIVO:return "приемным сыном";
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
}
