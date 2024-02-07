package org.koffa.bookcrudcicdaws.dao;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.bookcrudcicdaws.entity.Book;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@ContextConfiguration(classes = {BookDao.class})
@ExtendWith(SpringExtension.class)
class BookDaoDiffblueTest {
    @Autowired
    private BookDao bookDao;

    @MockBean
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    /**
     * Method under test: {@link BookDao#getBook(String)}
     */
    @Test
    void testGetBook() {
        // Arrange
        DynamoDbTable<Object> dynamoDbTable = mock(DynamoDbTable.class);
        Book buildResult = Book.builder().author("JaneDoe").id("42").title("Dr").build();
        when(dynamoDbTable.getItem(Mockito.<Key>any())).thenReturn(buildResult);
        when(dynamoDbEnhancedClient.table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any()))
                .thenReturn(dynamoDbTable);

        // Act
        bookDao.getBook("42");

        // Assert
        verify(dynamoDbEnhancedClient).table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any());
        verify(dynamoDbTable).getItem(Mockito.<Key>any());
    }

    /**
     * Method under test: {@link BookDao#save(Book)}
     */
    @Test
    void testSave() {
        // Arrange
        DynamoDbTable<Object> dynamoDbTable = mock(DynamoDbTable.class);
        doNothing().when(dynamoDbTable).putItem(Mockito.<Object>any());
        when(dynamoDbEnhancedClient.table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any()))
                .thenReturn(dynamoDbTable);
        Book book = new Book("42", "Dr", "JaneDoe");

        // Act
        Book actualSaveResult = bookDao.save(book);

        // Assert
        verify(dynamoDbEnhancedClient).table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any());
        verify(dynamoDbTable).putItem(Mockito.<Object>any());
        assertSame(book, actualSaveResult);
    }

    /**
     * Method under test: {@link BookDao#delete(Book)}
     */
    @Test
    void testDelete() {
        // Arrange
        DynamoDbTable<Object> dynamoDbTable = mock(DynamoDbTable.class);
        when(dynamoDbTable.deleteItem(Mockito.<Key>any())).thenReturn("Delete Item");
        when(dynamoDbEnhancedClient.table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any()))
                .thenReturn(dynamoDbTable);

        // Act
        bookDao.delete(new Book("42", "Dr", "JaneDoe"));

        // Assert
        verify(dynamoDbEnhancedClient).table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any());
        verify(dynamoDbTable).deleteItem(Mockito.<Key>any());
    }

    /**
     * Method under test: {@link BookDao#delete(Book)}
     */
    @Test
    void testDelete2() {
        // Arrange
        DynamoDbTable<Object> dynamoDbTable = mock(DynamoDbTable.class);
        when(dynamoDbTable.deleteItem(Mockito.<Key>any())).thenReturn("Delete Item");
        when(dynamoDbEnhancedClient.table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any()))
                .thenReturn(dynamoDbTable);

        // Act
        bookDao.delete(new Book());

        // Assert
        verify(dynamoDbEnhancedClient).table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any());
        verify(dynamoDbTable).deleteItem(Mockito.<Key>any());
    }

    /**
     * Method under test: {@link BookDao#update(Book)}
     */
    @Test
    void testUpdate() {
        // Arrange
        DynamoDbTable<Object> dynamoDbTable = mock(DynamoDbTable.class);
        when(dynamoDbTable.updateItem(Mockito.<Object>any())).thenReturn("Update Item");
        when(dynamoDbEnhancedClient.table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any()))
                .thenReturn(dynamoDbTable);
        Book book = new Book("42", "Dr", "JaneDoe");

        // Act
        Book actualUpdateResult = bookDao.update(book);

        // Assert
        verify(dynamoDbEnhancedClient).table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any());
        verify(dynamoDbTable).updateItem(Mockito.<Object>any());
        assertSame(book, actualUpdateResult);
    }

    /**
     * Method under test: {@link BookDao#scan()}
     */
    @Test
    void testScan() {
        // Arrange
        SdkIterable<Object> sdkIterable = mock(SdkIterable.class);

        ArrayList<Object> objectList = new ArrayList<>();
        Stream<Object> streamResult = objectList.stream();
        when(sdkIterable.stream()).thenReturn(streamResult);
        PageIterable<Object> pageIterable = mock(PageIterable.class);
        when(pageIterable.items()).thenReturn(sdkIterable);
        DynamoDbTable<Object> dynamoDbTable = mock(DynamoDbTable.class);
        when(dynamoDbTable.scan()).thenReturn(pageIterable);
        when(dynamoDbEnhancedClient.table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any()))
                .thenReturn(dynamoDbTable);

        // Act
        List<Book> actualScanResult = bookDao.scan();

        // Assert
        verify(sdkIterable).stream();
        verify(dynamoDbEnhancedClient).table(Mockito.<String>any(), Mockito.<TableSchema<Object>>any());
        verify(dynamoDbTable).scan();
        verify(pageIterable).items();
        assertTrue(actualScanResult.isEmpty());
    }
}
