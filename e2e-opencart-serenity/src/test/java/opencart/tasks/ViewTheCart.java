package opencart.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import opencart.ui.CartPage;
import opencart.ui.StorePage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ViewTheCart {

    public static Performable contents() {
        return Task.where("{0} opens the shopping cart",
                Click.on(StorePage.SHOPPING_CART_LINK),
                WaitUntil.the(CartPage.CART_TABLE, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
