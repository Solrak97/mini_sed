import groovy.io.FileType
import Logger

/*
I don't have any complex data structures, just a couple of lists to store all the files
in the subdirectories and a list to storethe name of the modified files,
I feel like a function would be better than a class somehow, the docs portrait Groovy
more like a scripting language than an object oriented compiled language so I feel like 
simplicity is the main goal here, the class is pretty useful if its going to be extended
somehow so I'm not really against it.
*/

class StringReplacer {

    def allFiles = []
    def modifiedFileList = []

    String basePath
    String basePattern
    String replacementString
    String modifiedFilesPath
    String backupPath

/*
Class Constructor

Params:
- String basePath: the path of the base folder to traverse
- String pattern: base pattern to find
- String replacement: string to replace pattern
- (Optional) String modPath: path of the modified files list, it has a default value of  "./modified_files"

Returns:
- None
*/

    StringReplacer(String basePath, String basePattern,
    String replacementString, String modifiedFilesPath = './modified_files') {

        this.basePath = basePath
        this.basePattern = basePattern
        this.replacementString = replacementString
        this.modifiedFilesPath = modifiedFilesPath

        this.backupPath = 'Files_Backup'

        try {
            File backupDir = new File(this.backupPath)
            if (!backupDir.exists()) {
                backupDir.mkdir()
            }
        }
        catch (Exception e) {
            Logger('There was a problem creating the backup directory')
            System.exit(0)
        }
    }

/*
A function to search and replace within all files starting in
a given directory

Params:
- None

Returns:
- None
*/

    void replaceAll() {
        Logger.writeMessage('Process Started')

        try {
            File baseDir = new File(this.basePath)
            baseDir.eachFileRecurse(FileType.FILES) {
                file -> this.allFiles << file
            }
        } catch (Exception e) {
            Logger.writeError("The given path: $e.message is not a valid directory!")
            Logger.writeMessage('Process finished with errors')
            System.exit(0)
        }

        for (file in this.allFiles) {
            replaceInFile(file, this.basePattern, this.replacementString)
        }

        // It's done as a string to avoid doing multiple io write calls
        String modFilesStr = ''
        for (file in this.modifiedFileList) {
            modFilesStr += ("$file\n")
        }

        // So its saved here after making a string from the list of files
        try {
            new File(this.modifiedFilesPath).write(modFilesStr)
        }catch (Exception e) {
            Logger.writeMessage("There was an error writing the list of modified files to $e.message")
            System.exit(0)
        }

        Logger.writeMessage('Process finished with no errors')
    }

/*
A function to search and replace on a single file

Params:
- file: the file to be searched for replace
- pattern: the pattern to search for
- replacement: the string to replace the pattern

Returns:
- None
*/

    void replaceInFile(File file, String pattern, String replacement) {
        if (file.text.contains(pattern)) {
            /*
            I'm not sure how low level u guys wanted this specific bit of code,
            it's pretty high level to use replaceAll, support for regex
            should be pretty easy to implement at this point if needed
            and I doubt writing my own state machine to parse text was part
            of the exercise so the high level code should be enough
             */
            String fileContent = file.text.replaceAll(pattern, replacement)
            new File("$this.backupPath/$file.name").bytes = file.bytes
            this.modifiedFileList << file.name
            file.write(fileContent)
            Logger.writeMessage("Pattern found: $pattern in file: $file.name replaced with: $replacement")
        }
    }

}
