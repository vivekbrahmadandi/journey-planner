package planner.cucumber;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import planner.screenplay.questions.TheFirstTrain;
import planner.screenplay.tasks.PlanATrip;

import java.time.DayOfWeek;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.hamcrest.Matchers.is;

public class PlanAJourneyStepDefinitions {
    @Before
    public void set_the_stage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^that (.*) is a Sydney commuter$")
    public void a_london_commuter_named(String commuterName) throws Throwable {
        theActorCalled(commuterName);
    }

    @When("^(.*) plans a journey from (.*) to (.*) departing at (\\d\\d:\\d\\d) next (.*)$")
    public void plan_a_journeys(String name,
                                String departure,
                                String destination,
                                String departureTime,
                                DayOfWeek departureDay) throws Throwable {
        theActorCalled(name).attemptsTo(
                PlanATrip.from(departure).to(destination).departingAt(departureTime).next(departureDay)

        );
    }

    @Then("^(.*) should see that the first train departs at (\\d\\d:\\d\\d) and arrives at (\\d\\d:\\d\\d)$")
    public void should_see_departure_time(String name,
                                          String expectedDepartureTime,
                                          String expectedArrivaltTime) throws Throwable {
        theActorCalled(name).should(
                seeThat("the first train leaves at", TheFirstTrain.departureTime(), is(expectedDepartureTime)),
                seeThat("the first train arrives at", TheFirstTrain.arrivalTime(), is(expectedArrivaltTime))
        );
    }

}
