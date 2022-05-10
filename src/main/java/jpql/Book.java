package jpql;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class Book extends Product {

    private String author;
    private String isbn;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return String.format(
                "id='%d', name='%s', author='%s', price='%d', stockAmount='%d', author='%s', isbn='%s'"
                , getId(), getName(), getAuthor(), getPrice(), getStockAmount(), getAuthor(), getIsbn()
        );
    }
}
