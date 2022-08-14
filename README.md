# gRPChat-Android
A simple gRPChat client for Android, along with the base backend to connect to.
Users can connect to a server and send/receive messages from other connected clients, similar to how Internet Relay Chat (IRC) works.

## Still To-Do
- Implement more services, allowing for greater flexibility in configuration
- Unit testing the hell out of everything :)

## "Gotchas" Faced During Development
- `android-stub` (or the equivalent of) a module of a similar name is how I set up the project. Without it, the base Android app has no reference to the generated files.
- Similarly, the backend configuration build file has its intellisense for the generated files commented out. The backend will still build and run, but Android Studio will complain about that missing reference.
- Internet permissions, as always.
- Using some sort of authentication is required from the client side, even if it is plaintext.
- Returning `super.foo` in the server side of an override function will result in a "method not implemented" error on the frontend, even if you have implemented something and return essentially nothing. Maybe Google's `empty` solves this.
