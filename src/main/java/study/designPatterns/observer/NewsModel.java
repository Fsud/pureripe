package study.designPatterns.observer;

/**
 * Created by fankun on 2018/1/19.
 */
public class NewsModel {
    private String title;
    private String content;
    public NewsModel(String title,String content){
        this.content = content;
        this.title = title;
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
}
