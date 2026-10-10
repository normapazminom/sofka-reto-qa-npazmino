package opencart.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import opencart.ui.CartPage;
import opencart.ui.CheckoutPage;

import java.util.Map;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Tareas del flujo de checkout como invitado. */
public class CheckoutAsGuest {

    /** Paso 1 y 2: elegir "Guest Checkout" y completar los datos de facturación. */
    public static Performable withDetails(Map<String, String> data) {
        return Task.where("{0} starts the checkout as a guest",
                JavaScriptClick.on(CartPage.CHECKOUT_BUTTON),
                WaitUntil.the(CheckoutPage.GUEST_OPTION, isPresent()).forNoMoreThan(40).seconds(),
                JavaScriptClick.on(CheckoutPage.GUEST_OPTION),
                JavaScriptClick.on(CheckoutPage.CONTINUE_ACCOUNT),
                WaitUntil.the(CheckoutPage.FIRST_NAME, isVisible()).forNoMoreThan(20).seconds(),
                Enter.theValue(data.get("firstName")).into(CheckoutPage.FIRST_NAME),
                Enter.theValue(data.get("lastName")).into(CheckoutPage.LAST_NAME),
                Enter.theValue(data.get("email")).into(CheckoutPage.EMAIL),
                Enter.theValue(data.get("telephone")).into(CheckoutPage.TELEPHONE),
                Enter.theValue(data.get("address")).into(CheckoutPage.ADDRESS),
                Enter.theValue(data.get("city")).into(CheckoutPage.CITY),
                Enter.theValue(data.get("postcode")).into(CheckoutPage.POSTCODE),
                SelectFromOptions.byVisibleText(data.get("country")).from(CheckoutPage.COUNTRY),
                WaitUntil.the(CheckoutPage.regionOption(data.get("region")), isPresent()).forNoMoreThan(15).seconds(),
                SelectFromOptions.byVisibleText(data.get("region")).from(CheckoutPage.REGION),
                JavaScriptClick.on(CheckoutPage.CONTINUE_GUEST)
        );
    }

    /** Pasos 4 y 5: aceptar el método de envío y los términos del método de pago. */
    public static Performable acceptingShippingAndPayment() {
        return Task.where("{0} accepts the shipping method and the payment terms",
                WaitUntil.the(CheckoutPage.CONTINUE_SHIPPING, isClickable()).forNoMoreThan(20).seconds(),
                JavaScriptClick.on(CheckoutPage.CONTINUE_SHIPPING),
                WaitUntil.the(CheckoutPage.AGREE_TERMS, isVisible()).forNoMoreThan(20).seconds(),
                JavaScriptClick.on(CheckoutPage.AGREE_TERMS),
                JavaScriptClick.on(CheckoutPage.CONTINUE_PAYMENT)
        );
    }
}
