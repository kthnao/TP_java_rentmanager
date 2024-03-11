package com.epf.rentmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class clientTest {

    @InjectMocks
    private ClientService ClientService;
    @Mock
    private ClientDao clientDao;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void findAll_should_return_a_list_of_clients_when_dao_works() throws DaoException, ServiceException {

        List<Client> listClient = new ArrayList<>();
        // When
        when(this.clientDao.findAll()).thenReturn(listClient);

        // Then
        List<Client> actualClients = ClientService.findAll();
        assertEquals(listClient, actualClients);
    }
}
