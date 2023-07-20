import groovy.io.FileType

class StringReplacer {

    def recFiles = []
    String path
    String pattern
    String replacement
    File modFiles
    String backupPath
    File backupDir



    StringReplacer(String basePath, String pattern,
    String replacement, String modPath = './modified_files') {
        
        this.path = basePath
        this.pattern = pattern
        this.replacement = replacement
        this.modFiles = new File(modPath)
        this.modFiles.write("")


        // Check if there is a backup dir
        // Creates the dir if there is none
        this.backupPath = "Backup"
        this.backupDir = new File(this.backupPath)
        if (!backupDir.exists()){
            backupDir.mkdir()
        }
    }



    void replaceAll(){
        // Obtiene los archivos en folder y subfolders
        // Esto si o si hay que meterlo en un catch para el logging
        def baseDir = new File(this.path)
        baseDir.eachFileRecurse(FileType.FILES) {
            file -> this.recFiles << file
        }

        for (file in this.recFiles){
            replaceFile(file, this.pattern, this.replacement)
        }

        if (isBackupEmpty()) {
            this.backupDir.delete()
        }
    }



    void replaceFile(File file, String pattern, String replace){
            def text =  file.text.replaceAll(pattern, replace)
            if (text != file.text) {

                this.modFiles.append(file.name)
                
                File backup = new File(this.backupDir.name + '/' + file.name + '\n')
                backup.write(file.text)
                file.write(text)
            }
    }


    boolean isBackupEmpty(){
        def backupFiles = []
        this.backupDir.eachFileRecurse(FileType.FILES) {
            file -> backupFiles << file
        }
        return backupFiles.isEmpty()
    }
}