import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
/**
 * GUI application that represents a Message Board.
 *
 * @author Sophie Imhof
 * @version 1
 */
public class MessageBoard extends Application {
    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        HBox postArea = new HBox();
        //Message List
        ObservableList<String> messageList = FXCollections.observableArrayList();
        ListView<String> postedView = new ListView<>(messageList);
        ArrayList<String> messageRecord = new ArrayList<String>();
        postedView.setMinHeight(500);
        postedView.setMinWidth(500);
        //Time Stamp List
        ObservableList<String> timeList = FXCollections.observableArrayList();
        ListView<String> timeView = new ListView<>(timeList);
        ArrayList<String> timeRecord = new ArrayList<String>();
        timeView.setMinHeight(500);
        //total number of messages
        final Label total = new Label("0 Messages");

        root.setCenter(postedView);
        root.setRight(timeView);
        root.setBottom(postArea);
        root.setTop(total);

        //Enter Information and Post/Remove Buttons
        final TextField name = new TextField();
        name.setPromptText("name");
        final TextField message = new TextField();
        message.setPromptText("message");
        Button post = new Button("Post Message");
        post.setStyle("-fx-text-fill: white; -fx-background-color: green;");
        Button delete = new Button("Delete Message");
        delete.setStyle("-fx-text-fill: white; -fx-background-color: red;");
        delete.disableProperty().bind((postedView.getSelectionModel().
                selectedItemProperty().isNull()));
        postArea.getChildren().addAll(name, message, post, delete);
        postArea.setAlignment(Pos.BOTTOM_LEFT);

        //Post button action
        post.setOnAction(e -> {
            if ((!name.getText().isBlank()) && (!message.getText().isBlank())) {
                messageRecord.add(0, name.getText() + " : " + message.getText());
                timeRecord.add(0, MessageBoard.getTime());
                addMessage(messageList, messageRecord);
                addMessage(timeList, timeRecord);
                changeNumMessages(messageRecord, total);
                name.clear();
                message.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You must enter a name and a message.");
                alert.showAndWait();
            }
        });

        //remove button action
        delete.setOnAction(e -> {
            String selectedItem = postedView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                timeRecord.remove(messageRecord.indexOf(selectedItem));
                messageRecord.remove(selectedItem);
                removeMessage(messageList, messageRecord);
                removeMessage(timeList, timeRecord);
                changeNumMessages(messageRecord, total);
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sophie's CS1331 Message Board");
        stage.show();
    }
    /**
     * Method that adds a message to the list of messages.
     *
     * @param messageList observable list of messages
     * @param messageRecord an array list to keep try of changes
     */
    private void addMessage(ObservableList<String> messageList, ArrayList<String> messageRecord) {
        messageList.clear();
        for (String s : messageRecord) {
            messageList.add(s);
        }
    }
    /**
     * Method that removes a message to the list of messages.
     *
     * @param messageList observable list of messages
     * @param messageRecord an array list to keep try of changes
     */
    private void removeMessage(ObservableList<String> messageList, ArrayList<String> messageRecord) {
        messageList.clear();
        for (String s : messageRecord) {
            messageList.add(s);
        }
    }
    /**
     * Method that gets the timestamp of a message posted.
     *
     * @return string version of the time posted
     */
    private static String getTime() {
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        String timeStamp = date.format(new Date());
        return timeStamp;
    }
    /*
    * Changes the total number of messages displayed.
    *
    *@param messageRecord array list of current messages displayed
    *@param l label displaying total number of messages
    */
    private void changeNumMessages(ArrayList<String> messageRecord, Label l) {
        l.setText(String.format("%d Messages", messageRecord.size()));
    }
}
