package nl.tudelft.oopp.prod.entities;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

public class EntitesGettersSettersTest {

    @Test
    public void testPojoStructureAndBehavior() {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        validator.validate(PojoClassFactory.getPojoClass(Question.class));
        validator.validate(PojoClassFactory.getPojoClass(User.class));
        validator.validate(PojoClassFactory.getPojoClass(Poll.class));
        validator.validate(PojoClassFactory.getPojoClass(Lecture.class));
        validator.validate(PojoClassFactory.getPojoClass(PollOption.class));
    }
}