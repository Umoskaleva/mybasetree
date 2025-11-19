package com.mybasetree.dto;

import java.util.ArrayList;
import java.util.List;


//Это DTO-класс (Data Transfer Object), который описывает узел дерева
//узел дерева
public class TreeNode {
    private String name;
    private List<TreeNode> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
