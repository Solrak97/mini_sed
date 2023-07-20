/*

It feels like a little overkill to have an entire file just
to create an instance and run a class method, but I dont want to
keep this part of the script inside the StringReplace file
I don´t think anyone is going to reuse the class but anyway, it feels
cleaner to me

*/

import StringReplacer
import Logger

StringReplacer replacer


/* I don't like having a conditional here to check for the optional
args, but I don't know enough groovy (yet) to do it on a cleaner way
*/
if (this.args.size() == 3) {
    replacer = new StringReplacer(this.args[0], this.args[1], this.args[2])
}
else if (this.args.size() == 4){
    replacer = new StringReplacer(this.args[0], this.args[1], this.args[2], this.args[3])
}
else{
    Logger.writeError('''please run with params <path> <pattern> <replacement string> <optional: output>''')
    System.exit(0)
}

replacer.replaceAll()
