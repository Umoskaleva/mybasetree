package com.mybasetree.controller.web;

import com.mybasetree.controller.dto.TreeNode;
import com.mybasetree.entity.Person;
import com.mybasetree.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


//шаблоны:
//tree
@Controller
@RequestMapping("/tree")
public class TreeWebController {

    @Autowired
    private PersonService personService;

    //показать визуальное дерево для конкретного человека
    @GetMapping("/{rootId}")
    public String showVisualTree(@PathVariable Long rootId, Model model){
        Person rootPerson = personService.findById(rootId);
        if(rootPerson == null){
            return "error";
        }
        TreeNode tree = buildTree(rootPerson);
        model.addAttribute("rootPerson", rootPerson);
        model.addAttribute("treeNode", tree);
        model.addAttribute("rootId", rootId);
        return "tree"; //имя шаблона tree.html
    }

    //показать страницу выбора корневого узла для дерева
    @GetMapping("/tree-selector")
    public String showTreeSelector(Model model){
        //получаем всех людей для выбора корневого узла
        model.addAttribute("persons",personService.findAll());
        return "tree-selector"; // шаблон для выбора корневого узла
    }

    //показать визуальное дерево для конкретного человека
    @GetMapping("/tree")
    public String showTree(Model model){
        model.addAttribute("persons", personService.findAll());
        return "tree-selector";
    }


    //метод построения дерева (как в TreeApiController)
    private TreeNode buildTree(Person person) {
        TreeNode node = new TreeNode();
        String name = person.getFirstName();
        if (person.getLastName() != null && !person.getLastName().isEmpty()) {
            name += " " + person.getLastName();
        }
        node.setName(name);
        //добавляем ID для ссылок в веб-интерфейсе
        node.setId(person.getId());
        //Получаем всех детей (MAMA = мама, PAPA = папа, PADRASTO = отчим, MADRASTA = мачеха)
        List<Person> children = personService.findChildrenOf(person);
        //Рекурсивно стоим поддеревья
        List<TreeNode> childNodes = new ArrayList<>();
        for (Person child : children) {
            childNodes.add(buildTree(child));
        }
        node.setChildren(childNodes);
        return node;
    }

//    //показать дерево семьи в виде дерева узлов
//    @GetMapping("/tree")
//    public String showTree(@RequestParam(required = false) Long rootId, Model model){
//        model.addAttribute("rootId", rootId != null ? rootId : 1);
//        return "tree"; //шаблон tree.html
//    }



}
