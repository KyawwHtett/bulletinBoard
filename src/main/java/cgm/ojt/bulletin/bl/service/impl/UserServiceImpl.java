package cgm.ojt.bulletin.bl.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.bl.service.UserService;
import cgm.ojt.bulletin.persistence.dao.UserDao;
import cgm.ojt.bulletin.persistence.entity.User;
import cgm.ojt.bulletin.web.form.UserForm;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void doSaveUser(UserForm userForm) {
		User user = new User(userForm);
		user.setCreated_at(new Date());
		this.userDao.dbSaveUser(user);
	}

	@Override
	public long doGetUserCount() {
		return this.userDao.dbGetUserCount();
	}

	@Override
	public UserDto doFindUserByEmail(String email) {
		User user = this.userDao.dbFindUserByEmail(email);
		if (user == null) {
			return null;
		}
		UserDto userDto = new UserDto(user);
		return userDto;
	}

	@Override
	public boolean doIsEmailExist(String email) {
		boolean result = false;
		User user = this.userDao.dbFindUserByAllEmail(email);
		if (user != null) {
			result = true;
		}
		return result;
	}

	@Override
	public List<UserDto> doGetAllUser() {
		return this.userDao.dbGetAllUser();
	}

	@Override
	public void doDeleteUserById(int userId) {
		this.userDao.dbDeleteUserById(userId);
	}

	@Override
	public UserDto dbFindUserById(int userId) {
		User user = this.userDao.dbFindUserById(userId);
		if (user == null) {
			return null;
		}
		UserDto userDto = new UserDto(user);
		return userDto;
	}

	@Override
	public void doUpdateUser(UserForm userForm) {
		this.userDao.dbUpdateUser(userForm);
	}

	@SuppressWarnings("resource")
	@Override
	public String doImportUser(MultipartFile file) throws IOException {
		String errorMsg = this.ValidateFile(file);
		if (errorMsg != null) {
			return errorMsg;
		}
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
			User user = new User();
			Row row = sheet.getRow(i);
			Cell cellUsername = row.getCell(1);
			user.setUsername(cellUsername.getStringCellValue());
			Cell cellEmail = row.getCell(2);
			user.setEmail(cellEmail.getStringCellValue());
			Cell cellPsw = row.getCell(3);
			user.setPassword(passwordEncoder.encode(cellPsw.getStringCellValue()));
			Cell cellGender = row.getCell(4);
			user.setGender(cellGender.getStringCellValue());
			Cell cellType = row.getCell(5);
			String type = (cellType.getStringCellValue().equals("User")) ? "0" : "1";
			user.setType(type);
			user.setCreated_at(new Date());
			this.userDao.dbSaveUser(user);
		}
		session.getTransaction().commit();
		session.close();
		return "File Upload Successful";
	}

	@Override
	public void doDownloadAllUser(HttpServletResponse response) throws IOException {
		List<User> listUser = this.userDao.dbGetAllUsers();
		String fileName = "user_list.xlsx";

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Post List");
		XSSFRow rowHead = sheet.createRow((short) 0);
		rowHead.createCell(0).setCellValue("ID");
		rowHead.createCell(1).setCellValue("Name");
		rowHead.createCell(2).setCellValue("Email");
		rowHead.createCell(3).setCellValue("Gender");
		rowHead.createCell(4).setCellValue("Type");

		int count = 1;
		for (User user : listUser) {
			XSSFRow row = sheet.createRow((short) count);
			row.createCell(0).setCellValue(count);
			row.createCell(1).setCellValue(user.getUsername());
			row.createCell(2).setCellValue(user.getEmail());
			row.createCell(3).setCellValue(user.getGender());
			String type = user.getType().equals("0") ? "User" : "Admin";
			row.createCell(4).setCellValue(type);
			count++;
		}
		count = 0;

		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileName);
			response.setHeader(headerKey, headerValue);
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workbook.close();
			IOUtils.closeQuietly(response.getOutputStream());
		}
	}

	@SuppressWarnings({ "resource" })
	private String ValidateFile(MultipartFile file) throws IOException {
		// check if file is null
		if (file.isEmpty()) {
			return "No file is selected";
		}
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		// check extension
		if (!extension.equals("xlsx")) {
			return "File Extension Wrong!";
		}
		// check null
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				return "File is null";
			}
		}
		// check file Type
		for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			// UserName String
			Cell cellUsername = row.getCell(1);
			if (cellUsername == null || (cellUsername.getCellType() != Cell.CELL_TYPE_STRING)) {
				return "Wrong Data Type in this file USERNAME";
			}
			// Email String
			Cell cellEmail = row.getCell(2);
			if (cellEmail == null || (cellEmail.getCellType() != Cell.CELL_TYPE_STRING)) {
				return "Wrong Data Type in this file EMAIL";
			}
			// Password String
			Cell cellPassword = row.getCell(3);
			if (cellPassword == null || (cellPassword.getCellType() != Cell.CELL_TYPE_STRING)) {
				return "Wrong Data Type in this file PASSWORD";
			}
			// Gender String
			Cell cellGender = row.getCell(4);
			if (cellGender == null || (cellGender.getCellType() != Cell.CELL_TYPE_STRING)) {
				return "Wrong Data Type in this file GENDER";
			}
			// Type String
			Cell cellType = row.getCell(5);
			if (cellType == null || (cellType.getCellType() != Cell.CELL_TYPE_STRING)) {
				return "Wrong Data Type in this file TYPE";
			}
		}
		// check file has no data
		if (sheet.getFirstRowNum() == sheet.getLastRowNum()) {
			return "No Data in the File";
		}
		return null;
	}
}