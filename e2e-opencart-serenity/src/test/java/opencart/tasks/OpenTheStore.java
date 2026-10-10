package opencart.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import opencart.ui.OpenCartHomePage;

public class OpenTheStore {

    public static Performable homePage() {
        return Task.where("{0} opens the OpenCart store",
                Open.browserOn().the(OpenCartHomePage.class));
    }
}
