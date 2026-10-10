package opencart.ui;

import net.serenitybdd.screenplay.targets.Target;

/** Elementos de la página principal y del encabezado de la tienda. */
public class StorePage {

    public static final Target SHOPPING_CART_LINK =
            Target.the("shopping cart link").locatedBy("#top-links a[title='Shopping Cart']");

    /** Botón "Add to Cart" de la tarjeta del producto indicado. */
    public static Target addToCartButtonFor(String product) {
        return Target.the("'Add to Cart' button of " + product).locatedBy(
                "//div[contains(@class,'product-thumb')][.//h4/a[normalize-space()='" + product + "']]"
                        + "//button[contains(@onclick,'cart.add')]");
    }

    /** Mensaje de éxito que aparece al agregar el producto indicado. */
    public static Target addedMessageFor(String product) {
        return Target.the("success message for " + product).locatedBy(
                "//div[contains(@class,'alert-success')][contains(.,'" + product + "')]");
    }
}
