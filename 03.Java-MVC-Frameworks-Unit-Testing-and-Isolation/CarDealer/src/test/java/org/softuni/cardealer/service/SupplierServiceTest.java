package org.softuni.cardealer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTest {
	private static final String ID = "Invalid ID";
	@Autowired
	private SupplierRepository supplierRepository;

	private ModelMapper modelMapper;

	private SupplierService supplierService;

	@Before
	public void init() {
		modelMapper = new ModelMapper();

		supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);
	}

	private Supplier getSupplier() {
		Supplier result = new Supplier();
		result.setName("Ivanov");
		result.setImporter(true);

		return result;

	}

	private SupplierServiceModel getSupplierWithCorectData() {
		SupplierServiceModel result = new SupplierServiceModel();
		result.setName("Petrov");
		result.setImporter(false);

		return result;

	}

	private SupplierServiceModel getSupplierWithNullName() {
		SupplierServiceModel result = new SupplierServiceModel();
		result.setName(null);
		result.setImporter(false);

		return result;

	}

	private SupplierServiceModel getSupplierWithoutImporter() {
		SupplierServiceModel result = new SupplierServiceModel();
		result.setName(null);

		return result;

	}

	@Test
	public void test_saveSupplier_whenSupplierDataIsCorect_thenReturnSavedSupplier() {
		// given or arrange
		SupplierServiceModel toBeSaveSupplier = getSupplierWithCorectData();
		// when or act
		SupplierServiceModel actual = supplierService.saveSupplier(toBeSaveSupplier);
		SupplierServiceModel expected = modelMapper.map(supplierRepository.findById(actual.getId()).orElse(null), SupplierServiceModel.class);
		// then or assert
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.isImporter(), actual.isImporter());

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void test_saveSupplier_whenSupplierNameIsIncorrect_thenReturnExeption() {
		// given or arrange
		SupplierServiceModel toBeSaveSupplier = getSupplierWithNullName();

		// when or act
		supplierService.saveSupplier(toBeSaveSupplier);
	}

	@Test
	public void test_saveSupplier_whenSupplierImporterIsIncorrect_thenReturnExeption() {
		// given or arrange
		SupplierServiceModel toBeSaveSupplier = getSupplierWithoutImporter();
		// when or act
		try {
			supplierService.saveSupplier(toBeSaveSupplier);
			assertTrue(false);
		} catch (DataIntegrityViolationException ex) {
			System.out.println("Fount exception " + ex);
			assertTrue(true);
		}
	}

	@Test
	public void test_editSupplier_whenSupplierDataIsCorrect_thenReturnEditedSuplier() {
		// given or arrange
		Supplier persistedSupplier = supplierRepository.saveAndFlush(getSupplier());

		SupplierServiceModel toBeEdited = new SupplierServiceModel();
		toBeEdited.setId(persistedSupplier.getId());
		toBeEdited.setName("Ani Petrova");
		toBeEdited.setImporter(true);

		// when or act
		SupplierServiceModel actualSupplier = supplierService.editSupplier(toBeEdited);

		Supplier editedSupplier = supplierRepository.findById(persistedSupplier.getId()).orElse(null);

		SupplierServiceModel expectedSupplier = modelMapper.map(editedSupplier, SupplierServiceModel.class);

		// then or assert
		assertEquals(expectedSupplier.getId(), actualSupplier.getId());
		assertEquals(expectedSupplier.getName(), actualSupplier.getName());
		assertEquals(expectedSupplier.isImporter(), actualSupplier.isImporter());

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void test_editSupplier_whenSupplierDataIsInCorrect_thenReturnExeption() {
		// given or arrange

		Supplier persistedSupplier = supplierRepository.saveAndFlush(getSupplier());

		SupplierServiceModel toBeEdited = new SupplierServiceModel();
		toBeEdited.setId(persistedSupplier.getId());
		toBeEdited.setName(null);
		toBeEdited.setImporter(true);

		// when or act
		SupplierServiceModel actualSupplier = supplierService.editSupplier(toBeEdited);

	}

	@Test
	public void test_deleteSupplier_whenSupplierDataIsCorrect_thenReturnDeletedSupplier() {
		// given or arrange
		Supplier supplier = supplierRepository.saveAndFlush(getSupplier());
		long expectedRepositoryCount = supplierRepository.count();

		// when or act
		supplierService.deleteSupplier(supplier.getId());

		// then or assert
		long actualRepositoryCount = supplierRepository.count();

		Assert.assertEquals(expectedRepositoryCount - 1, actualRepositoryCount);
	}

	@Test(expected = Exception.class)
	public void test_deleteSupplier_whenSupplierDataIsInCorrect_thenReturnExeption() {
		// given or arrange
		Supplier supplier = supplierRepository.saveAndFlush(getSupplier());
		// when or act
		supplierService.deleteSupplier(ID);

	}

	@Test
	public void test_givenSupplier_whenFindSupplierById_thenReturnSupplierById() {
		// given or arrange
		Supplier supplier = supplierRepository.saveAndFlush(getSupplier());

		// when or act
		SupplierServiceModel actual = supplierService.findSupplierById(supplier.getId());
		SupplierServiceModel expected = modelMapper.map(supplier, SupplierServiceModel.class);

		// then or assert
		assertEquals(expected.getId(), actual.getId());

	}

	@Test(expected = Exception.class)
	public void test_missingSupplier_whenFindSupplierById_thenReturnExeption() {
		// given or arrange
		Supplier supplier = supplierRepository.saveAndFlush(getSupplier());
		// when or act
		SupplierServiceModel actual = supplierService.findSupplierById(ID);
		// then or assert
		assertEquals(null, actual);

	}

}
