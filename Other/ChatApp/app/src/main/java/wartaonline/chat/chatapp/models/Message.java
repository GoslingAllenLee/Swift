package wartaonline.chat.chatapp.models;


public class Message {
    public String uid;
    public String name;
    public String avatar;
    public String message;
    public String tanggal;
    public int tipe;
    public String originalname;

    public Message() {
    }

    public Message(String uid, String name, String avatar, String message, String tanggal) {
        this.uid = uid;
        this.name = name;
        this.avatar = avatar;
        this.message = message;
        this.tanggal = tanggal;

    }

    public Message(String uid, String name, String avatar, String message, String tanggal, int tipe) {
        this.uid = uid;
        this.name = name;
        this.avatar = avatar;
        this.message = message;
        this.tanggal = tanggal;
        this.tipe = tipe;
    }

    public String getTanggal() {
        return this.tanggal;
    }
}


