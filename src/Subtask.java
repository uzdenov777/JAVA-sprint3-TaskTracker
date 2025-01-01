class Subtask extends Task {
int idEpic;

    public Subtask(String name, String description, int id, String status, int idEppic) {
        super(name, description, id, status);
         this.idEpic = idEppic;
    }

    public int getIdEpic() {
        return idEpic;
    }
}
