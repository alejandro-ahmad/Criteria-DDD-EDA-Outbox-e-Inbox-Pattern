package com.example.demo.categories.infrastructure.input.post;

import com.example.demo.categories.application.command.create.CategoryCreateCommand;
import com.example.demo.shared.domain.bus.command.CommandBus;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Categories")
public class CategoryPostController {
    private final CommandBus commandBus;

    public CategoryPostController(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    @PostMapping("/private/categories")
    public ResponseEntity<Void> post(@RequestBody CategoryPostRequest request){
        CategoryCreateCommand command = new CategoryCreateCommand(request.socialStrataId(), request.name(),
                                                                  request.carnetName(), request.code());

        commandBus.dispatch(command);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
