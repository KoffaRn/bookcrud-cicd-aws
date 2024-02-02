package org.koffa.bookcrudcicdaws.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.bookcrudcicdaws.dao.BookDao;
import org.koffa.bookcrudcicdaws.entity.Book;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
class BookControllerDiffblueTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookDao bookDao;

    /**
     * Method under test: {@link BookController#getBook(String)}
     */
    @Test
    void testGetBook() throws Exception {
        // Arrange
        Book buildResult = Book.builder().author("JaneDoe").id("42").title("Dr").build();
        when(bookDao.getBook(Mockito.any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/{id}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":\"42\",\"title\":\"Dr\",\"author\":\"JaneDoe\"}"));
    }

    /**
     * Method under test: {@link BookController#getBooks()}
     */
    @Test
    void testGetBooks() throws Exception {
        // Arrange
        when(bookDao.scan()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link BookController#getBooks()}
     */
    @Test
    void testGetBooks2() throws Exception {
        // Arrange
        ArrayList<Book> bookList = new ArrayList<>();
        Book buildResult = Book.builder().author("JaneDoe").id("42").title("Dr").build();
        bookList.add(buildResult);
        when(bookDao.scan()).thenReturn(bookList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":\"42\",\"title\":\"Dr\",\"author\":\"JaneDoe\"}]"));
    }

    /**
     * Method under test: {@link BookController#saveBook(Book)}
     */
    @Test
    void testSaveBook() throws Exception {
        // Arrange
        doNothing().when(bookDao).save(Mockito.any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"title\":null,\"author\":null}"));
    }

    /**
     * Method under test: {@link BookController#saveBook(Book)}
     */
    @Test
    void testSaveBook2() throws Exception {
        // Arrange
        doNothing().when(bookDao).save(Mockito.any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books");
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"title\":null,\"author\":null}"));
    }

    /**
     * Method under test: {@link BookController#updateBook(Book)}
     */
    @Test
    void testUpdateBook() throws Exception {
        // Arrange
        Book buildResult = Book.builder().author("JaneDoe").id("42").title("Dr").build();
        when(bookDao.update(Mockito.any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/books");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"title\":null,\"author\":null}"));
    }
}
