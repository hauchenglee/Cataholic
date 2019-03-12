package web.com.store.model;

public class MailBean {
    private int state;
    private String title;
    private String content;
    private String code;

    public MailBean(int state, String title, String content, String code) {
        this.state = state;
        this.title = title;
        this.content = content;
        this.code = code;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
