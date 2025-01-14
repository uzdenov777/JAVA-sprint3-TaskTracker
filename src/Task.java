class Task {
    private String name;
    private String description;
    private StatusTask status;
    private int id;


    public Task(String name, String description, int id, StatusTask status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public StatusTask getStatus(){
        if (status == null){return null;}
        return status;
    }

    public void setStatus(StatusTask status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }
}
