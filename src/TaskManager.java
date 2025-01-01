import java.util.ArrayList;
import java.util.HashMap;

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
        if (tasks.size() == 0){return null;}

        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() { //Получение списка всех Epic
        if (epics.size() == 0){return null;}
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() { //Получение списка всех подзадач
        if (subtasks.size() == 0){return null;}
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

    public Task getTaskById(int id) { //Получение по идентификатору

            if (tasks.containsKey(id)) { //Проверяет ID пренадлежит задачам////////////////////////
                return tasks.get(id);
            }

        for (Epic epic : epics.values()) { //Проверяет ID пренадлежит Epic
            if (epic.getId() == id) {
                return epic;
            }
        }
        for (Subtask subtask : subtasks.values()) { //Проверяет ID пренадлежит подзадачам
            if (subtask.getId() == id) {
                return subtask;
            }
        }
        System.out.println("Не существует такого ID");
        return null;
    }


    public void addTask(Task taskInput) { //Добавляет полученный объект в соответсвующий HashMap и проверяет ,если такой ID уже

        if (taskInput.getClass() == Task.class) { // Проверяет на совместимость с задачей
            for (int id : tasks.keySet()) {
                if (id == taskInput.getId()) {
                    System.out.println("Объект с таким ID уже создан");
                }
            }
            tasks.put(taskInput.getId(), taskInput);
        }

        if (taskInput.getClass() == Epic.class) { //Проверяет на совместимость с Epic
            for (int id : epics.keySet()) {
                if (id == taskInput.getId()) {
                    System.out.println("Объект с таким ID уже создан");
                }
            }
            epics.put(taskInput.getId(), (Epic) taskInput);
        }

        if (taskInput.getClass() == Subtask.class) { //Проверяет на совместимость с подзадачей и добавляет в Epic
            boolean epicPresence = false;
            for (int id : epics.keySet()) {
                if ((((Subtask) taskInput).getIdEpic() == id)) { //если есть Epic ,которому подзадача пренадлежит
                    epicPresence = true;
                }
            }

            for (int id : subtasks.keySet()) {
                if (id == taskInput.getId()) {
                    System.out.println("Подзадача с таким ID уже создана");
                }
            }
            if (epicPresence) {
                subtasks.put(taskInput.getId(), (Subtask) taskInput);
                epics.get(((Subtask) taskInput).idEpic).
                        addSubtask((Subtask) taskInput); //Добавляет подзадачу в список Epic
                chekStatus((Subtask) taskInput); //Проверяет статус Epic после добавления в него подзадачи
            }else {
                System.out.println("Такого Epic не существует");
            }
        }
    }

    public void updateTask(Task taskInput) { // Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        if (taskInput.getClass() == Task.class) {
            for (int id : tasks.keySet()) {
                if (id == taskInput.getId()) {
                    tasks.put(taskInput.getId(), taskInput);
                }
            }
        }

        if (taskInput.getClass() == Epic.class) { //
            for (int id : epics.keySet()) {
                if (id == taskInput.getId()) {
                    epics.put(taskInput.getId(), (Epic) taskInput);
                }
            }
        }

        if (taskInput.getClass() == Subtask.class) { //
            for (int id : subtasks.keySet()) {
                if (id == taskInput.getId()) {
                    subtasks.put(taskInput.getId(), (Subtask) taskInput);
                    epics.get(((Subtask) taskInput).idEpic).addSubtask((Subtask) taskInput); //Добавляет подзадачу в список Epic
                    chekStatus((Subtask) taskInput); //Проверяет статус Epic после добавления в него подзадачи
                }
            }
        }
    }

    public void removeTaskById(int id) { //Удаление по идентификатору.
        for (int idKey : tasks.keySet()) { //сделать как в получение по айди у ИСлама
            if (id == idKey) {
                tasks.remove(id);
                return;
            }
        }
        for (int idKey : epics.keySet()) { //
            if (id == idKey) {
                epics.remove(id);
                return;
            }
        }
        for (int idKey : subtasks.keySet()) { //
            if (id == idKey) {
                subtasks.remove(id);
                return;
            }
        }
    }

    public HashMap<Integer ,Subtask> getListSubtasks(int id) { //Получение списка всех подзадач определённого эпика.
        for (int idKey : epics.keySet()) { //еределать как у Ислама
            if (id == idKey) {
                return epics.get(id).getSubtasksArray();
            }
        }
        System.out.println("Такого Epica нету");
        return null;
    }

    public void chekStatus(Subtask subtask) {//
        Epic epic = epics.get(subtask.getIdEpic());
        HashMap<Integer ,Subtask> subtasks = epic.getSubtasksArray();

        int countNEW = 0;
        int countIN_PROGRESS = 0;
        int countDONE = 0;

        for (Subtask sub : subtasks.values()) {

            if (sub.getStatus().equals("NEW")) {
                countNEW++;
            } else if (sub.getStatus().equals("IN_PROGRESS")) {
                countIN_PROGRESS++;
            } else if (sub.getStatus().equals("DONE")) {
                countDONE++;
            }
        }

        if (countNEW == subtasks.size() || subtasks.size() == 0) {
            epic.setStatus("NEW");
        } else if (countDONE == subtasks.size()) {
            epic.setStatus("DONE");
        } else {
            epic.setStatus("IN_PROGRESS");
        }
    }
}