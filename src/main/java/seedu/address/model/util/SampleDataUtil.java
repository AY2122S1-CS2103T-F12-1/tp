package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlySmartNus;
import seedu.address.model.SmartNus;
import seedu.address.model.choice.Choice;
import seedu.address.model.question.Importance;
import seedu.address.model.question.MultipleChoiceQuestion;
import seedu.address.model.question.Name;
import seedu.address.model.question.Question;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Question[] getSampleQuestions() {
        return new Question[] {
            new MultipleChoiceQuestion(new Name("Alex Yeoh"), new Importance("1"),
                getTagSet("friends"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("Bernice Yu"), new Importance("2"),
                getTagSet("colleagues", "friends"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("Charlotte Oliveiro"), new Importance("3"),
                getTagSet("neighbours"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("David Li"), new Importance("1"),
                getTagSet("family"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("Irfan Ibrahim"), new Importance("2"),
                getTagSet("classmates"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false))),
            new MultipleChoiceQuestion(new Name("Roy Balakrishnan"), new Importance("3"),
                getTagSet("colleagues"), getChoiceSet(new Choice("first choice", true),
                    new Choice("second choice", false)))
        };
    }

    public static ReadOnlySmartNus getSampleAddressBook() {
        SmartNus sampleAb = new SmartNus();
        for (Question sampleQuestion : getSampleQuestions()) {
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
