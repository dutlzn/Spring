package com.demo.generic;

public class GenericClassExample<T> {
    private T member;
    public GenericClassExample(T member){
        this.member = member;
    }

    public T getMember() {
        return member;
    }

    public void setMember(T member) {
        this.member = member;
    }

    public T handleSomething(T target) {
        return target;
    }

    public String sayHello(String name){
        return "Hello" + name;
    }


}
