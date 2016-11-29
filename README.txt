
***********************************
*           READ ME               *
***********************************

To run the application, please use the speed21.java driver.

The application "drivers" are within the driver package:
au.edu.rmit.cpt222.driver
-a2Server (launches the server, please ensure this is running first)
-a2Client (launches the client)

Clients connect to the server via localhost on port 10001
ClientCallbackServer's listen on localhost at the first available
free port.

Some notes on the GUI:
Some of the functionality of the application was intentionally
left out of the GUI, this was entirely a design choice. 
For example the GUI does not show the user the bust cards. 
I made this choice as during development I discovered that 
displaying the bust card was misleading.
This code however, is fully implemented within the model.