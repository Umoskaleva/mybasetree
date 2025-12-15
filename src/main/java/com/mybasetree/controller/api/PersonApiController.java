package com.mybasetree.controller.api;

import com.mybasetree.entity.Person;
import com.mybasetree.entity.Relationship;
import com.mybasetree.entity.RelationshipRole;
import com.mybasetree.exception.PersonNotFoundException;
import com.mybasetree.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonApiController {
    @Autowired
    private PersonService personService;

    //СОЗДАЕМ персону в базе
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person saved = personService.createPerson(person);
        return ResponseEntity.ok(saved);
    }

    //ОБНОВЛЕНИЕ данных персоны
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDitails) {
            Person updated = personService.updatePerson(id, personDitails);
            return ResponseEntity.ok(updated);
    }


    //создаем связи между персонами Родитель -> Ребенок
    @PostMapping("/{fromId}/relationships")
    public ResponseEntity<String> createRelationship(
            @PathVariable Long fromId,
            @RequestParam Long toId,
            @RequestParam String role) {
        Person from = personService.findById(fromId);
        Person to = personService.findById(toId);

        if (from == null || to == null) {
            return ResponseEntity.notFound().build();
        }
        //получим RelationshipRole MAMA,PAPA,MARIDO,ESPOSA и т д.
        RelationshipRole relationshipRole = RelationshipRole.valueOf(role.toUpperCase());

        //используем сервис для создания связи между людьми
        personService.createRelationship(from, to, relationshipRole);

        return ResponseEntity.ok("Связь создана:" + from.getFirstName() + " -> " + to.getFirstName() + "как" + role);
    }


}
