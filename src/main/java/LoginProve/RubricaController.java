/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginProve;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author anuar
 */
public class RubricaController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane anchorUp;
    @FXML
    private HBox hboxButton;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnMod;
    @FXML
    private MenuButton menuSort;
    @FXML
    private MenuButton menuFilter;
    @FXML
    private Button btnImp;
    @FXML
    private Button btnExp;
    @FXML
    private TextField txtSearch;
    @FXML
    private AnchorPane anchorBottom;
    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> clmSurname;
    @FXML
    private TableColumn<?, ?> clmName;
    @FXML
    private TableColumn<?, ?> clmEmail;
    @FXML
    private TableColumn<?, ?> clmTel;
    @FXML
    private TableColumn<?, ?> clmTag;
    @FXML
    private RadioMenuItem radioMenuSur1;
    @FXML
    private RadioMenuItem radioMenuSur2;
    @FXML
    private RadioMenuItem radioMenuName1;
    @FXML
    private RadioMenuItem radioMenuName2;
    @FXML
    private CheckMenuItem checkMenuHome;
    @FXML
    private CheckMenuItem checkMenuUni;
    @FXML
    private CheckMenuItem checkMenuJob;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
