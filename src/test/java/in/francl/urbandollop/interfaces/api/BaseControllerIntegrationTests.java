package in.francl.urbandollop.interfaces.api;

import in.francl.urbandollop.interfaces.api.advice.ExceptionHandlerController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;


@WebMvcTest
@ContextConfiguration(
        classes = {
                ExceptionHandlerController.class,
                BaseController.class,
        }
)
public class BaseControllerIntegrationTests {
}
