package ua.dokat.exeptions.entity;

public class TestException extends Exception {

    public TestException(){

    }

    public TestException(String message){
        super(message);
    }

    //todo: сделать ентити который будет возвращаться при ошибках.
    public <T> T returnResponseObj(T obj){
        return obj;
    }
}
