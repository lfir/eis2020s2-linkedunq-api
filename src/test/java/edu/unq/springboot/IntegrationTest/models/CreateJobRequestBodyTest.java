package edu.unq.springboot.IntegrationTest.models;

import java.time.LocalDate;

import edu.unq.springboot.models.CreateJobRequestBody;
import org.junit.jupiter.api.Test;

public class CreateJobRequestBodyTest {
	@Test
	void receivedRequestGeneratesValidJobAttributes() {
		CreateJobRequestBody bd = new CreateJobRequestBody("Jose123", "titulo", "desc", "2010-01-01", "2010-01-01", "http://www.mercadolibre.com.ar/");
		assert(String.class.isInstance(bd.getUsername()));
		assert(String.class.isInstance(bd.getDescripcion()));
		assert(String.class.isInstance(bd.getTitulo()));
		assert(LocalDate.class.isInstance(LocalDate.parse(bd.getFechaInicioTrabajo())));
		assert(LocalDate.class.isInstance(LocalDate.parse(bd.getFechaFinTrabajo())));
		assert(String.class.isInstance(bd.getEnlace()));
	}
}
