package org.softuni.cardealer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceTest {
	private static final String ID = "Invalid ID";
	private CustomerService customerService;
	@Autowired
	private CustomerRepository customerRepository;

	private ModelMapper modelMapper;

	@Before
	public void init() {
		modelMapper = new ModelMapper();
		customerService = new CustomerServiceImpl(customerRepository, modelMapper);

	}

	private Customer getCustomer() {
		Customer result = new Customer();
		result.setName("Ani");
		result.setBirthDate(LocalDate.of(1892, 1, 12));
		result.setYoungDriver(true);

		return result;

	}

	private CustomerServiceModel getCustomerWithCorectData() {
		CustomerServiceModel result = new CustomerServiceModel();
		result.setName("Gosho");
		result.setBirthDate(LocalDate.of(1960, 10, 5));
		result.setYoungDriver(false);

		return result;

	}

	private CustomerServiceModel getCustomerWithNullName() {
		CustomerServiceModel result = new CustomerServiceModel();
		result.setName(null);
		result.setBirthDate(LocalDate.of(1960, 10, 5));
		result.setYoungDriver(false);

		return result;

	}

	private CustomerServiceModel getCustomerWithNullBirthday() {
		CustomerServiceModel result = new CustomerServiceModel();

		result.setName("Pesho");
		result.setBirthDate(null);
		result.setYoungDriver(false);

		return result;

	}

	private CustomerServiceModel getCustomerWithIncorectBirthday() {
		CustomerServiceModel result = new CustomerServiceModel();

		result.setName("Pesho");
		result.setBirthDate(LocalDate.of(12, 13, 32));
		result.setYoungDriver(false);

		return result;

	}

	private CustomerServiceModel getCustomerWithEmptyYoungDriven() {
		CustomerServiceModel result = new CustomerServiceModel();
		result.setName("Pesho");
		result.setBirthDate(LocalDate.of(1960, 10, 5));

		return result;

	}

	@Test
	public void test_saveCustomer_whenCustomerDataIsCorect_thenReturnSavedCustomer() {
		// given or arrange
		CustomerServiceModel toBeSaveCustomer = getCustomerWithCorectData();
		// when or act
		CustomerServiceModel actualCustomer = customerService.saveCustomer(toBeSaveCustomer);
		CustomerServiceModel expectedCustomer = modelMapper.map(customerRepository.findById(actualCustomer.getId()).orElse(null), CustomerServiceModel.class);
		// then or assert
		assertEquals(expectedCustomer.getId(), actualCustomer.getId());
		assertEquals(expectedCustomer.getName(), actualCustomer.getName());
		assertEquals(expectedCustomer.getBirthDate(), actualCustomer.getBirthDate());
		assertEquals(expectedCustomer.isYoungDriver(), actualCustomer.isYoungDriver());

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void test_saveCustomer_whenCustomerNameIsIncorrect_thenReturnExeption() {
		// given or arrange
		CustomerServiceModel toBeSaveCustomer = getCustomerWithNullName();

		// when or act
		customerService.saveCustomer(toBeSaveCustomer);
	}

	@Test
	public void test_saveCustomer_whenCustomerBirthdayIsNull_thenReturnExeption() {
		// given or arrange
		CustomerServiceModel toBeSaveCustomer = getCustomerWithNullBirthday();
		// when or act
		try {
			customerService.saveCustomer(toBeSaveCustomer);
			assertTrue(false);
		} catch (DataIntegrityViolationException ex) {
			System.out.println("Fount exception " + ex);
			assertTrue(true);
		}
	}

	@Test(expected = DateTimeException.class)
	public void test_saveCustomer_whenCustomerBirthdayIsIncorect_thenReturnExeption() {
		// given or arrange
		CustomerServiceModel toBeSaveCustomer = getCustomerWithIncorectBirthday();
		// when or act

		customerService.saveCustomer(toBeSaveCustomer);

	}

	@Test
	public void test_saveCustomer_whenCustomerIsWithEmptyYoungDriven_thenReturnExeption() {
		// given or arrange
		CustomerServiceModel toBeSaveCustomer = getCustomerWithEmptyYoungDriven();

		// when or act
		try {
			customerService.saveCustomer(toBeSaveCustomer);
			assertTrue(false);
		} catch (AssertionError ex) {
			System.out.println("Fount exception " + ex);
			assertTrue(true);
		}
	}

	@Test
	public void test_editCustomer_whenCustomerDataIsCorrect_thenReturnEditedCustomer() {
		// given or arrange
		Customer persistedCustomer = customerRepository.saveAndFlush(getCustomer());

		CustomerServiceModel toBeEdited = new CustomerServiceModel();
		toBeEdited.setId(persistedCustomer.getId());
		toBeEdited.setName("Ani");
		toBeEdited.setBirthDate(LocalDate.of(1892, 1, 12));
		toBeEdited.setYoungDriver(false);

		// when or act
		CustomerServiceModel actualCustomer = customerService.editCustomer(toBeEdited);

		Customer editedCustomer = customerRepository.findById(persistedCustomer.getId()).orElse(null);

		CustomerServiceModel expectedCustomer = modelMapper.map(editedCustomer, CustomerServiceModel.class);

		// then or assert
		assertEquals(expectedCustomer.getId(), actualCustomer.getId());
		assertEquals(expectedCustomer.getName(), actualCustomer.getName());
		assertEquals(expectedCustomer.getBirthDate(), actualCustomer.getBirthDate());
		assertEquals(expectedCustomer.isYoungDriver(), actualCustomer.isYoungDriver());

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void test_editCustomer_whenCustomerDataIsInCorrect_thenReturnExeption() {
		// given or arrange
		Customer persistedCustomer = customerRepository.saveAndFlush(getCustomer());

		CustomerServiceModel toBeEdited = new CustomerServiceModel();
		toBeEdited.setId(persistedCustomer.getId());
		toBeEdited.setName(null);
		toBeEdited.setBirthDate(LocalDate.of(1892, 1, 12));
		toBeEdited.setYoungDriver(false);

		// when or act
		customerService.editCustomer(toBeEdited);

	}

	@Test
	public void test_deleteCustomer_whenCustomerDataIsCorrect_thenReturnDeletedCustomer() {
		// given or arrange
		Customer customer = customerRepository.saveAndFlush(getCustomer());
		long expectedRepositoryCount = customerRepository.count();

		// when or act
		customerService.deleteCustomer(customer.getId());

		// then or assert
		long actualRepositoryCount = customerRepository.count();

		Assert.assertEquals(expectedRepositoryCount - 1, actualRepositoryCount);
	}

	@Test(expected = Exception.class)
	public void test_deleteCustomer_whenCustomerDataIsInCorrect_thenReturnExeption() {
		// given or arrange
		Customer customer = customerRepository.saveAndFlush(getCustomer());
		// when or act
		customerService.deleteCustomer("Invalid ID!!");

	}

	@Test
	public void test_givenCustomer_whenFindCustomerById_thenReturnCustomerById() {
		// given or arrange
		Customer persistedCustomer = customerRepository.saveAndFlush(getCustomer());

		// when or act
		CustomerServiceModel actual = customerService.findCustomerById(persistedCustomer.getId());
		CustomerServiceModel expected = modelMapper.map(persistedCustomer, CustomerServiceModel.class);

		// then or assert
		assertEquals(expected.getId(), actual.getId());

	}

	@Test(expected = Exception.class)
	public void test_missingCustomer_whenFindCustomerById_thenReturnExeption() {
		// given or arrange

		Customer persistedCustomer = customerRepository.saveAndFlush(getCustomer());

		// when or act
		CustomerServiceModel actual = customerService.findCustomerById(ID);

		// then or assert
		assertEquals(null, actual);

	}

}
