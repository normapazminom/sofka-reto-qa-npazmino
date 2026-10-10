package opencart.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.waits.WaitUntil;
import opencart.ui.CheckoutPage;
import opencart.ui.ConfirmationPage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ConfirmTheOrder {

    public static Performable now() {
        return Task.where("{0} confirms the order",
                WaitUntil.the(CheckoutPage.CONFIRM_ORDER, isClickable()).forNoMoreThan(20).seconds(),
                JavaScriptClick.on(CheckoutPage.CONFIRM_ORDER),
                WaitUntil.the(ConfirmationPage.ORDER_PLACED_HEADING, isVisible()).forNoMoreThan(30).seconds()
        );
    }
}
