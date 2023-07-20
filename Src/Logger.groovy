/*
Super simple logging class, it holds 2 methods
for different kinds of logging
*/

class Logger {

/*
Method to writte a simple message with a timestamp to the standard output

Params:
- message: message to be logged to the standard output

Returns:
- None
*/
    static void writeMessage(String message) {
        println(new Date().toString() + "\t$message")
    }


/*
Method to writte a simple message with a timestamp and Error data to the standard output

Params:
- message: message to be logged to the standard output

Returns:
- None
*/
    static void writeError(String message) {
        println(new Date().toString() + "\tERROR: $message")
    }

}
