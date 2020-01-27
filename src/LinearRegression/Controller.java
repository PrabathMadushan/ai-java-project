/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinearRegression;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Prabath
 */
public class Controller implements Initializable {

    @FXML
    private Button btnTrainAI;

    @FXML
    private ChoiceBox<String> cmbXY;
    @FXML
    private TextField txtPValue;
    @FXML
    private Button txtProdict;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private TextField txtX;
    @FXML
    private TextField txtY;
    @FXML
    private TableView<DataModel> tblData;
    @FXML
    private TableColumn<DataModel, Integer> clmX;
    @FXML
    private TableColumn<DataModel, Integer> clmY;

    @FXML
    private LineChart<Number, Number> crtDataChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initChart();
        initEvents();
        otherInits();
    }

    public void otherInits() {
        cmbXY.setItems(FXCollections.observableArrayList(
                "X Value",
                "Y Value"
        ));
        cmbXY.getSelectionModel().selectFirst();
    }

    public void initEvents() {
        btnAdd.setOnAction(e -> {
            dataModel.add(new DataModel(Float.parseFloat(txtX.getText()), Float.parseFloat(txtY.getText())));
            sortDataSet();
        });
        btnRemove.setOnAction(e -> {
            DataModel seModel = tblData.getSelectionModel().getSelectedItem();
            dataModel.remove(seModel);
            sortDataSet();
        });

//        dataModel.addListener((ListChangeListener.Change<? extends DataModel> c) ->sortDataSet());
    }

    private void sortDataSet() {
        dataModel.sort((DataModel o1, DataModel o2) -> {
            if (o1.getX() == o2.getX()) {
                return 0;
            } else if (o1.getX() > o2.getX()) {
                return 1;
            } else {
                return -1;
            }
        });
    }

    public void initTable() {
        clmX.setCellValueFactory(new PropertyValueFactory<>("x"));
        clmY.setCellValueFactory(new PropertyValueFactory<>("y"));
        dataModel.addAll(
                new DataModel(10.3f, 183800),
                new DataModel(10.3f, 183200),
                new DataModel(10.1f, 174900),
                new DataModel(9.3f, 173500),
                new DataModel(8.4f, 172900),
                new DataModel(7.3f, 173200),
                new DataModel(8.4f, 173200),
                new DataModel(7.9f, 169700),
                new DataModel(7.6f, 174500),
                new DataModel(7.6f, 177900),
                new DataModel(6.9f, 188100),
                new DataModel(7.4f, 203200),
                new DataModel(8.1f, 230200),
                new DataModel(7f, 258200),
                new DataModel(6.5f, 309800),
                new DataModel(5.8f, 329800)
        //                new DataModel(8, 3),
        //                new DataModel(2, 10),
        //                new DataModel(11, 3),
        //                new DataModel(6, 6),
        //                new DataModel(5, 8),
        //                new DataModel(4, 12),
        //                new DataModel(12, 1),
        //                new DataModel(9, 4),
        //                new DataModel(6, 9),
        //                new DataModel(1, 14)
        );
        tblData.setItems(dataModel);
        sortDataSet();

    }

    public void initChart() {
        crtDataChart.setAnimated(false);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data");
        ObservableList<XYChart.Data<Number, Number>> data = series.getData();

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Best Fiting Line");
        ObservableList<XYChart.Data<Number, Number>> data2 = series2.getData();
        ArrayList<DataModel> lst = new ArrayList<>(dataModel);
        LinearRegression lr = new LinearRegression(lst);
        lr.genBestFitLine();
        data2.add(new XYChart.Data<>(dataModel.get(0).getX(), lr.predict(dataModel.get(0).getX())));
        data2.add(new XYChart.Data<>(dataModel.get(dataModel.size() - 1).getX(), lr.predict(dataModel.get(dataModel.size() - 1).getX())));
        crtDataChart.getData().addAll(series, series2);

        dataModel.forEach(dm -> {
            data.add(new XYChart.Data<>(dm.getX(), dm.getY()));
        });
        dataModel.addListener((ListChangeListener.Change<? extends DataModel> c) -> {

//            data.removeAll(data);
//            dataModel.forEach(dm -> {
//                data.add(new XYChart.Data<>(dm.getX(), dm.getY()));
//            });
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(dm -> {
                        data.add(new XYChart.Data<>(dm.getX(), dm.getY()));
                        data2.set(0, new XYChart.Data<>(dataModel.get(0).getX(), lr.predict(dataModel.get(0).getX())));
                        data2.set(1, new XYChart.Data<>(dataModel.get(dataModel.size() - 1).getX(), lr.predict(dataModel.get(dataModel.size() - 1).getX())));

                    });
                } else if (c.wasRemoved()) {
                    c.getRemoved().forEach(dm -> {
                        data.removeIf((XYChart.Data<Number, Number> t) -> {
                            return dm.getX() == t.getXValue().floatValue()
                                    && dm.getY() == t.getYValue().floatValue();
                        });
                    });
                    data2.set(0, new XYChart.Data<>(dataModel.get(0).getX(), lr.predict(dataModel.get(0).getX())));
                    data2.set(1, new XYChart.Data<>(dataModel.get(dataModel.size() - 1).getX(), lr.predict(dataModel.get(dataModel.size() - 1).getX())));
                }
            }

        });

    }

    private ObservableList<DataModel> dataModel = FXCollections.observableArrayList();

}
