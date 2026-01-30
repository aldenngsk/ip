import java.util.Scanner;

public class SixSeven {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm SixSeven");
        System.out.println("What can I do for you?");

        while (true) {
            try {
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
                    if (index < 0 || index >= taskCount) {
                        throw new DukeException("That task number does not exist.");
                    }
                    tasks[index].markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index]);
                    continue;
                }

                if (input.startsWith("delete ")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                
                    if (index < 0 || index >= taskCount) {
                        throw new DukeException("That task number does not exist.");
                    }
                
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(tasks[index]);
                
                    for (int i = index; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }
                
                    taskCount--;
                
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    continue;
                }
                

                if (input.startsWith("unmark ")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= taskCount) {
                        throw new DukeException("That task number does not exist.");
                    }
                    tasks[index].markUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index]);
                    continue;
                }

                if (input.equals("todo")) {
                    throw new DukeException("The description of a todo cannot be empty.");
                }

                if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("The description of a todo cannot be empty.");
                    }
                    tasks[taskCount++] = new Todo(description);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    continue;
                }

                if (input.startsWith("deadline ")) {
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2) {
                        throw new DukeException("Please specify a deadline using /by.");
                    }
                    tasks[taskCount++] = new Deadline(parts[0], parts[1]);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    continue;
                }

                if (input.startsWith("event ")) {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new DukeException("Please specify an event using /from and /to.");
                    }
                    tasks[taskCount++] = new Event(parts[0], parts[1], parts[2]);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    continue;
                }

                throw new DukeException("I don't understand that command.");

            } catch (DukeException e) {
                System.out.println("Oops! " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Oops! Something went wrong.");
            }
        }

        scanner.close();
    }
}
