package com.crud.reserva;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.crud.reserva.service.ReservaService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect" })
@Sql("/test.sql")
@AutoConfigureTestDatabase
public class CrudReservasApplicationTests {
 
    @Autowired
    ReservaService reservaService;
 
    @Test
    public void testPruebaCargaSql() {
        assertThat(reservaService.getAll()).hasSize(2);
        MockitoAnnotations.openMocks(this);
    }
 
}
