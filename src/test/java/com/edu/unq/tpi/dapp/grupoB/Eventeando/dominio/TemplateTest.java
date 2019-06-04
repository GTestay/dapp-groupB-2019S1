package com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.EventFactory;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.factory.UserFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Template.INVALID_TEMPLATE_CREATION;
import static junit.framework.TestCase.*;

public class TemplateTest {


    private User organizer;
    private EventFactory eventFactory;
    private List<Expense> expenses;
    private String description;
    private List<User> guests;

    @Before
    public void setUp() {
        UserFactory userFactory = new UserFactory();
        organizer = userFactory.user();

        eventFactory = new EventFactory();
        expenses = eventFactory.expenses();

        description = eventFactory.description();
        guests = userFactory.someUsers();
    }

    @Test
    public void aTemplateCanNotBeCreatedWithoutDescription() {
        try {
            Template.create(null, expenses);
            fail();
        } catch (RuntimeException e) {
            assertEquals(INVALID_TEMPLATE_CREATION, e.getMessage());
        }
    }

    @Test
    public void aTemplateCanNotBeCreatedWithoutExpenses() {
        try {
            Template.create(description, new ArrayList<>());
            fail();
        } catch (RuntimeException e) {
            assertEquals(INVALID_TEMPLATE_CREATION, e.getMessage());
        }
    }


    @Test
    public void aTemplateCanGenerateANewPartyEventWithAnOrganizerAndGuests() {
        Template template = createTemplate();

        Party event = template.generateNewPartyEventWith(organizer, guests, eventFactory.anInvitationLimitDate());

        assertThatIsValidEvent(template, organizer, event.description(), event.expenses());
    }


    @Test
    public void aTemplateCanGenerateANewPotluckEventWithAnOrganizerAndGuests() {
        Template template = createTemplate();

        PotluckEvent event = template.generateNewPotluckEventWith(organizer, guests);

        assertThatIsValidEvent(template, organizer, event.description(), event.expenses());
    }


    @Test
    public void aTemplateCanGenerateANewBaquitaSharedExpensesEventWithAnOrganizerAndGuests() {
        Template template = createTemplate();

        BaquitaSharedExpensesEvent event = template.generateNewBaquitaSharedExpensesEventWith(organizer, guests);

        assertThatIsValidEvent(template, organizer, event.description(), event.expenses());
    }


    @Test
    public void aTemplateCanGenerateANewBaquitaCrowfundingEventWithAnOrganizerAndGuests() {
        Template template = createTemplate();

        BaquitaCrowdFundingEvent event = template.generateNewBaquitaCrowdFundingEventWith(organizer, guests);

        assertThatIsValidEvent(template, organizer, event.description(), event.expenses());
    }

    private Template createTemplate() {
        return Template.create(description, expenses);
    }

    private void assertThatIsValidEvent(Template template, User organizer, String description, List<Expense> expenses) {
        assertTrue(template.hasUser(organizer));
        assertEquals(description, template.description());
        assertEquals(expenses, template.expenses());
    }


}
