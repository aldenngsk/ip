# SixSeven – iP

> "Your mind is for having ideas, not holding them." – David Allen

**SixSeven** frees your mind of having to remember things you need to do. It is:

- *text-based*
- **easy to learn**
- ~~complicated~~ **fast** to use

All you need to do:

1. Download the JAR from [Releases](https://github.com/aldenngsk/ip/releases).
2. Run `java -jar SixSeven.jar`.
3. Add your tasks (todo, deadline, event).
4. Use `find` to search, or `list` to see all.

Check off what you can do:

- [x] Add and list tasks
- [x] Mark done / unmark
- [x] Find by keyword
- [ ] GUI (coming later)

Example entry point (see `sixseven.SixSeven`):

```java
public static void main(String[] args) {
    new SixSeven(DATA_FILE).run();
}
```

That’s it. 📋
