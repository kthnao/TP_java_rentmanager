package com.epf.rentmanager;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Client_test {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;
    @Test
    public void create_client_with_null_name() throws ServiceException, DaoException {
        //given
        Client client = new Client(1L, "", "Jean", "jean.dupont@email.com", LocalDate.of(1988, 01, 22));
        //when
        when(clientDao.create(client)).thenThrow(new DaoException("Le nom doit pas être vide."));
        //then
        assertThrows(DaoException.class, () -> clientDao.create(client));
    }

    @Test
    public void find_all_clients() throws ServiceException, DaoException {
       //given
        Client client = new Client(1L, "Dupont", "Jean", "jean.dupont@email.com", LocalDate.of(1988, 01, 22));
       //when
        when(clientDao.findAll()).thenReturn(List.of(client));
        List<Client> result = clientDao.findAll();
        //then
        assertEquals(List.of(client), result);
        verify(clientDao, times(1)).findAll();
    }
    @Test
    public void create_client_with_valid_data() throws ServiceException, DaoException {
        //given
        Client client = new Client(1L, "Dupont", "Jean",  "jean.dupont@email.com", LocalDate.of(1988, 01, 22));
        //when
        when(clientDao.create(any(Client.class))).thenReturn(1L);
        long result = clientDao.create(client);
        //then
        assertEquals(1L, result);
        verify(clientDao, times(1)).create(client);
    }

    @Test
    public void count_clients() throws ServiceException, DaoException {
        //given

        //when
        Number result = clientService.count();
        //then
        assertEquals(0, result);

    }




}
