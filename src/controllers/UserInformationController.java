package controllers;

import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class UserInformationController {

    public static void setFirstName(TextField textfield){
        IMatDataHandler.getInstance().getCustomer().setFirstName(textfield.getText());
        System.out.println(IMatDataHandler.getInstance().getCustomer().getFirstName());
    }

    public static void setLastName(TextField textfield){
        IMatDataHandler.getInstance().getCustomer().setLastName(textfield.getText());
        System.out.println(IMatDataHandler.getInstance().getCustomer().getLastName());
    }

    public static void setAddress(TextField textfield){
        IMatDataHandler.getInstance().getCustomer().setAddress(textfield.getText());
        System.out.println(IMatDataHandler.getInstance().getCustomer().getAddress());
    }

    public static void setPostCode(TextField textfield){
        IMatDataHandler.getInstance().getCustomer().setPostCode(textfield.getText());
        System.out.println(IMatDataHandler.getInstance().getCustomer().getPostCode());
    }
}
