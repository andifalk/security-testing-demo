package com.example.securitytesting.validator;

import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;
import org.springframework.core.io.ClassPathResource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        DictionarySubstringRule dictionaryRule = null;
        try {
            dictionaryRule = new DictionarySubstringRule(
                    new WordListDictionary(WordLists.createFromReader(
                            // Reader around the word list file
                            new FileReader[]{new FileReader(new ClassPathResource("10-million-password-list-top-1000.txt").getFile())},
                            // True for case sensitivity, false otherwise
                            false,
                            // Dictionaries must be sorted
                            new ArraysSort())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                    new LengthRule(12, 128),
                    new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                    new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                    new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
                    new WhitespaceRule(),
                    dictionaryRule));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                String.join(",", validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}