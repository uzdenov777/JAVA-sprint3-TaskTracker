class Subtask extends Task {
int idEpic;

    public Subtask(String name, String description, int id, StatusTask status, int idEpic) {
        super(name, description, id, status);
         this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }
}
