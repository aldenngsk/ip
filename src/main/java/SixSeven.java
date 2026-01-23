import java.util.Scanner;

public class SixSeven {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm SixSeven");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                continue;
            }

            if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[index]);
                continue;
            }

            if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].markUndone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(tasks[index]);
                continue;
            }

            if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount++] = new Todo(description);
                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                continue;
            }

            if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ");
                tasks[taskCount++] = new Deadline(parts[0], parts[1]);
                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                continue;
            }

            if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ");
                tasks[taskCount++] = new Event(parts[0], parts[1], parts[2]);
                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                continue;
            }

        }

        scanner.close();
    }
}
