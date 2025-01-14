import java.util.HashMap;

public enum StatusTask {
    NEW,
    IN_PROGRESS,
    DONE;

    public static void checkStatus(Subtask subtask, HashMap<Integer, Epic> epics) {//
        Epic epic = epics.get(subtask.getIdEpic());
        HashMap<Integer, Subtask> subtasks = epic.getSubtasksArray();

        int countNew = 0;
        int countDone = 0;

        for (Subtask sub : subtasks.values()) {

            if (sub.getStatus().equals("NEW")) {
                countNew++;
            } else if (sub.getStatus().equals("DONE")) {
                countDone++;
            }
        }

        if (countNew == subtasks.size() || subtasks.isEmpty()) {
            epic.setStatus(StatusTask.NEW);
        } else if (countDone == subtasks.size()) {
            epic.setStatus(StatusTask.DONE);
        } else {
            epic.setStatus(StatusTask.IN_PROGRESS);
        }
    }
}