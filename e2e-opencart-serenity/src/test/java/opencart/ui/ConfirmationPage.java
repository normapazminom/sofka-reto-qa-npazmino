package opencart.ui;

import net.serenitybdd.screenplay.targets.Target;

/** Página final con el mensaje "Your order has been placed!". */
public class ConfirmationPage {

    public static final Target ORDER_PLACED_HEADING = Target.the("order placed heading").locatedBy(
            "//h1[contains(normalize-space(.),'Your order has been placed!')]");
}
