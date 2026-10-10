package opencart.ui;

import net.serenitybdd.screenplay.targets.Target;

/** Elementos de la página del carrito (route=checkout/cart). */
public class CartPage {

    /** Tabla con los productos del carrito (no depende de textos del sitio). */
    public static final Target CART_TABLE =
            Target.the("cart table").locatedBy("//div[@id='content']//form//table");

    /** Botón de checkout: se ubica por su enlace, porque los rótulos de la demo varían. */
    public static final Target CHECKOUT_BUTTON =
            Target.the("checkout button").locatedBy("//a[contains(@class,'btn-primary')][contains(@href,'checkout/checkout')]");

    public static Target productNamed(String product) {
        return Target.the(product + " in the cart").locatedBy(
                "//div[@id='content']//form//table//a[normalize-space()='" + product + "']");
    }
}
