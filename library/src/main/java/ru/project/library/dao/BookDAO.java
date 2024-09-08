package ru.project.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.project.library.models.Book;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", (rs, rowNum) -> {
            Book book = new Book();
            book.setBookId(rs.getInt("book_id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            book.setYear(rs.getInt("year"));
            book.setPersonId(rs.getInt("person_id"));
            return book;
        });
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{id}, (rs, rowNum) -> {
            Book book = new Book();
            book.setBookId(rs.getInt("book_id"));
            book.setName(rs.getString("name"));
            book.setAuthor(rs.getString("author"));
            book.setYear(rs.getInt("year"));
            book.setPersonId(rs.getInt("person_id"));
            return book;
        }).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);}

    public void appointBook(int id, int person_id) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", person_id,id);
    }

    public void returnBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE book_id = ?", id);
    }

    public Integer getPersonIdByBookId(int bookId) {
        String sql = "SELECT person_id FROM Book WHERE book_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{bookId}, Integer.class);
    }
}
