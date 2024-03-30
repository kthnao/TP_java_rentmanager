package com.epf.rentmanager;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Vehicle_test {
@Mock
    private VehicleDao vehicleDao;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    public void create_cars_with_valid_data() throws ServiceException, DaoException {
        //given
        Vehicle vehicle = new Vehicle(1L, "Renault", "Megane", 4);
        //when
        when(vehicleDao.create(any(Vehicle.class))).thenReturn(1L);
        long result = vehicleDao.create(vehicle);
        //then
        assertEquals(1L, result);
        verify(vehicleDao, times(1)).create(vehicle);
    }

    @Test
    public void find_all_cars() throws ServiceException, DaoException {
        //given
        Vehicle vehicle = new Vehicle(1L, "Renault", "Megane", 4);
        //when
        when(vehicleDao.findAll()).thenReturn(List.of(vehicle));
        List<Vehicle> result = vehicleDao.findAll();
        //then
        assertEquals(List.of(vehicle), result);
        verify(vehicleDao, times(1)).findAll();
    }

    @Test
    public void delete_cars() throws ServiceException, DaoException {
        //given
        Vehicle vehicle = new Vehicle(1L, "Renault", "Megane", 4);
        //when
        when(vehicleDao.delete(vehicle)).thenReturn(1L);
        long result = vehicleDao.delete(vehicle);
        //then
        assertEquals(result, 1L);
        verify(vehicleDao, times(1)).delete(vehicle);
    }

}
