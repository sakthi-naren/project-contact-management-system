package controllers;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import models.Contacts;
import models.DBContactSystem;
import view.ContactSystemUI;

public class ContactManagementSystem {

    private final ContactSystemUI uiHandler = new ContactSystemUI(); 
    private DBContactSystem db ;
    private final String[] MENU_STRINGS = {"Display All Contacts" , "Add New Contact" , "Modify Existing Contact" , "Delete Contacts"};
    private final int MENU_STRINGS_LENGTH = MENU_STRINGS.length;
    private final String MAIN_MENU_HEADER = "Contacts Management System";
    private final String INPUT_MISMATCH_ERROR_MESSAGE = "INPUT VALUE MUST BE OF REQUIRED TYPE!!!";
    private final String INPUT_OUT_OF_BOUND_ERROR_MESSAGE = "INPUT VALUE OUT OF CHOICE!!!";
    private final String UNSUPPORTED_FEATURE_ERROR_MESSAGE = "FEATURE NOT SUPPORTED YET!!!";
    private final String OPERATION_SUCCESS_STRING = " OPERATION COMPLETED SUCCESFULLY!!!";
    private final String OPERATION_FAILURE_STRING = " OPERATION FAILED!!!";
    

    public void startApplication () {
        mainMenu();
    }

    private void mainMenu() {

        int choice ;

        try {
            uiHandler.printMenu(MENU_STRINGS, MAIN_MENU_HEADER);
            db = DBContactSystem.getDeclaredInstance();
            choice = uiHandler.getInteger();
            if(choice > MENU_STRINGS_LENGTH + 1 || choice < 1 ) throw new Warning(INPUT_OUT_OF_BOUND_ERROR_MESSAGE);
            
            switch(choice) {
                case 1 -> displayAllContacts();

                case 2 -> {
                    if(addNewContact()) {
                        uiHandler.printNewLine();
                        uiHandler.printOutputMessage("ADD CONTACT"  + OPERATION_SUCCESS_STRING);
                    } else {
                        uiHandler.printNewLine();
                        uiHandler.printWarningMessage("ADD CONTACT" + OPERATION_FAILURE_STRING);
                    }
                }

                case 3 -> {
                    if(modifyExistingContacts()) {
                        uiHandler.printNewLine();
                        uiHandler.printOutputMessage("MODIFY CONTACT"  + OPERATION_SUCCESS_STRING);
                    } else {
                        uiHandler.printNewLine();
                        uiHandler.printWarningMessage("MODIFY CONTACT" + OPERATION_FAILURE_STRING);
                    }
                }

                case 4 -> {
                    if(deleteContact()) {
                        uiHandler.printNewLine();
                        uiHandler.printOutputMessage("DELETE CONTACT"  + OPERATION_SUCCESS_STRING);
                    } else {
                        uiHandler.printNewLine();
                        uiHandler.printWarningMessage("DELETE CONTACT" + OPERATION_FAILURE_STRING);
                    }
                }
                    
                default -> {
                    if(choice == MENU_STRINGS_LENGTH + 1) return;
                    else unSupportedActivity();  
                }
            }

        } catch (Warning | UnsupportedOperationException warning )  {
            uiHandler.printNewLine();
            uiHandler.printWarningMessage(warning.getMessage());
        } catch (InputMismatchException warning) {
            uiHandler.printNewLine();
            uiHandler.printWarningMessage(INPUT_MISMATCH_ERROR_MESSAGE);
            uiHandler.getString();    
        } catch (DataBaseCustomException dbError) {
            uiHandler.printNewLine();
            uiHandler.printWarningMessage(dbError.errorMessage());
        }
        
        mainMenu();
        
    }

    private void unSupportedActivity() {
        throw new UnsupportedOperationException(UNSUPPORTED_FEATURE_ERROR_MESSAGE);
    }

    private void displayAllContacts () throws DataBaseCustomException {

        List<Contacts> contactsList = db.selectAllData();
        uiHandler.printNewLine();
        uiHandler.printInformativeMessage(contactsList.size() + " Contacts found!");
        uiHandler.printNewLine();
        if (!contactsList.isEmpty()) {
            for (Contacts contact : contactsList) {
                uiHandler.printOutputMessage(contact.getName() + " :  " + contact.getNumber());
            }
        }  

    }

    private boolean addNewContact() throws DataBaseCustomException , InputMismatchException {
        uiHandler.printNewLine();
        uiHandler.printLine("Enter Contact Details:");
        uiHandler.print("Enter Number: ");
        BigDecimal number = uiHandler.getBigDecimal();
        uiHandler.print("Enter Name: ");
        String name = uiHandler.getString();
        return db.addData(db.createNewContact(name, number));
    }

    private boolean modifyExistingContacts () throws DataBaseCustomException , InputMismatchException {
        uiHandler.printNewLine();
        uiHandler.print("Enter Existing Contact Number: ");
        BigDecimal number = uiHandler.getBigDecimal();
        if(isContactExits(number)) {
            uiHandler.printNewLine();
            uiHandler.printLine("Enter Contact Details:");
            uiHandler.print("Enter Number: ");
            BigDecimal newNumber = uiHandler.getBigDecimal();
            uiHandler.print("Enter Name: ");
            String name = uiHandler.getString();
            return db.modifyData(name, newNumber, number) ;
        } 
        return false;
        
    }

    private boolean isContactExits(BigDecimal number) throws DataBaseCustomException {
        Contacts contact = db.selectData(number);
        return contact!=null;
    }

    private boolean deleteContact() throws DataBaseCustomException , InputMismatchException{
        uiHandler.printNewLine();
        uiHandler.print("Enter number for deleting contact");
        BigDecimal number = uiHandler.getBigDecimal();
        return db.deleteData(number);
    }



}
