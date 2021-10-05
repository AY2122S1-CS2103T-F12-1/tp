package seedu.address.testutil;

import seedu.address.model.SmartNus;
import seedu.address.model.question.Question;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private SmartNus addressBook;

    public AddressBookBuilder() {
        addressBook = new SmartNus();
    }

    public AddressBookBuilder(SmartNus addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Question} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Question question) {
        addressBook.addQuestion(question);
        return this;
    }

    public SmartNus build() {
        return addressBook;
    }
}
