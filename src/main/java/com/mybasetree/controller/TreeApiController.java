package com.mybasetree.controller;


import com.mybasetree.dto.TreeNode;
import com.mybasetree.entity.Person;
import com.mybasetree.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class TreeApiController {

    @Autowired
    private PersonService personService;

    @GetMapping("/tree/{rootId}")
    public ResponseEntity<TreeNode> getFamilyTree(@PathVariable long rootId) {
        Person root = personService.findById(rootId);
        if (root == null) {
            return ResponseEntity.notFound().build();
        }
        TreeNode tree = buildTree(root);
        return ResponseEntity.ok(tree);

    }

    //buildTree выполняет преобразование данных из структуры БД в древовидную структуру для API
    private TreeNode buildTree(Person person) {
        TreeNode node = new TreeNode();
        String name = person.getFirstName();
        if (person.getLastName() != null && !person.getLastName().isEmpty()) {
            name += " " + person.getLastName();
        }
        node.setName(name);

        //Получаем всех детей (HIJO = сын, HIJA = дочь, NINOADOPTIVO = приемный ребенок, HIJAADOPTIVA = приемная дочь)
        List<Person> children = personService.findChildrenOf(person);

        //Рекурсивно стоим поддеревья
        List<TreeNode> childNodes = new ArrayList<>();
        for (Person child : children) {
            childNodes.add(buildTree(child));
        }

        node.setChildren(childNodes);
        return node;
    }



}
