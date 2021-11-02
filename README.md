# EqnoCal-Android  

### Introduction  

- A scientific calculator for Android.
- Sdk version: minApi-26, targetApi-31.
- You can use it on your Android phone.

### Installing

- You could build the source files using `Android Studio`.
- Open the project with it and select `Build APKs` in `Build` item.
- Then you'll get an apk file named `EqnoCal`.
- Install the apk on your Android phone and you can use it now.

### Solution  

- Judge syntax with regex:  
  When you would like to append a character to the screen,  
  it will be judged by several regex strings.  
  If the expression affter appending is valid, it'll be shown on the screen at once,  
  while if not, it won't be shown and there'll be the origin expression on the screen.
- Parse the expression with Syntax Tree:  
  When you click `equal`, the expression will be checked and sent to Syntax Tree.  
  And it'll be parsed and calculated to a digital ans.  
  The ans will be shown on the screen in place of the origin expression.
  
