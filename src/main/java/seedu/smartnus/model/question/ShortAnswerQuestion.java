package seedu.smartnus.model.question;

import java.util.Set;

import seedu.smartnus.model.choice.Choice;
import seedu.smartnus.model.tag.Tag;

public class ShortAnswerQuestion extends Question {
    public static final String MESSAGE_VALID_SAQ = "Short Answer Questions must have at least one correct answer.";

    public ShortAnswerQuestion(Name name, Importance importance, Set<Tag> tags,
                                  Set<Choice> choices) {
        super(name, importance, tags, choices);
    }

    public ShortAnswerQuestion(Name name, Importance importance, Set<Tag> tags,
                                  Set<Choice> choices, Statistic statistic) {
        super(name, importance, tags, choices, statistic);
    }

    /**
     * Returns True if {@code ShortAnswerQuestion} is valid, false otherwise.A {@code ShortAnswerQuestion} is
     * valid if it has exactly one choice which is correct.
     *
     * @return True if this ShortAnswerQuestion is valid, false otherwise.
     */
    @Override
    public boolean isValidQuestion() {
        int choiceCount = getChoices().size();
        int correctChoiceCount = 0;
        for (Choice choice : getChoices()) {
            if (choice.getIsCorrect()) {
                correctChoiceCount += 1;
            }
        }
        return correctChoiceCount >= 1;
    }

    @Override
    public String getQuestionAndOptions() {
        String title = this.getName().toString();
        return title;
    }

    @Override
    public int getQuestionType() {
        return SAQ_QUESTION_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MultipleChoiceQuestion)) {
            return false;
        }

        return super.equals(other);
    }
}