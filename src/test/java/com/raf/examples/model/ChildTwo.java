package com.raf.examples.model;

public class ChildTwo extends Parent {

    private String childTwoProperty;

    public ChildTwo() {

    }

    public ChildTwo(String name, String description, String childTwoProperty) {
        super(name, description);
        this.childTwoProperty = childTwoProperty;
    }

    public String getChildTwoProperty() {
        return childTwoProperty;
    }

    public void setChildTwoProperty(String childTwoProperty) {
        this.childTwoProperty = childTwoProperty;
    }
}
