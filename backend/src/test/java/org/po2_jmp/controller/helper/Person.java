package org.po2_jmp.controller.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Person {

    private String name;
    private int age;

    @JsonCreator
    public Person(
            @JsonProperty("name") String name,
            @JsonProperty("age") int age) {
        this.name = name;
        this.age = age;
    }

}
