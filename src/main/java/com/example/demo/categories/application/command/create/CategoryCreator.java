package com.example.demo.categories.application.command.create;

import com.example.demo.categories.application.exceptions.CategoryDuplicateException;
import com.example.demo.categories.application.exceptions.SocialStrataNotExistsException;
import com.example.demo.categories.domain.CategoryRepository;
import com.example.demo.categories.domain.SocialStrataExistenceChecker;
import com.example.demo.categories.domain.model.Category;
import com.example.demo.categories.domain.valobj.CategoryCarnetName;
import com.example.demo.categories.domain.valobj.CategoryName;
import com.example.demo.categories.domain.valobj.SocialStrataId;
import com.example.demo.shared.domain.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryCreator {
    private final CategoryRepository repository;
    private final SocialStrataExistenceChecker socialStrataExistenceChecker;

    public CategoryCreator(CategoryRepository repository,SocialStrataExistenceChecker socialStrataExistenceChecker){
        this.repository = repository;
        this.socialStrataExistenceChecker = socialStrataExistenceChecker;
    }

    @Transactional
    public void execute(CategoryCreateCommand command){
         SocialStrataId socialStrataId= SocialStrataId.create(command.socialStrataId());
         CategoryName name= CategoryName.create(command.name());
         CategoryCarnetName carnetName= CategoryCarnetName.create(command.carnetName());


        if(repository.existsByName(name)){
            throw new CategoryDuplicateException("Ya existe un registro con el nombre: " + command.name());
        }

        if(!socialStrataExistenceChecker.exists(SocialStrataId.create(command.socialStrataId()))){
            throw new SocialStrataNotExistsException(String.format("El estamento con id %s no existe", command.socialStrataId()));
        }

        if(repository.existsByCode(command.code())){
            throw new CategoryDuplicateException("Ya existe un registro con el codigo: " + command.code());
        }

        Category category = Category.create(
                socialStrataId,
                name,
                carnetName,
                command.code()
        );

        repository.save(category);
    }
}
