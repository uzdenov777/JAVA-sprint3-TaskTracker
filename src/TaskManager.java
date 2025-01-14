import java.util.HashMap;
import java.util.Optional;

class TaskManager {
    public HashMap<Integer, Task> tasks = new HashMap<>(); //Хранит задачи
    public HashMap<Integer, Epic> epics = new HashMap<>();//Хранит Epic
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();//Хранит подзадачи

    private int countId = 0;

    public int getNewId() {//Генерирует уникальный ID
        countId++;
        return countId;
    }

    public HashMap<Integer, Task> getTasks() { //Получение списка всех задач
        if (tasks.isEmpty()) {
            return new HashMap<>();
        }
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() { //Получение списка всех Epic
        if (epics.isEmpty()) {
            return new HashMap<>();
        }
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() { //Получение списка всех подзадач
        if (subtasks.isEmpty()) {
            return new HashMap<>();
        }
        return subtasks;
    }

    public void clearTasks() { //Удаление всех задач.
        tasks.clear();
    }

    public void clearEpics() { //Удаление всех Epic.
        epics.clear();
    }

    public void clearSubtasks() { //Удаление всех подзадач.
        subtasks.clear();
    }

   public Optional<Task> getTaskById(int id) { //Получение Task по идентификатору
        Optional<Task> getTask = Optional.empty();

        if (tasks.containsKey(id)) { //Проверяет ID, принадлежит задачам
            getTask = Optional.of(tasks.get(id));
        }else {
            System.out.println("Не существует такого ID Task");
        }
        return getTask;
    }
    public Optional<Epic> getEpicById(int id) { //Получение Epic по идентификатору
        Optional<Epic> getTask = Optional.empty();

        if (epics.containsKey(id)) { //Проверяет ID, принадлежит Epic
            getTask = Optional.of(epics.get(id));
        }else {
            System.out.println("Не существует такого ID Epic");
        }
        return getTask;
    }
    public Optional<Subtask> getSubtaskById(int id) { //Получение Subtask по идентификатору
        Optional<Subtask> getTask = Optional.empty();

        if (subtasks.containsKey(id)) { //Проверяет ID, принадлежит ли подзадачам
            getTask = Optional.of(subtasks.get(id));
        }else {
            System.out.println("Не существует такого ID Subtask");
        }
        return getTask;
    }

    public void addTask(Task taskInput) { //Добавляет полученный объект Task в соответсвующий HashMap и проверяет, если такой ID уже
        if (tasks.containsKey(taskInput.getId())) {// Проверяет на совместимость с задачей
            System.out.println("Задача с таким ID уже создан");
        }
        tasks.put(taskInput.getId(), taskInput);
    }

    public void addEpic(Epic taskInput) { //Добавляет полученный объект Epic в соответсвующий HashMap и проверяет, если такой ID уже
        if (epics.containsKey(taskInput.getId())) {//Проверяет на совместимость с Epic
            System.out.println("Epic с таким ID уже создан");
            return;
        }
        epics.put(taskInput.getId(), taskInput);
    }

    public void addSubtask(Subtask taskInput) { //Добавляет полученный объект Subtask в соответсвующий HashMap и проверяет, если такой ID уже
        boolean epicPresence = false;

        if (epics.containsKey(((taskInput).getIdEpic()))) { //если есть Epic, которому подзадача принадлежит
            epicPresence = true;
        }

        if (subtasks.containsKey(taskInput.getId())) {
            System.out.println("Подзадача с таким ID уже создана");
        }

        if (epicPresence) {
            subtasks.put(taskInput.getId(), taskInput);
            epics.get((taskInput).idEpic).
                    addSubtask(taskInput); //Добавляет подзадачу в список Epic
            StatusTask.checkStatus(taskInput, epics); //Проверяет статус Epic после добавления в него подзадачи
        } else {
            System.out.println("Такого Epic не существует");
        }
    }

    public void updateTask(Task taskInput) { // Обновление Task. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        if (tasks.containsKey(taskInput.getId())) {
            tasks.put(taskInput.getId(), taskInput);
        }
    }

    public void updateEpic(Epic taskInput) { // Обновление Epic. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        if (epics.containsKey(taskInput.getId())) {
            epics.put(taskInput.getId(), taskInput);
        }
    }

    public void updateSubtask(Subtask taskInput) { // Обновление Subtask. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        if (subtasks.containsKey(taskInput.getId())) {
            subtasks.put(taskInput.getId(), taskInput);
            epics.get(taskInput.idEpic).addSubtask(taskInput); //Добавляет подзадачу в список Epic
            StatusTask.checkStatus(taskInput, epics); //Проверяет статус Epic после добавления в него подзадачи
        }
    }

    public void removeTaskById(int id) { //Удаление Task по идентификатору.
        tasks.remove(id);
    }

    public void removeEpicById(int id) { //Удаление Epic по идентификатору.
        epics.remove(id);
    }

    public void removeSubtaskById(int id) { //Удаление Subtask по идентификатору.
        subtasks.remove(id);
    }

    public HashMap<Integer, Subtask> getListSubtasks(int id) { //Получение списка всех подзадач определённого Epic.

        if (epics.containsKey(id)) {
            return epics.get(id).getSubtasksArray();
        }

        System.out.println("Такого Epic нету");
        return new HashMap<>();
    }
}