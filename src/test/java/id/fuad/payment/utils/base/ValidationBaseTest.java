package id.fuad.payment.utils.base;

import id.fuad.payment.config.constant.IssueConstant;
import id.fuad.payment.exception.UnprocessableContentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class ValidationBaseTest {
    ValidationBase validationBase;

    @BeforeEach
    void setup() {
        validationBase = new ValidationBase(new ArrayList<>());
    }

    @Test
    void testAddErrorField() {
        Assertions.assertDoesNotThrow(() -> {
            validationBase.addErrorField("test_name", IssueConstant.REQUIRED);

            Assertions.assertEquals(1, validationBase.errorBagDtos().size());
            Assertions.assertEquals("test_name", validationBase.errorBagDtos().get(0).getField());
            Assertions.assertEquals(IssueConstant.REQUIRED.name(), validationBase.errorBagDtos().get(0).getIssue());
        });
    }

    @Test
    void testAddErrorPathVariable() {
        Assertions.assertDoesNotThrow(() -> {
            validationBase.addErrorPathVariable("testId", IssueConstant.NOT_MATCH);

            Assertions.assertEquals(1, validationBase.errorBagDtos().size());
            Assertions.assertEquals(":testId", validationBase.errorBagDtos().get(0).getPathVariable());
            Assertions.assertEquals(IssueConstant.NOT_MATCH.name(), validationBase.errorBagDtos().get(0).getIssue());
        });
    }

    @Test
    void testRaiseError() {
        UnprocessableContentException exception = Assertions.assertThrows(UnprocessableContentException.class, () -> {
            validationBase.addErrorPathVariable("testId", IssueConstant.NOT_MATCH);
            validationBase.raiseError();
        });

        Assertions.assertEquals(1, exception.errorBagDtos.size());
        Assertions.assertEquals(":testId", exception.errorBagDtos.get(0).getPathVariable());
        Assertions.assertEquals(IssueConstant.NOT_MATCH.name(), exception.errorBagDtos.get(0).getIssue());
    }
}
