package com.MysqlJDBC;

import org.junit.Assert;
import org.junit.Test;

import com.MysqlJDBC.EmployeePayrollService.IOService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EmployeePayrollServiceTest {
	private static final IOService DB_IO = null;

	@Test
	public void given3EmployeesWhenWrittenToFileShouldWatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmps = new EmployeePayrollData[] { new EmployeePayrollData(1, "Devid", 100000.0),
				new EmployeePayrollData(2, "Prince", 200000.0), new EmployeePayrollData(3, "Terisa", 300000.0) };
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);

		employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
		System.out.println(entries);
		Assert.assertEquals(3, entries);
	}

	// UC2
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
		Assert.assertEquals(3, employeePayrollData.size());
	}

	// UC3
	// UC4
	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(DB_IO);
		employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00);
		boolean result = employeePayrollService.checkEmployeePayrollINSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}

	// UC5
	@Test
	public void givenDataRange_WhenRetrieved_ShouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(DB_IO);
		LocalDate startDate = LocalDate.of(2021, 07, 30);
		LocalDate endDate = LocalDate.now();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(DB_IO,
				startDate, endDate);
		Assert.assertEquals(3, employeePayrollData.size());
	}
}
