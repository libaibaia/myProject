package com.UI.controller;

import com.constant.ChainType;
import com.constant.ExpValueType;
import com.constant.PayloadGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    /***
     * 命令TextArea
     */
    @FXML
    private TextArea command;

    /***
     * 结果类型选择框
     */
    @FXML
    private ComboBox<ExpValueType> expValueType;
    /***
     * exp结果文本框
     */
    @FXML
    private TextArea expResultTextArea;

    /***
     * 利用链类型
     */
    @FXML
    private ComboBox<ChainType> chainType;

    /***
     * 获取exp更新到文本框
     */
    @FXML
    private void getExpValue() {
        String text = command.getText();
        String[] cmd = text.split("\n");
        PayloadGenerator payloadGenerator = getCurrentChainType(cmd);
        expResultTextArea.clear();
        //生成payload
        //获取结果更新到expResultTextArea
        String exp = payloadGenerator.getExp(expValueType.getSelectionModel().getSelectedItem(),cmd);
        expResultTextArea.appendText(exp);
    }

    /**
     * 根据当前选择获取对应的链类型，并返回枚举中的对象
     * @param cmd 命令参数
     * @return PayloadGenerator对象
     */
    private PayloadGenerator getCurrentChainType(String[] cmd){
        return chainType.getSelectionModel().getSelectedItem().getPayloadGenerator();
    }

    /***
     * 初始化
     * @param location
     * The location used to resolve relative paths for the root object, or
     * <tt>null</tt> if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or <tt>null</tt> if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBox();
    }


    /***
     * 初始化选择列表
     */
    private void initComboBox() {
        ObservableList<ChainType> chainTypes = FXCollections.observableArrayList(ChainType.values());
        ObservableList<ExpValueType> expValueTypes = FXCollections.observableArrayList(ExpValueType.values());
        chainType.setItems(chainTypes);
        expValueType.setItems(expValueTypes);
    }


}
