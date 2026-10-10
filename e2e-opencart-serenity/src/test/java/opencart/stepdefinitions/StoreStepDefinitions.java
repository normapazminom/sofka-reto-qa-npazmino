package opencart.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;
import opencart.tasks.AddToCart;
import opencart.tasks.CheckoutAsGuest;
import opencart.tasks.ConfirmTheOrder;
import opencart.tasks.OpenTheStore;
import opencart.tasks.ViewTheCart;
import opencart.ui.CartPage;
import opencart.ui.ConfirmationPage;

import java.util.Map;

public class StoreStepDefinitions {

    @Given("{actor} opens the OpenCart store")
    public void opensTheStore(Actor actor) {
        actor.wasAbleTo(OpenTheStore.homePage());
    }

    @When("{actor} adds the product {string} to the cart")
    public void addsTheProduct(Actor actor, String product) {
        actor.attemptsTo(AddToCart.theProduct(product));
    }

    @When("{actor} views the shopping cart")
    public void viewsTheCart(Actor actor) {
        actor.attemptsTo(ViewTheCart.contents());
    }

    @Then("{actor} should see {string} and {string} in the cart")
    public void shouldSeeProductsInTheCart(Actor actor, String first, String second) {
        actor.attemptsTo(
                Ensure.that(Text.of(CartPage.productNamed(first))).containsIgnoringCase(first),
                Ensure.that(Text.of(CartPage.productNamed(second))).containsIgnoringCase(second)
        );
    }

    @When("{actor} starts the checkout as a guest with these details:")
    public void startsGuestCheckout(Actor actor, DataTable details) {
        Map<String, String> data = details.asMap(String.class, String.class);
        actor.attemptsTo(CheckoutAsGuest.withDetails(data));
    }

    @When("{actor} accepts the shipping method and the payment terms")
    public void acceptsShippingAndPayment(Actor actor) {
        actor.attemptsTo(CheckoutAsGuest.acceptingShippingAndPayment());
    }

    @When("{actor} confirms the order")
    public void confirmsTheOrder(Actor actor) {
        actor.attemptsTo(ConfirmTheOrder.now());
    }

    @Then("{actor} should see the message {string}")
    public void shouldSeeTheMessage(Actor actor, String message) {
        actor.attemptsTo(
                Ensure.that(Text.of(ConfirmationPage.ORDER_PLACED_HEADING)).containsIgnoringCase(message)
        );
    }
}
