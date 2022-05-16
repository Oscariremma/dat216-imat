package controllers;

import interfaces.NavigationRequestListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DeliveryController extends AnchorPane {

    @FXML
    FlowPane dayFlowPane;

    @FXML
    Label deliveryCost;

    @FXML
    FlowPane timeFlowPane;

    @FXML
    TextField firstNameTextField;

    @FXML
    TextField lastNameTextField;

    @FXML
    TextField addressTextField;

    @FXML
    TextField postCodeTextField;

    @FXML AnchorPane nextPaymentAnchorPane;
    @FXML AnchorPane backButtonAnchorPane;


    public static List<DeliveryDateController> deliveryDates = new ArrayList<>();
    public static List<DeliveryTimeController> deliveryTimes = new ArrayList<>();

    private static LocalDate deliveryDate;
    private static String deliveryTime;

    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();

    public DeliveryController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/delivery.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (int i = 0; i < 8; i++){
            DeliveryDateController item = new DeliveryDateController((LocalDateTime.parse(LocalDateTime.now().plusDays(i + 1).toString()).getDayOfMonth()), LocalDateTime.parse(LocalDateTime.now().plusDays(i + 1).toString()).getDayOfWeek(), (LocalDate.parse(LocalDate.now().plusDays(i + 1).toString())));
            dayFlowPane.getChildren().add(item);
            deliveryDates.add(item);
       }

        for (int i = 0; i < 8; i++){
            DeliveryTimeController item = new DeliveryTimeController(i);
            timeFlowPane.getChildren().add(item);
            deliveryTimes.add(item);
        }

        displayExistingUserInformation();

        textfieldChangeListener();

        // Pre-selects the first options, in each flowpane.
        deliveryDates.get(0).Select();
        deliveryTimes.get(0).Select();

        nextPaymentAnchorPane.setOnMouseClicked(mouseEvent -> triggerNavigationRequest(new NavigationRequest(NavigationType.Payment, null)));
        backButtonAnchorPane.setOnMouseClicked(mouseEvent -> triggerNavigationRequest(new NavigationRequest(NavigationType.Back, null)));

    }

    public static LocalDate getDeliveryDate(){
        return DeliveryController.deliveryDate;
    }
    public static String getDeliveryTime(){
        return DeliveryController.deliveryTime;
    }

    public static void setDeliveryDate(LocalDate date){
        DeliveryController.deliveryDate = date;
    }

    public static void setDeliveryTime(String time){
        DeliveryController.deliveryTime = time;
    }

    private void textfieldChangeListener() {
        firstNameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> UserInformationController.setFirstName(firstNameTextField));
        lastNameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> UserInformationController.setLastName(lastNameTextField));
        addressTextField.textProperty().addListener((observableValue, oldValue, newValue) -> UserInformationController.setAddress(addressTextField));
        postCodeTextField.textProperty().addListener((observableValue, oldValue, newValue) -> UserInformationController.setPostCode(postCodeTextField));
    }

    public static void deselectAllDates() {
        for (DeliveryDateController date: deliveryDates) {
            date.Deselect();
        }
    }

    public static void deselectAllTimes() {
        for (DeliveryTimeController time: deliveryTimes) {
            time.Deselect();
        }
    }

    private void displayExistingUserInformation(){
        firstNameTextField.setText(IMatDataHandler.getInstance().getCustomer().getFirstName());
        lastNameTextField.setText(IMatDataHandler.getInstance().getCustomer().getLastName());
        addressTextField.setText(IMatDataHandler.getInstance().getCustomer().getAddress());
        postCodeTextField.setText(IMatDataHandler.getInstance().getCustomer().getPostCode());

    }

    public static void registernavigationRequestListener(NavigationRequestListener listener){
        navigationRequestListeners.add(listener);
    }

    public void triggerNavigationRequest(NavigationRequest request){
        for (NavigationRequestListener listener : navigationRequestListeners) {
            try {
                listener.goToNavigationRequest(request);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
