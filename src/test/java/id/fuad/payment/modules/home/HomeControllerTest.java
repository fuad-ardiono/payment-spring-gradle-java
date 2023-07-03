package id.fuad.payment.modules.home;

import id.fuad.payment.module.home.HomeController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class HomeControllerTest {
    @InjectMocks
    HomeController homeController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHomeController() {
        String response = homeController.index();

        Assertions.assertEquals("Hello World!", response);
    }
}
