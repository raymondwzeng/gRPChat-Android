# gRPChat-Android
A simple gRPChat client for Android.

## "Gotchas Faced During Development
- `android-stub` (or the equivalent of) a module of a similar name is how I set up the project. Without it, the base Android app has no reference to the generated files.
- Similarly, the backend configuration build file has its intellisense for the generated files commented out. The backend will still build and run, but Android Studio will complain about that missing reference.
- Internet permissions, as always.
- Using some sort of authentication is required from the client side, even if it is plaintext.
