package org.koffa.bookcrudcicdaws.dao;

import lombok.RequiredArgsConstructor;
import org.koffa.bookcrudcicdaws.entity.Book;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookDao {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    public Book getBook(String id) {
        return getMappedTable(Book.class).getItem(Key.builder().partitionValue(id).build());
    }
    public void save(Book book) {
        if(book.getId() == null) book.setId(UUID.randomUUID().toString());
        if(book.getId().isEmpty()) book.setId(UUID.randomUUID().toString());
        getMappedTable(Book.class).putItem(book);
    }
    public void delete(Book book) {
        getMappedTable(Book.class).deleteItem(Key.builder().partitionValue(book.getId()).build());
    }
    public Book update(Book book) {
        getMappedTable(Book.class).updateItem(book);
        return book;
    }
    public List<Book> scan() {
        return getMappedTable(Book.class).scan().items().stream().toList();
    }
    private <T> DynamoDbTable<T> getMappedTable(Class<T> type) {
        return dynamoDbEnhancedClient.table("books", TableSchema.fromBean(type));
    }
}
