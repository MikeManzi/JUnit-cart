package com.example.cart.service;

import com.example.cart.model.Item;
import com.example.cart.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepositoryMock;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void getAll_withSomeElements() {

        when(itemRepositoryMock.findAll()).thenReturn(Arrays.asList(new Item(1,"Item1",100,2,200),
                new Item(2,"Item2",400,3,1200)));
        assertEquals(200,itemService.getAll().get(0).getValue());
    }
}
