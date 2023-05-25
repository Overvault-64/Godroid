# Project Godroid
## Android API for Godot 4.x

Project "Godroid" aims to add the most important Android features to your Godot 4 game/app.

This is still a WIP but it already provides the following methods:

- `GodroidAPI.display_toast(message : String)` - Displays a toast notification.

- `GodroidAPI.share_text(title : String, subject : String, text : String)` - Initializes a text share intent.

- `GodroidAPI.share_file(path : String, fileType : String, title : String)` - Initializes a file share intent. (Tested file types: "AUDIO", "IMAGE", "VIDEO")

- `GodroidAPI.show_notification(message : String, title : String, interval : int, tag := 1, repeat_duration : int = 0)` - Displays a notification with the given message, title and delay (interval).

- `GodroidAPI.in_app_review()` - Initializes the in-app review flow. (Can be done once in a while due to [quota limitations](https://developer.android.com/guide/playcore/in-app-review/#quotas))

<br>

---
<br>

Installation:
1. Download the [latest release](https://github.com/overvault-64/Godroid/releases/latest).
2. Put `Godroid.release.aar` and `Godroid.gdap` inside `android/plugins` in your project directory.
3. Put `godroid.gd` inside `addons` and add it as an Autoload to access its methods.

<br>

---
<br>

Help is HIGHLY welcome for both expanding features and debugging.

<br>

### Credits
- To [DrMoriarty](https://github.com/DrMoriarty) for portions of code from his [LocalNotifications](https://github.com/DrMoriarty/godot-local-notification)
- To [
Pascal Schwenke](https://github.com/pschw) for portions of code from his [InAppReviews](https://github.com/pschw/InAppReview)