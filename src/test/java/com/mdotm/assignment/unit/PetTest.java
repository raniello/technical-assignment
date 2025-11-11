package com.mdotm.assignment.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import com.mdotm.assignment.domain.Pet;
import com.mdotm.assignment.domain.exception.PetValidationException;


public class PetTest {
    @Test
    public void cannotCreateWithNullName(){
        assertThrows(PetValidationException.class, () -> Pet.create(null, "Cat", 10));
    }

    @Test
    public void cannotCreateWithBlankName(){
        assertThrows(PetValidationException.class, () -> Pet.create(" ", "Cat", 10));
    }

    @Test
    public void cannotCreateWithNullSpecies(){
        assertThrows(PetValidationException.class, () -> Pet.create("Greg", null, 10));
    }

    @Test
    public void cannotCreateWithBlankSpecies(){
        assertThrows(PetValidationException.class, () -> Pet.create("Greg", " ", 10));
    }

    @Test
    public void cannotCreateWithNullAge(){
        assertThrows(PetValidationException.class, () -> Pet.create("Greg", "dog", null));
    }

    @Test
    public void cannotCreateWithNegativeAge(){
        assertThrows(PetValidationException.class, () -> Pet.create("Greg", "dog", -1));
    }

    @Test
    public void cannotAssignNullName(){
        var pet = Pet.create("Greg", "dog", 10);
        assertThrows(PetValidationException.class, () -> pet.rename(null));
    }

    @Test
    public void cannotAssignBlankName(){
        var pet = Pet.create("Greg", "dog", 10);
        assertThrows(PetValidationException.class, () -> pet.rename("  "));
    }

    @Test
    public void cannotAssignNullSpecies(){
        var pet = Pet.create("Greg", "dog", 10);
        assertThrows(PetValidationException.class, () -> pet.changeSpecies(null));
    }

    @Test
    public void cannotAssignBlankSpecies(){
        var pet = Pet.create("Greg", "dog", 10);
        assertThrows(PetValidationException.class, () -> pet.changeSpecies("  "));
    }

    @Test
    public void cannotAssignNullAge(){
        var pet = Pet.create("Greg", "dog", 10);
        assertThrows(PetValidationException.class, () -> pet.updateAge(null));
    }

    @Test
    public void cannotAssignNegativeAge(){
        var pet = Pet.create("Greg", "dog", 10);
        assertThrows(PetValidationException.class, () -> pet.updateAge(-1));
    }
}
