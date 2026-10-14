package com.example.demo.categories.infrastructure.output.persistence;

import com.example.demo.categories.domain.model.Category;
import com.example.demo.categories.domain.valobj.CategoryCarnetName;
import com.example.demo.categories.domain.valobj.CategoryId;
import com.example.demo.categories.domain.valobj.CategoryName;
import com.example.demo.categories.domain.valobj.SocialStrataId;
import org.springframework.stereotype.Component;

@Component
public class CategoryPersistenceMapper {
    public Category toDomain(CategoryEntity entity){
        if(entity == null){
            return null;
        }

        return new Category(
                entity.getId() != null ? CategoryId.fromString(entity.getId()) : null,
                entity.getSocialStrataId() != null ? SocialStrataId.fromPersistence(entity.getSocialStrataId()) : null,
                entity.getName() != null ? CategoryName.fromPersistence(entity.getName()) : null,
                entity.getCarnetName() != null ? CategoryCarnetName.fromPersistence(entity.getCarnetName()) : null,
                entity.getCode() != null ? entity.getCode() : null
        );
    }

    public CategoryEntity toEntity(Category domain){
        if(domain == null){
            return null;
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setId(domain.id() != null ? domain.id().value().toString() : null);
        entity.setSocialStrataId(domain.socialStrataId()
                                                != null ? domain.socialStrataId().value() : null);
        entity.setName(domain.name() != null ? domain.name().value() : null);
        entity.setCarnetName(domain.carnetName() != null ? domain.carnetName().value()
                                                                          : null);
        entity.setCode(domain.code() != null ? domain.code() : null);

        return entity;
    }
}
