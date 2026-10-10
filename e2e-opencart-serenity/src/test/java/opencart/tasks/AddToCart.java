package opencart.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import opencart.ui.StorePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AddToCart {

    public static Performable theProduct(String product) {
        return Task.where("{0} adds '" + product + "' to the cart",
                Click.on(StorePage.addToCartButtonFor(product)),
                WaitUntil.the(StorePage.addedMessageFor(product), isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
