package org.softuni.cardealer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTest {
	private static final String ID = "Test1";
	@Autowired
	private CarRepository carRepository;

	private CarService carService;

	@Mock
	private CarRepository mockCarRepository;

	private CarService carServiceWithMockedRepository;

	private ModelMapper modelMapper;

	@Before
	public void init() {
		mockCarRepository = Mockito.mock(CarRepository.class);
		modelMapper = new ModelMapper();
		carServiceWithMockedRepository = new CarServiceImpl(mockCarRepository, modelMapper);
		carService = new CarServiceImpl(carRepository, modelMapper);

	}

	private Car getCar() {
		Car result = new Car();
		result.setMake("Ford");
		result.setModel("Mustang");
		result.setTravelledDistance(15000L);

		return result;

	}

	private CarServiceModel getCarWithCorectData() {
		CarServiceModel result = new CarServiceModel();
		result.setMake("Moskvich");
		result.setModel("Iskra");
		result.setTravelledDistance(15000000L);

		return result;

	}

	private CarServiceModel getCarWithNullMake() {
		CarServiceModel result = new CarServiceModel();
		result.setMake(null);
		result.setModel("Fiesta");
		result.setTravelledDistance(15000L);

		return result;

	}

	private CarServiceModel getCarWithNullModel() {
		CarServiceModel result = new CarServiceModel();
		result.setMake("Opel");
		result.setModel(null);
		result.setTravelledDistance(15000L);

		return result;

	}

	private CarServiceModel getCarWithNullTravelDistance() {
		CarServiceModel result = new CarServiceModel();
		result.setMake("Opel");
		result.setModel("Astra");
		result.setTravelledDistance(null);

		return result;

	}
	// Test With H2 DB

	@Test
	public void test_saveCar_whenCarDataIsCorect_thenReturnSavedCar() {
		// given or arrange
		CarServiceModel toBeSavedCar = getCarWithCorectData();
		// when or act
		CarServiceModel actualCar = carService.saveCar(toBeSavedCar);
		CarServiceModel expectedCar = modelMapper.map(carRepository.findById(actualCar.getId()).orElse(null), CarServiceModel.class);
		// then or assert
		assertEquals(expectedCar.getId(), actualCar.getId());
		assertEquals(expectedCar.getMake(), actualCar.getMake());
		assertEquals(expectedCar.getModel(), actualCar.getModel());
		assertEquals(expectedCar.getTravelledDistance(), actualCar.getTravelledDistance());

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void test_saveCar_whenCarMakeIsIncorrect_thenReturnExeption() {
		// given or arrange
		CarServiceModel toBeSavedCar = getCarWithNullMake();

		// when or act
		carService.saveCar(toBeSavedCar);
	}

	@Test
	public void test_saveCar_whenCarModelIsIncorrect_thenReturnExeption() {
		// given or arrange
		CarServiceModel toBeSavedCar = getCarWithNullModel();

		// when or act
		try {
			carService.saveCar(toBeSavedCar);
			assertTrue(false);
		} catch (DataIntegrityViolationException ex) {
			System.out.println("Fount exception " + ex);
			assertTrue(true);
		}
	}

	@Test
	public void test_saveCar_whenCarTravelledDistanceIsIncorrect_thenReturnExeption() {
		// given or arrange
		CarServiceModel toBeSavedCar = getCarWithNullTravelDistance();

		// when or act
		try {
			carService.saveCar(toBeSavedCar);
			assertTrue(false);
		} catch (DataIntegrityViolationException ex) {
			System.out.println("Fount exception " + ex);
			assertTrue(true);
		}
	}

	@Test
	public void test_editCar_whenCarDataIsCorrect_thenReturnEditedCar() {
		// given or arrange
		Car persistedCar = getCar();
		persistedCar = carRepository.saveAndFlush(persistedCar);

		CarServiceModel toBeEdited = new CarServiceModel();
		toBeEdited.setId(persistedCar.getId());
		toBeEdited.setMake("Ford");
		toBeEdited.setModel("Fiesta");
		toBeEdited.setTravelledDistance(250000L);

		// when or act
		CarServiceModel actual = carService.editCar(toBeEdited);

		Car editedCar = carRepository.findById(persistedCar.getId()).orElse(null);

		CarServiceModel expected = modelMapper.map(editedCar, CarServiceModel.class);

		// then or assert
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getMake(), actual.getMake());
		assertEquals(expected.getModel(), actual.getModel());
		assertEquals(expected.getTravelledDistance(), actual.getTravelledDistance());

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void test_editCar_whenCarDataIsInCorrect_thenReturnExeption() {
		// given or arrange
		Car persistedCar = getCar();
		persistedCar = carRepository.saveAndFlush(persistedCar);

		CarServiceModel toBeEdited = new CarServiceModel();
		toBeEdited.setId(persistedCar.getId());
		toBeEdited.setMake(null);
		toBeEdited.setModel("Zafira");
		toBeEdited.setTravelledDistance(150000L);

		// when or act
		carService.editCar(toBeEdited);

	}

	@Test
	public void test_deleteCar_whenCarDataIsCorrect_thenReturnDeletedCar() {
		// given or arrange
		Car car = carRepository.saveAndFlush(getCar());
		long expectedRepositoryCount = carRepository.count();

		// when or act
		carService.deleteCar(car.getId());

		// then or assert
		long actualRepositoryCount = carRepository.count();

		Assert.assertEquals(expectedRepositoryCount - 1, actualRepositoryCount);
	}

	@Test(expected = Exception.class)
	public void test_deleteCar_whenCarDataIsInCorrect_thenReturnExeption() {
		// given or arrange
		Car car = carRepository.saveAndFlush(getCar());
		// when or act
		CarServiceModel deletedCar = carService.deleteCar("InvalidID");
		// then or assert
	}

	// Test with Mockito library

	@Test
	public void test_givenCar_whenFindCarById_thenReturnCarById() {
		// given or arrange

		Mockito.when(mockCarRepository.findById(ID)).thenReturn(Optional.of(new Car() {
			{
				setId(ID);
			}
		}));
		// when or act
		CarServiceModel carFount = carServiceWithMockedRepository.findCarById(ID);

		// then or assert
		assertEquals(ID, carFount.getId());

	}

	@Test
	public void test_missingCar_whenFindCarById_thenReturnNull() {
		// given or arrange

		Mockito.when(mockCarRepository.findById(ID)).thenReturn(Optional.empty());

		// when or act
		CarServiceModel carFount = carServiceWithMockedRepository.findCarById(ID);

		// then or assert
		assertEquals(null, carFount);

	}

}
