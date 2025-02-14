import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents a question with an ID, title, and description
class Question {
    private static int nextId = 1;
    private final int id;
    private String title;
    private String description;

    public Question(String title, String description) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + description;
    }
}

// Represents an answer to a specific question
class Answer {
    private static int nextId = 1;
    private final int id;
    private final int questionId;
    private String text;

    public Answer(int questionId, String text) {
        this.id = nextId++;
        this.questionId = questionId;
        this.text = text;
    }

    public int getId() { return id; }
    public int getQuestionId() { return questionId; }
    public String getText() { return text; }

    public void updateText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "[Answer " + id + " for Question " + questionId + "] " + text;
    }
}

// Manages a collection of questions
class QuestionManager {
    private final List<Question> questions = new ArrayList<>();

    public String addQuestion(String title, String description) {
        if (title == null || title.isBlank()) {
            return "Please provide a valid title.";
        }
        if (description == null || description.isBlank()) {
            return "Description cannot be empty.";
        }
        Question q = new Question(title, description);
        questions.add(q);
        return "Question added! ID: " + q.getId();
    }

    public List<Question> getAllQuestions() {
        return questions;
    }

    public String updateQuestion(int id, String newTitle, String newDescription) {
        for (Question q : questions) {
            if (q.getId() == id) {
                if (!newTitle.isBlank()) q.updateTitle(newTitle);
                if (!newDescription.isBlank()) q.updateDescription(newDescription);
                return "Question updated successfully.";
            }
        }
        return "No question found with that ID.";
    }

    public String deleteQuestion(int id) {
        return questions.removeIf(q -> q.getId() == id) ? "Question deleted." : "Question not found.";
    }

    public Question findQuestionById(int id) {
        for (Question q : questions) {
            if (q.getId() == id) return q;
        }
        return null;
    }
}

// Manages collection of answers
class AnswerManager {
    private final List<Answer> answers = new ArrayList<>();

    public String addAnswer(int questionId, String text) {
        if (text == null || text.isBlank()) {
            return "Answer cannot be empty.";
        }
        answers.add(new Answer(questionId, text));
        return "Answer added!";
    }

    public List<Answer> getAnswersForQuestion(int questionId) {
        List<Answer> results = new ArrayList<>();
        for (Answer a : answers) {
            if (a.getQuestionId() == questionId) results.add(a);
        }
        return results;
    }

    public String updateAnswer(int id, String newText) {
        for (Answer a : answers) {
            if (a.getId() == id) {
                a.updateText(newText);
                return "Answer updated.";
            }
        }
        return "Answer not found.";
    }

    public String deleteAnswer(int id) {
        return answers.removeIf(a -> a.getId() == id) ? "Answer deleted." : "Answer not found.";
    }
}

// Main class to run the Q&A system
public class HW2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuestionManager questionManager = new QuestionManager();
        AnswerManager answerManager = new AnswerManager();

        while (true) {
            System.out.println("\nStudent Q&A System");
            System.out.println("1. Add a Question");
            System.out.println("2. View All Questions");
            System.out.println("3. Update a Question");
            System.out.println("4. Delete a Question");
            System.out.println("5. Add an Answer");
            System.out.println("6. View Answers for a Question");
            System.out.println("7. Update an Answer");
            System.out.println("8. Delete an Answer");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.println(questionManager.addQuestion(title, description));
                    break;
                case 2:
                    List<Question> questions = questionManager.getAllQuestions();
                    if (questions.isEmpty()) System.out.println("No questions found.");
                    else questions.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter Question ID: ");
                    int qId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter New Title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter New Description: ");
                    String newDesc = scanner.nextLine();
                    System.out.println(questionManager.updateQuestion(qId, newTitle, newDesc));
                    break;
                case 4:
                    System.out.print("Enter Question ID to Delete: ");
                    int deleteQId = Integer.parseInt(scanner.nextLine());
                    System.out.println(questionManager.deleteQuestion(deleteQId));
                    break;
                case 5:
                    System.out.print("Enter Question ID for the Answer: ");
                    int qidForAnswer = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Answer: ");
                    String answerText = scanner.nextLine();
                    System.out.println(answerManager.addAnswer(qidForAnswer, answerText));
                    break;
                case 6:
                    System.out.print("Enter Question ID to View Answers: ");
                    int qidForView = Integer.parseInt(scanner.nextLine());
                    List<Answer> answers = answerManager.getAnswersForQuestion(qidForView);
                    if (answers.isEmpty()) System.out.println("No answers available.");
                    else answers.forEach(System.out::println);
                    break;
                case 7:
                    System.out.print("Enter Answer ID to Update: ");
                    int aidToUpdate = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter New Answer: ");
                    String newAnswerText = scanner.nextLine();
                    System.out.println(answerManager.updateAnswer(aidToUpdate, newAnswerText));
                    break;
                case 8:
                    System.out.print("Enter Answer ID to Delete: ");
                    int aidToDelete = Integer.parseInt(scanner.nextLine());
                    System.out.println(answerManager.deleteAnswer(aidToDelete));
                    break;
                case 9:
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}