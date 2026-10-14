package com.example.demo.categories.infrastructure.input.put;

import com.example.demo.categories.application.command.update.CategoryUpdateCommand;
import com.example.demo.shared.domain.bus.command.CommandBus;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Categories")
public class CategoryPutController {
    private final CommandBus commandBus;

    public CategoryPutController(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    @PutMapping("/private/categories/{id}")
    public ResponseEntity<Void> put(@PathVariable String id, @RequestBody CategoryPutRequest request){
        CategoryUpdateCommand command = new CategoryUpdateCommand(id, request.socialStrataId(), request.name(),
                                                                  request.carnetName());

        commandBus.dispatch(command);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
