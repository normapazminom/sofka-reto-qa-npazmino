package opencart.ui;

import net.serenitybdd.screenplay.targets.Target;

/** Elementos de la página de Checkout (flujo "Guest Checkout"). Localizadores en XPath. */
public class CheckoutPage {

    // Paso 1: opciones de checkout
    public static final Target GUEST_OPTION =
            Target.the("Guest Checkout option").locatedBy("//input[@name='account' and @value='guest']");
    public static final Target CONTINUE_ACCOUNT =
            Target.the("continue (checkout options)").locatedBy("//*[@id='button-account']");

    // Paso 2: detalles de facturación
    public static final Target FIRST_NAME = Target.the("first name").locatedBy("//*[@id='input-payment-firstname']");
    public static final Target LAST_NAME = Target.the("last name").locatedBy("//*[@id='input-payment-lastname']");
    public static final Target EMAIL = Target.the("e-mail").locatedBy("//*[@id='input-payment-email']");
    public static final Target TELEPHONE = Target.the("telephone").locatedBy("//*[@id='input-payment-telephone']");
    public static final Target ADDRESS = Target.the("address").locatedBy("//*[@id='input-payment-address-1']");
    public static final Target CITY = Target.the("city").locatedBy("//*[@id='input-payment-city']");
    public static final Target POSTCODE = Target.the("post code").locatedBy("//*[@id='input-payment-postcode']");
    public static final Target COUNTRY = Target.the("country").locatedBy("//*[@id='input-payment-country']");
    public static final Target REGION = Target.the("region / state").locatedBy("//*[@id='input-payment-zone']");
    public static final Target CONTINUE_GUEST =
            Target.the("continue (billing details)").locatedBy("//*[@id='button-guest']");

    /** Opción de región; se carga por ajax al elegir el país. */
    public static Target regionOption(String region) {
        return Target.the("region option " + region).locatedBy(
                "//select[@id='input-payment-zone']/option[normalize-space()='" + region + "']");
    }

    // Pasos 4 y 5: envío y pago
    public static final Target CONTINUE_SHIPPING =
            Target.the("continue (delivery method)").locatedBy("//*[@id='button-shipping-method']");
    public static final Target AGREE_TERMS =
            Target.the("terms and conditions checkbox").locatedBy("//input[@name='agree']");
    public static final Target CONTINUE_PAYMENT =
            Target.the("continue (payment method)").locatedBy("//*[@id='button-payment-method']");

    // Paso 6: confirmación
    public static final Target CONFIRM_ORDER =
            Target.the("confirm order button").locatedBy("//*[@id='button-confirm']");
}
