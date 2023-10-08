import java.util.Scanner

class NotesApp {
    private val archives = mutableListOf<Archive>()
    private val scanner = Scanner(System.`in`)

    fun run() {
        while (true) {
            printMainMenu()
            val choice = readIntInput()

            when (choice) {
                1 -> createArchive()
                2 -> viewArchives()
                3 -> exitApp()
                else -> println("Неправильный выбор. Введите правильную цифру.")
            }
        }
    }

    private fun printMainMenu() {
        println("Меню:")
        println("1. Создать архив")
        println("2. Просмотр архивов")
        println("3. Выйти из приложения")
    }

    private fun createArchive() {
        print("Введите название архива: ")
        val archiveTitle = scanner.nextLine()
        if (archiveTitle.isNotEmpty()) {
            val archive = Archive(archiveTitle)
            archives.add(archive)
            println("Архив '$archiveTitle' создан.")
        } else {
            println("Название архива не может быть пустым.")
        }
    }

    private fun viewArchives() {
        if (archives.isEmpty()) {
            println("Нет созданных архивов.")
            return
        }

        while (true) {
            println("Список архивов:")
            archives.forEachIndexed { index, archive ->
                println("${index + 1}. ${archive.title}")
            }
            println("${archives.size + 1}. Вернуться в главное меню")
            val choice = readIntInput()

            if (choice in 1..archives.size) {
                val selectedArchive = archives[choice - 1]
                viewArchive(selectedArchive)
            } else if (choice == archives.size + 1) {
                return
            } else {
                println("Неправильный выбор. Введите правильную цифру.")
            }
        }
    }

    private fun viewArchive(archive: Archive) {
        while (true) {
            println("Архив: ${archive.title}")
            println("1. Добавить заметку")
            println("2. Просмотреть заметки")
            println("3. Вернуться к списку архивов")
            val choice = readIntInput()

            when (choice) {
                1 -> createNoteInArchive(archive)
                2 -> viewNotesInArchive(archive)
                3 -> return
                else -> println("Неправильный выбор. Введите правильную цифру.")
            }
        }
    }

    private fun createNoteInArchive(archive: Archive) {
        print("Введите название заметки: ")
        val noteTitle = scanner.nextLine()
        if (noteTitle.isNotEmpty()) {
            print("Введите текст заметки: ")
            val noteContent = scanner.nextLine()
            if (noteContent.isNotEmpty()) {
                val note = Note(noteTitle, noteContent)
                archive.addNote(note)
                println("Заметка '$noteTitle' добавлена в архив '${archive.title}'.")
            } else {
                println("Содержание заметки не может быть пустым.")
            }
        } else {
            println("Название заметки не может быть пустым.")
        }
    }

    private fun viewNotesInArchive(archive: Archive) {
        val notes = archive.getNotes()
        if (notes.isEmpty()) {
            println("В архиве '${archive.title}' нет заметок.")
            return
        }

        while (true) {
            println("Заметки в архиве '${archive.title}':")
            notes.forEachIndexed { index, note ->
                println("${index + 1}. ${note.title}")
            }
            println("${notes.size + 1}. Вернуться к списку архива")
            val choice = readIntInput()

            if (choice in 1..notes.size) {
                val selectedNote = notes[choice - 1]
                viewNoteContent(selectedNote)
            } else if (choice == notes.size + 1) {
                return
            } else {
                println("Неправильный выбор. Введите правильную цифру.")
            }
        }
    }

    private fun viewNoteContent(note: Note) {
        println("Содержание заметки '${note.title}':")
        println(note.content)
        println("1. Вернуться к списку заметок")
        val choice = readIntInput()
        if (choice == 1) {
            return
        } else {
            println("Неправильный выбор. Введите правильную цифру.")
        }
    }

    private fun exitApp() {
        println("Приложение завершено.")
        System.exit(0)
    }

    private fun readIntInput(): Int {
        try {
            return scanner.nextLine().toInt()
        } catch (e: NumberFormatException) {
            println("Пожалуйста, введите цифру.")
            return -1
        }
    }

}