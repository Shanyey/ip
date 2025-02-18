# Nova User Guide
![Screenshot of Nova GUI](./Ui.png)

Nova Chatbot is a **lightweight**, Java-based personal assistant for managing your tasks. With Nova, you can:
- add to-dos
- add deadlines
- add events 
- delete tasks
- mark or unmark tasks as done
- view your task list, 
- undo or redo changes

Nova’s conversational tone and intuitive error messages make task management both efficient and enjoyable.

> [!TIP]
> 
>- Replace <content> with the correct text without the arrow heads!
> - Inputs are case-insensitive.

## Adding To-Dos

Creates a to-do task with a description. Type List after to view your to-do task.

Format: `Todo <Description>`

Example: `Todo Read Book`

```
Your wish is my command UwU
added to-do task Read Book OwU!
```

## Adding deadlines

Creates a deadline task with a description and due date. Type list after to view your deadline task.

Format: `Deadline <Description> /by <yyyy-mm-dd hh:mm>`

Example: `Deadline Return Textbook /by 2025-03-05 19:30`

```
Your wish is my command UwU
added deadline Return Textbook OwU!
```

## Adding events
Creates an event task with a description, starting and ending times. Type list after to view your event.

Format: `Event <Description> /from <yyyy-mm-dd hh:mm> /to <yyyy-mm-dd hh:mm>`

Example: `Event Lecture /from 2025-02-19 12:00 /to 2025-02-19 14:00`

```
Your wish is my command UwU
added event Lecture :(
```

## List

Displays a list of all your added tasks.

Format: `List`
```
Your wish is my command UwU
1.[T][ ] Read Book
2.[D][ ] Return Textbook (by: mar 05 2025 19:30)
3.[E][ ] Lecture (from: Feb 19 2025 12:00 to: Feb 19 2025 14:00)
```

## Mark Task

Mark a task as done.

Format: `Mark <Index>`
- You must input a valid digit as the index.

Example: `Mark 1`

```
Your wish is my command UwU
marked task as done OwO
```

## Unmark Task

Mark a task as not done.

Format: `Unmark <Index>`
- You must input a valid digit as the index.

Example: `Unmark 1`

```
Your wish is my command UwU
unmarked your task OwO
```

## Delete Task

Deletes a selected task.

Format: `Delete <Index>`
- You must input a valid digit as the index.

Example: `Delete 3`

```
Your wish is my command UwU
deleted your task OwO
```

## Find Task

Finds a task based on the description.

Format: `Find <Description>`
- Description does not have to be the full description of the task to find tasks.

Example: `find Read`

```
Your wish is my command UwU
1.[T][ ] Read Book
```

## Undo Action

Undo the previous action that edited tasks.

Format: `undo`

```
Your wish is my command UwU
your action has been undone :)
```

## Redo Action

Re-applies an undone action.

Format: `redo`

```
Your wish is my command UwU
your action has been reverted :)
```

## Viewing Help

Provides all functions and command formats.

Format: `help`

```
Your wish is my command UwU
Single word commands are:
List, Undo. Redo, Find, Bye, Help

Adding a todo task:
todo <description>

Adding a deadline task:
deadline <description> /by <YYY-MM-DD HH:MM>

Adding a event task:
event <description> /from <YYY-MM-DD HH:MM> /to <YYY-MM-DD HH:MM>

!!! Please do not use '/' in your descriptions !!!
```

## Exiting the program

Exits the chatbot

Format: `bye`

## Saving the data

Task data is automatically saved upon exiting the chat using the `Bye` command.

>[!Warning]
> 
>Please exit using 'bye' command in order to keep your data!