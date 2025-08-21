package io.irwansyahdev96.readcollection.constant;

public enum Message {
    SUCCESS_SAVE("Data success to saved"),FAILED_SAVE("Data failed to save"),
    SUCCESS_UPDATE("Data success to updated"),FAILED_UPDATE("Data failed to update"),
    SUCCESS_DELETE("Data success to deleted"),FAILED_DELETE("Data failed to delete");

    private String message;

    private Message(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
