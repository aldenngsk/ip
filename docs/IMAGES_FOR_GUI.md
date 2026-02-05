# Images for GUI (optional)

The GUI uses **placeholders** (grey circles) where images can go. To make it look nicer, you can add:

1. **User avatar** – Small image (e.g. 40×40 or 80×80 px) shown next to the user’s messages (right side).  
   - Suggested: a simple person/avatar icon or your preferred user icon.  
   - Use in: `DialogBox.getUserDialog(text, userImageView)` when you have an image.

2. **Duke/SixSeven avatar** – Small image (e.g. 40×40 or 80×80 px) shown next to SixSeven’s replies (left side).  
   - Suggested: a robot/bot icon or mascot.  
   - Use in: `DialogBox.getDukeDialog(text, dukeImageView)` when you have an image.

3. **App icon** – Icon for the window title bar and taskbar (e.g. 16×16, 32×32).  
   - Set in `MainApp.start()` with `stage.getIcons().add(new Image(...))` if you add an icon.

Place image files under `src/main/resources/images/` (e.g. `user.png`, `duke.png`) and load them in the controller with `new ImageView(new Image(getClass().getResourceAsStream("/images/duke.png")))` then pass the ImageView into the DialogBox factory methods.
