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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    @GetMapping("/selector")
    public String showTree(Model model){
        model.addAttribute("persons", personService.findAll());
        return "tree-selector";
    }


    private TreeNode buildTree(Person person) {
        TreeNode node = new TreeNode();
        List<String> namePart = new ArrayList<>();
        if (person.getLastName() != null && !person.getLastName().isEmpty()) {
            namePart.add(person.getLastName());
        } if (person.getFirstName() != null && !person.getFirstName().isEmpty()){
            namePart.add(person.getFirstName());
        } if (person.getSurName() != null && !person.getSurName().isEmpty()){
            namePart.add(person.getSurName());
        }
        //собираем все части ФИО
        String allParts = String.join(" ", namePart);
        node.setName(allParts);
        //добавляем ID для ссылок в веб-интерфейсе
        node.setId(person.getId());

        //Получаем всех детей (MAMA = мама, PAPA = папа, PADRASTO = отчим, MADRASTA = мачеха)
        List<Person> children = personService.findChildrenOf(person);
        //Рекурсивно стоим поддеревья
        List<TreeNode> childNodes = new ArrayList<>();
        for (Person child : children) {
            childNodes.add(buildTree(child));
        }

        //получаем всех жен (ESPOSA = жена, MARIDO = муж)
        List<Person> partners = personService.findPartnersOf(person);
        List<TreeNode> partnersNode = new ArrayList<>();
        for (Person partner : partners) {
            partnersNode.add(buildTree(partner));
        }

        node.setChildren(childNodes);
        node.setPartners(partnersNode);
        return node;
    }

}
