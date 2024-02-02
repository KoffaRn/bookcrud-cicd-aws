package org.koffa.bookcrudcicdaws.dao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.bookcrudcicdaws.entity.Book;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@ContextConfiguration(classes = {BookDao.class})
@ExtendWith(SpringExtension.class)
class BookDaoDiffblueTest {
  @Autowired
  private BookDao bookDao;

  @MockBean
  private DynamoDbEnhancedClient dynamoDbEnhancedClient;
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
}
