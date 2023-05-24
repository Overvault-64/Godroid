# Project Godroid
## Android API for Godot 4.x

Project "Godroid" aims to add the most important Android features to your Godot 4.x game/app.

This is still a WIP but it already provides the following methods:

- `GodroidAPI.displayToast(message : String)` - Display a toast notification.

- `GodroidAPI.shareText(title : String, subject : String, text : String)` - Initializes a text share intent.

- `GodroidAPI.shareFile(path : String, fileType : String, title : String)` - Initializes a file share intent. (Tested file types: "AUDIO", "IMAGE", "VIDEO")

- `GodroidAPI.inAppReview()` - Initializes the in-app review flow. (Can be done once in a while due to [quota limitations](https://developer.android.com/guide/playcore/in-app-review/#quotas))

<br>

---
<br>

Installation:
1. Download the [latest release](https://github.com/overvault-64/Godroid/releases/download/v1.0/godroid.1.0.zip).
2. Put `Godroid.release.aar` and `Godroid.gdap` inside `android/plugins` in your project directory.
3. Put `godroid.gd` inside `addons` and add it as an Autoload to access its methods.

<br>

---
<br>

Help is HIGHLY welcome for both expanding features and debugging.

<br>

### To do:
- integrate [Local Notifications](https://github.com/DrMoriarty/godot-local-notification)
- integrate [Firebase Cloud Messaging](https://github.com/DrMoriarty/godot-firebase-cloudmessaging) (I already tried to port it but didn't work)