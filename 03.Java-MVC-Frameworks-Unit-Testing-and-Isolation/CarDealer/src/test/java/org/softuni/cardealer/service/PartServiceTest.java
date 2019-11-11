package org.softuni.cardealer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTest {
	private static final String ID = "Invalid ID";
	@Autowired
	private PartRepository partRepository;
	private ModelMapper modelMapper;
	private PartService partService;

	@Before
	public void init() {
		modelMapper = new ModelMapper();
		partService = new PartServiceImpl(partRepository, modelMapper);

	}

	private Supplier getSupplier() {
		Supplier result = new Supplier();
		result.setName("Ivanov");
		result.setImporter(true);

		return result;

	}

	private SupplierServiceModel getSupplierServiceModel() {
		SupplierServiceModel result = new SupplierServiceModel();
		result.setName("Petrov");
		result.setImporter(false);

		return result;

	}

	private Part getPart() {
		Part result = new Part();
		result.setName("CarPart");
		result.setPrice(new BigDecimal(200));

		return result;

	}

	private PartServiceModel getPartWithCorectData() {
		PartServiceModel result = new PartServiceModel();
		result.setName("CarPart2");
		result.setPrice(new BigDecimal(150));

		return result;

	}

	private PartServiceModel getPartWithNullName() {
		PartServiceModel result = new PartServiceModel();
		result.setName(null);
		result.setPrice(new BigDecimal(200));
		result.setSupplier(getSupplierServiceModel());
		return result;

	}

	private PartServiceModel getPartWithNullPrice() {
		PartServiceModel result = new PartServiceModel();
		result.setName("CarPart2");
		result.setPrice(BigDecimal.ZERO);
		result.setSupplier(getSupplierServiceModel());

		return result;

	}

	@Test
	public void test_savePart_whenPartDataIsCorect_thenReturnSavedPart() {
		// given or arrange
		PartServiceModel toBeSavePart = getPartWithCorectData();
		// when or act
		PartServiceModel actual = partService.savePart(toBeSavePart);
		PartServiceModel expected = modelMapper.map(partRepository.findById(actual.getId()).orElse(null), PartServiceModel.class);
		// then or assert
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getPrice(), actual.getPrice());
		assertEquals(expected.getSupplier(), actual.getSupplier());

	}

	@Test(expected = Exception.class)
	public void test_savePart_whenPartNameIsIncorrect_thenReturnExeption() {
		// given or arrange
		PartServiceModel toBeSavePart = getPartWithNullName();

		// when or act
		partService.savePart(toBeSavePart);
	}

	@Test
	public void test_savePart_whenPartPriceIsIncorrect_thenReturnExeption() {
		// given or arrange
		PartServiceModel toBeSavePart = getPartWithNullPrice();

		// when or act
		try {
			partService.savePart(toBeSavePart);
			assertTrue(false);
		} catch (Exception ex) {
			System.out.println("Fount exception " + ex);
			assertTrue(true);
		}
	}

	@Test
	public void test_editPart_whenPartDataIsCorrect_thenReturnEditedPart() {
		// given or arrange
		Part persistedPart = partRepository.saveAndFlush(getPart());

		PartServiceModel toBeEdited = new PartServiceModel();
		toBeEdited.setId(persistedPart.getId());
		toBeEdited.setName("CarPart5");
		toBeEdited.setPrice(new BigDecimal(200));

		// when or act
		PartServiceModel actual = partService.editPart(toBeEdited);

		Part editedPart = partRepository.findById(persistedPart.getId()).orElse(null);

		PartServiceModel expected = modelMapper.map(editedPart, PartServiceModel.class);

		// then or assert
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());

	}

	@Test(expected = Exception.class)
	public void test_editPart_whenPartDataIsInCorrect_thenReturnExeption() {
		// given or arrange
		Part persistedPart = partRepository.saveAndFlush(getPart());

		PartServiceModel toBeEdited = new PartServiceModel();
		toBeEdited.setId(persistedPart.getId());
		toBeEdited.setName(null);
		toBeEdited.setPrice(new BigDecimal(200));

		// when or act
		PartServiceModel actual = partService.editPart(toBeEdited);

	}

	@Test
	public void test_deletePart_whenPartDataIsCorrect_thenReturnDeletedPart() {
		// given or arrange
		Part persistedPart = partRepository.saveAndFlush(getPart());

		long expectedRepositoryCount = partRepository.count();

		// when or act
		partService.deletePart(persistedPart.getId());

		// then or assert
		long actualRepositoryCount = partRepository.count();

		Assert.assertEquals(expectedRepositoryCount - 1, actualRepositoryCount);
	}

	@Test(expected = Exception.class)
	public void test_deletePart_whenPartDataIsInCorrect_thenReturnExeption() {
		// given or arrange
		Part persistedPart = partRepository.saveAndFlush(getPart());

		// when or act
		partService.deletePart(ID);

	}

	@Test
	public void test_givenPart_whenFindPartById_thenReturnPartById() {
		// given or arrange
		Part persistedPart = partRepository.saveAndFlush(getPart());

		// when or act
		PartServiceModel actual = partService.findPartById(persistedPart.getId());
		PartServiceModel expected = modelMapper.map(persistedPart, PartServiceModel.class);

		// then or assert
		assertEquals(expected.getId(), actual.getId());

	}

	@Test(expected = Exception.class)
	public void test_missingPart_whenFindPartById_thenReturnExeption() {
		// given or arrange
		Part persistedPart = partRepository.saveAndFlush(getPart());
		// when or act
		PartServiceModel actual = partService.findPartById(ID);
		// then or assert
		assertEquals(null, actual);

	}

}
