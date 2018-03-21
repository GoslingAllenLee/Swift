package abbalove.chat.chatapp.models;



public class User {

    public String uid;
    public String name;
    public String email;
    public String image;
    public String DOB;
    public String token;


    public User() {
    }

    public User(String uid, String name, String email, String image, String DOB) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.image = image;
        this.DOB = DOB;
    }

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}
