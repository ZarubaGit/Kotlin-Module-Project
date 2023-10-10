import java.lang.NumberFormatException
import java.util.Scanner
import kotlin.system.exitProcess

private val scanner = Scanner(System.`in`)

class NotesApp {
    private val archives = mutableListOf<Archive>()

     fun run() {
        while (true) {
            printMainMenu()
            when (readIntInput()) {
                1 -> createArchive()
                2 -> viewArchives()
                3 -> exitApp()
                else -> println("Неправильный выбор, введите правильную цифру.")
            }

        }
    }

    private fun readIntInput(): Int {
        return try {
            scanner.nextLine().toInt()
        } catch (e: NumberFormatException) {
            println("Пожалуйста, введите цифру")
            -1
        }
    }

    private fun printMainMenu() {
        println(
            "Меню:" +
                    "\n1. Создать архив" +
                    "\n2. Просмотр архивов" +
                    "\n3. Выйти из приложения"
        )
    }

    private fun createArchive(){
        println("Введите название архива: ")
        val archiveTitle = scanner.nextLine()
        if(archiveTitle.isNotEmpty()){
            val archive = Archive(archiveTitle)
            archives.add(archive)
            println("Архив $archiveTitle создан.")
        } else {
            println("Название архива не может быть пустым.")
        }
    }

    private fun viewArchives(){
        if(archives.isEmpty()){
            println("Нет созданных архивов.")
            return
        }
        while(true){
            println("Список архивов:")
            archives.forEachIndexed{index, archive ->
                println("${index + 1}. ${archive.title}")
            }
            println("${archives.size + 1}. Вернуться в главное меню")
            when (val choice = readIntInput()) {
                in 1.. archives.size -> {
                    val selectedArchive = archives[choice -1]
                    viewArchive(selectedArchive)
                }
                archives.size + 1 -> {
                    return
                }
                else -> {
                    println("Неправильный выборю. Введите правильную цифру.")
                }
            }
        }
    }
    private fun viewArchive(archive: Archive){
        while(true){
            println("Архив ${archive.title}" +
                    "\n1. Добавить заметку" +
                    "\n2. Просмотреть заметку" +
                    "\n3. Вернуться к списку архивов")
            when(readIntInput()){
                1 -> createNoteInArchive(archive)
                2 -> viewNoteInArchive(archive)
                3 -> return
                else -> println("Неправильный выбор. Введите правильную цифру")
            }
        }
    }
    private fun createNoteInArchive(archive: Archive){
        println("Введите название заметки: ")
        val noteTitle = scanner.nextLine()
        if(noteTitle.isNotEmpty()){
            println("Введите текст заметки: ")
            val noteContent = scanner.nextLine()
            if(noteContent.isNotEmpty()){
                val note = Note(noteTitle, noteContent)
                archive.addNote(note)
                println("Заметка $noteTitle добавлена в архив '${archive.title}'")
            } else {
                println("Содержание заметки не может быть пустым")
            }
        } else {
            println("Название заметки не может быть пустым")
        }
    }
    private fun viewNoteInArchive(archive: Archive){
        val notes = archive.getNotes()
        if(notes.isEmpty()){
            println("В архиве '${archive.title}' нет заметок.")
            return
        }
        while(true){
            println("Заметки в архиве '${archive.title}':")
            notes.forEachIndexed{index, note ->
                println("${index + 1}. ${note.title}")
            }
            println("${notes.size + 1}. Вернуться к списку архивов")
            when (val choice = readIntInput()) {
                in 1..notes.size -> {
                    val selectedNote = notes[choice - 1]
                    viewNoteContent(selectedNote)

                }
                notes.size + 1 -> {
                    return
                }
                else -> {
                    println("Неправильный выбор. Введите правильную цифру.")
                }
            }
        }
    }
    private fun viewNoteContent(note: Note){
        println("Содержание заметки '${note.title}':")
        println(note.content)
        println("1 .Вернуться к списку заметок")
        val choice = readIntInput()
        if(choice == 1){
            return
        } else {
            println("Неправильный выбор. Введите правильную цифру.")
        }
    }

    private fun exitApp(){
        println("Приложение завершено.")
        exitProcess(0)
    }


}