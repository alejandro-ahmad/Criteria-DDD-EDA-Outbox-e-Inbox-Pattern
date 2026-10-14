package com.example.demo.social_strata.infrastructure.input.post;

import com.example.demo.shared.domain.bus.command.CommandBus;
import com.example.demo.social_strata.application.command.create.SocialStrataCreateCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@Tag(name = "Social Strata (Estamentos)")
public class SocialStrataPostController {
    private final CommandBus commandBus;

    public SocialStrataPostController(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    @PostMapping("private/social-strata")
    public ResponseEntity<Void> create(@Valid @RequestBody SocialStrataPostRequest request){

        SocialStrataCreateCommand command = new SocialStrataCreateCommand(request.name());

        commandBus.dispatch(command);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
