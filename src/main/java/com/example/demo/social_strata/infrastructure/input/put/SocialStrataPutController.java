package com.example.demo.social_strata.infrastructure.input.put;

import com.example.demo.shared.domain.bus.command.CommandBus;
import com.example.demo.social_strata.application.command.update.SocialStrataUpdateCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Social Strata (Estamentos)")
public class SocialStrataPutController {
    private final CommandBus commandBus;

    public SocialStrataPutController(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    @PutMapping("/private/social-strata/{id}")
    public ResponseEntity<Void> put(@PathVariable String id, @Valid @RequestBody SocialStrataPutRequest request){

        SocialStrataUpdateCommand command = new SocialStrataUpdateCommand(id, request.name());

        commandBus.dispatch(command);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
