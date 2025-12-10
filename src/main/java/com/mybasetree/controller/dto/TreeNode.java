package com.mybasetree.controller.dto;

import java.util.ArrayList;
import java.util.List;


//Это DTO-класс (Data Transfer Object), который описывает узел дерева
//узел дерева
public class TreeNode {
    private String name;
    private List<TreeNode> children = new ArrayList<>();
    private List<TreeNode> partners = new ArrayList<>();
    private Long id;

    public String getName() {
        return name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public Long getId() {
        return id;
    }

    public List<TreeNode> getPartners() {
        return partners;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPartners(List<TreeNode> partners) {
        this.partners = partners;
    }
}
