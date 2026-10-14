package com.example.demo.categories.domain.model;

import com.example.demo.categories.domain.valobj.CategoryCarnetName;
import com.example.demo.categories.domain.valobj.CategoryId;
import com.example.demo.categories.domain.valobj.CategoryName;
import com.example.demo.categories.domain.valobj.SocialStrataId;

public class Category {
    private final CategoryId   id;
    private SocialStrataId socialStrataId;
    private       CategoryName       name;
    private       CategoryCarnetName carnetName;
    private final Integer            code;

    public Category(CategoryId id,SocialStrataId socialStrataId ,CategoryName name,CategoryCarnetName carnetName, Integer code){
        this.id = id;
        this.socialStrataId = socialStrataId;
        this.name = name;
        this.carnetName = carnetName;
        this.code = code;
    }

    public static Category create(SocialStrataId socialStrataId, CategoryName name, CategoryCarnetName carnetName, Integer code){
        return new Category(CategoryId.generate(),socialStrataId,name,carnetName,code);
    }

    public void update(SocialStrataId socialStrataId ,CategoryName name,CategoryCarnetName carnetName){
        this.socialStrataId = socialStrataId;
        this.name = name;
        this.carnetName = carnetName;
    }

    public CategoryId id(){
        return id;
    }

    public SocialStrataId socialStrataId() {
        return socialStrataId;
    }

    public CategoryName name(){
        return name;
    }

    public CategoryCarnetName carnetName(){
        return carnetName;
    }

    public Integer code(){
        return code;
    }
}
