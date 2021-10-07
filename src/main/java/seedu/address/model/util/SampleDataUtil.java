package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.SmartNus;
import seedu.address.model.choice.Choice;
import seedu.address.model.ReadOnlySmartNus;
import seedu.address.model.question.Address;
import seedu.address.model.question.Email;
import seedu.address.model.question.MultipleChoiceQuestion;
import seedu.address.model.question.Name;
import seedu.address.model.question.Phone;
import seedu.address.model.question.Question;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Question[] getSamplePersons() {
        return new Question[] {
            new MultipleChoiceQuestion(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false)))
        };
    }

    public static ReadOnlySmartNus getSampleAddressBook() {
        SmartNus sampleAb = new SmartNus();
        for (Question sampleQuestion : getSamplePersons()) {
            sampleAb.addQuestion(sampleQuestion);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a choice set containing the list of strings given.
     */
    public static Set<Choice> getChoiceSet(Choice... choices) {
        return Arrays.stream(choices)
                .collect(Collectors.toSet());
    }

}
