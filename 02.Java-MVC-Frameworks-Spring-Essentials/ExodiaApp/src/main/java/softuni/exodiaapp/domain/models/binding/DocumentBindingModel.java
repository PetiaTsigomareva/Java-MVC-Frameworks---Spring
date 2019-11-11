package softuni.exodiaapp.domain.models.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DocumentBindingModel {

    private String title;
    private String content;

    public DocumentBindingModel() {
    }

    @NotNull(message = "can not be empty!")
    @Size(min = 1, message = "can not be empty")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "can not be empty!")
    @Size(min = 1, message = "can not be empty")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
