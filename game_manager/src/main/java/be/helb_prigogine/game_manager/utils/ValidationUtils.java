package be.helb_prigogine.game_manager.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ValidationUtils {
    //This classs ic created because both controller use this function. Problem is that we don't want duplication, so we don't write it in both
    //And we don't want that a controller has a dependency to another controller
    public String getErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse("Validation failed");
    }
}
