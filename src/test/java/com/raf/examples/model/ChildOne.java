package com.raf.examples.model;

public class ChildOne extends Parent {

    private String childOneProperty;

    public ChildOne() {
    }

    public ChildOne(String name, String description, String childOneProperty) {
        super(name, description);
        this.childOneProperty = childOneProperty;
    }

    public String getChildOneProperty() {
        return childOneProperty;
    }

    public void setChildOneProperty(String childOneProperty) {
        this.childOneProperty = childOneProperty;
    }
}
