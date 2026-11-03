# JPA Practice Project Plan

Use the existing task CRUD application as a starting point and gradually turn it into a small project-management system.

## 1. Add Projects

A project is its own entity and database table. One project can contain many tasks, while each task belongs to one project.

```text
Project 1 ────── many Tasks
```

This is the same relationship viewed from two directions:

- `Project` to `Task`: one-to-many
- `Task` to `Project`: many-to-one

The database structure would be:

```text
projects
├── id
└── name

tasks
├── id
├── title
└── project_id  → projects.id
```

The foreign key belongs in the `tasks` table because each task identifies the project to which it belongs.

On the `Task` entity:

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "project_id")
private Project project;
```

On the `Project` entity:

```java
@OneToMany(mappedBy = "project")
private List<Task> tasks = new ArrayList<>();
```

`mappedBy = "project"` refers to the `project` field inside `Task`. This side mirrors the relationship; it does not create another foreign key or join table.

### Features to build

- Create, view, update, and delete projects.
- Add a task to a project.
- List all tasks belonging to a project.
- Decide whether tasks can exist without a project.
- Decide what should happen to tasks when their project is deleted.



### JPA concepts practiced

- `@ManyToOne`
- `@OneToMany`
- `@JoinColumn`
- `mappedBy`
- Owning and inverse sides
- Foreign keys
- Lazy loading
- Cascading



## 2. Add Task Comments

One task can have many comments:

```text
Task 1 ────── many TaskComments
```

A comment could have an ID, message, creation time, author, and task.

Practice:

- `cascade = CascadeType.ALL`
- `orphanRemoval = true`
- Helper methods such as `addComment()` and `removeComment()`
- Preventing infinite JSON recursion by returning DTOs instead of entities



## 3. Add Users and Task Assignments

Each task can be assigned to a user, while a user can have many assigned tasks:

```text
User 1 ────── many Tasks
```

Features:

- Assign and unassign tasks.
- List tasks assigned to a user.
- Find overdue tasks belonging to a user.
- Distinguish between the task creator and assignee.

Practice derived repository queries such as:

```java
List<Task> findByAssigneeId(UUID userId);
```



## 4. Add Tags

A task can have many tags, and a tag can belong to many tasks:

```text
Tasks many ────── many Tags
```

Example:

```text
Task: Fix login
Tags: backend, urgent, bug
```

Use `@ManyToMany` and `@JoinTable` to practice join tables and relationship ownership. Do this after becoming comfortable with many-to-one and one-to-many relationships.

## 5. Add Subtasks

A task can contain other tasks:

```text
Parent Task
├── Subtask
├── Subtask
└── Subtask
```

This is a self-referencing relationship:

```java
@ManyToOne
private Task parent;

@OneToMany(mappedBy = "parent")
private List<Task> subtasks;
```



## 6. Practice Repository Queries

Add filters for:

- Status
- Priority
- Due-date range
- Project
- Assignee
- Title search
- Overdue tasks

Start with derived queries:

```java
List<Task> findByStatus(TaskStatus status);

List<Task> findByDueDateBeforeAndStatusNot(
    LocalDate date,
    TaskStatus status
);
```

Then learn:

- `@Query` and JPQL
- Pagination with `Pageable`
- Specifications for combining optional filters
- Fetch joins and the N+1 query problem



## 7. Add Task History

Record every task status change:

```text
Task 1 ────── many TaskStatusHistory entries
```

Store the previous status, new status, change time, and user who made the change. This provides practice with transactions and audit records.

## 8. Add Optimistic Locking

Add a version field:

```java
@Version
private Long version;
```

This prevents two users from silently overwriting the same task at the same time.

## Recommended Build Order

1. Projects and tasks
2. Task comments
3. Users and assignments
4. Custom queries and pagination
5. Tags
6. Subtasks
7. Task history and auditing
8. Optimistic locking



## First Goal

Start with projects and implement:

```text
POST /api/v1/projects
POST /api/v1/projects/{projectId}/tasks
GET  /api/v1/projects/{projectId}/tasks
```

Focus first on understanding why `Task` owns the relationship through its `project_id` foreign key.

## Learning Resources

- [Hibernate ORM associations guide](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#associations)
- [Spring Data JPA guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [JPA and Hibernate associations](https://www.baeldung.com/jpa-hibernate-associations)
- [Beginner JPA relationships video](https://www.youtube.com/watch?v=CvDS6DltIno)
- [Many-to-one video](https://www.youtube.com/watch?v=s35TLV_gQi8)
- [One-to-many video](https://www.youtube.com/watch?v=1SIkBduGXeE)

Older tutorials may import `javax.persistence`. This Spring Boot project should use `jakarta.persistence` instead.