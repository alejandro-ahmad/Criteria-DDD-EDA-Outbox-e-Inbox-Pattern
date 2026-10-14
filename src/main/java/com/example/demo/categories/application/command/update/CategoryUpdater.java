package com.example.demo.categories.application.command.update;

import com.example.demo.categories.application.exceptions.CategoryDuplicateException;
import com.example.demo.categories.application.exceptions.CategoryNotFoundException;
import com.example.demo.categories.application.exceptions.SocialStrataNotExistsException;
import com.example.demo.categories.domain.CategoryRepository;
import com.example.demo.categories.domain.SocialStrataExistenceChecker;
import com.example.demo.categories.domain.model.Category;
import com.example.demo.categories.domain.valobj.CategoryCarnetName;
import com.example.demo.categories.domain.valobj.CategoryId;
import com.example.demo.categories.domain.valobj.CategoryName;
import com.example.demo.categories.domain.valobj.SocialStrataId;
import com.example.demo.shared.domain.Service;

@Service
public class CategoryUpdater {
    private final CategoryRepository repository;
    private final SocialStrataExistenceChecker socialStrataExistenceChecker;

    public CategoryUpdater(CategoryRepository repository,SocialStrataExistenceChecker socialStrataExistenceChecker){
        this.repository = repository;
        this.socialStrataExistenceChecker = socialStrataExistenceChecker;
    }

    public void execute(CategoryUpdateCommand command){
        CategoryId id = CategoryId.fromString(command.id());
        CategoryName name = CategoryName.create(command.name());
        SocialStrataId socialStrataId = SocialStrataId.create(command.socialStrataId());
        CategoryCarnetName carnetName = CategoryCarnetName.create(command.carnetName());

        Category category = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("No se encontró una categoria con id: " + command.id()));

        if(repository.existsByNameAndIdNot(name,id)){
            throw new CategoryDuplicateException("Ya existe un registro con el nombre: " + command.name());
        }

        if(!socialStrataExistenceChecker.exists(socialStrataId)){
            throw new SocialStrataNotExistsException(String.format("El estamento con id %s no existe", socialStrataId));
        }

        category.update(socialStrataId,name,carnetName);

        repository.save(category);

    }
}
